package ml.socshared.gateway.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ml.socshared.gateway.client.StorageServiceClient;
import ml.socshared.gateway.domain.RestResponsePage;
import ml.socshared.gateway.domain.facebook.FacebookPage;
import ml.socshared.gateway.domain.facebook.response.FacebookGroupResponse;
import ml.socshared.gateway.domain.request.PostRequest;
import ml.socshared.gateway.domain.storage.PostType;
import ml.socshared.gateway.domain.storage.SocialNetwork;
import ml.socshared.gateway.domain.storage.request.GroupRequest;
import ml.socshared.gateway.domain.storage.request.PublicationRequest;
import ml.socshared.gateway.domain.storage.response.GroupResponse;
import ml.socshared.gateway.domain.storage.response.PublicationResponse;
import ml.socshared.gateway.domain.text_analyzer.response.KeyWordResponse;
import ml.socshared.gateway.domain.vk.PageAdapter;
import ml.socshared.gateway.domain.vk.response.VkGroupResponse;
import ml.socshared.gateway.exception.impl.HttpBadRequestException;
import ml.socshared.gateway.security.model.TokenObject;
import ml.socshared.gateway.service.FacebookService;
import ml.socshared.gateway.service.StorageService;
import ml.socshared.gateway.service.TextAnalyzerService;
import ml.socshared.gateway.service.VkService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class StorageServiceImpl implements StorageService {

    private final StorageServiceClient storageClient;
    private final VkService vkService;
    private final FacebookService facebookService;
    private final TextAnalyzerService textAnalyzerService;

    @Value("#{tokenGetter.tokenStorageService}")
    private TokenObject tokenStorage;

    private String storageAuthToken() {
        return "Bearer " + tokenStorage.getToken();
    }

    @Override
    public Page<GroupResponse> getGroups(UUID systemUserId, Pageable pageable) {
        return storageClient.getGroupsByUserId(systemUserId, pageable.getPageNumber(), pageable.getPageSize(), storageAuthToken());
    }

    @Override
    public Page<PublicationResponse> getPosts(UUID systemUserId, UUID groupId, Pageable pageable) {
        return storageClient.getPublicationsByGroupId(groupId, pageable.getPageNumber(), pageable.getPageSize(), storageAuthToken());
    }

    @Override
    public PublicationResponse savePost(UUID systemUserId, PostRequest request) {
        PublicationRequest req = new PublicationRequest();
        req.setGroupIds(request.getGroupIds());
        StringBuilder builder = new StringBuilder(request.getText() + "\n\n");

        log.info("HashTags: " + Arrays.toString(request.getHashTags()));

        if (request.getHashTags() != null && request.getHashTags().length != 0) {
            for (String str : request.getHashTags()) {
                String result = str.trim();
                if (!result.isEmpty())
                    builder.append('#').append(result.replaceAll("\\s", "_")).append(" ");
            }
        } else {
            List<KeyWordResponse> keyWords = textAnalyzerService.getKeyWords(request.getText(), 2, 4);

            int i = 0;
            for (KeyWordResponse keyWord : keyWords) {
                if (i <= 5)
                    builder.append('#').append(keyWord.getKeyWord().replaceAll("\\s", "_")).append(" ");
                else
                    break;
                i++;
            }
        }

        req.setText(builder.toString());
        if (request.getType() == PostType.DEFERRED && request.getPublicationDateTime() != null)
            req.setPublicationDateTime(request.getPublicationDateTime());
        else if (request.getType() == PostType.DEFERRED && request.getPublicationDateTime() == null)
            throw new HttpBadRequestException("publication date time for deferred is null");
        req.setType(request.getType());
        req.setUserId(systemUserId.toString());

        log.info("sending publication -> " + req);

        return storageClient.savePost(req, storageAuthToken());
    }

    @Override
    public FacebookPage<FacebookGroupResponse> getGroupsFacebookWithSelected(UUID systemUserId, Pageable pageable) {
        FacebookPage<FacebookGroupResponse> facebookGroupPage = facebookService.getGroups(systemUserId, pageable.getPageNumber(), pageable.getPageSize());
        RestResponsePage<GroupResponse> groupResponsesPage = storageClient.getGroupsByUserAndSocial(systemUserId, SocialNetwork.FACEBOOK, pageable.getPageNumber(), pageable.getPageSize(), storageAuthToken());

        List<FacebookGroupResponse> facebookGroups = facebookGroupPage.getObjects();
        List<GroupResponse> groupResponses = groupResponsesPage.getContent();

        for (FacebookGroupResponse facebookGroup : facebookGroups) {
            facebookGroup.setSelected(false);
            for (GroupResponse group : groupResponses) {
                if (group.getGroupFacebookId().equals(facebookGroup.getGroupId())) {
                    facebookGroup.setSystemUserId(group.getUserId().toString());
                    facebookGroup.setSystemGroupId(group.getGroupId());
                    facebookGroup.setSelected(true);
                    break;
                }
            }
        }

        return facebookGroupPage;
    }

    @Override
    public Page<VkGroupResponse> getGroupsVkWithSelected(UUID systemUserId, Pageable pageable) {
        PageAdapter<VkGroupResponse> vkResponse = vkService.getGroups(systemUserId, pageable.getPageNumber(),
                pageable.getPageSize());

        RestResponsePage<GroupResponse> storageResponse = storageClient.getGroupsByUserAndSocial(systemUserId, SocialNetwork.VK,
                pageable.getPageNumber(), pageable.getPageSize(), storageAuthToken());

        for(VkGroupResponse vkg : vkResponse.getObject()) {
            vkg.setSelected(false);
            for(GroupResponse gr : storageResponse.getContent()) {
                if(vkg.getGroupId().equals(gr.getGroupVkId())) {
                    vkg.setSystemUserId(gr.getUserId().toString());
                    vkg.setSystemGroupId(gr.getGroupId());
                    vkg.setSelected(true);
                    break;
                }
            }
        }

        return new PageImpl<>(vkResponse.getObject());
    }


    @Override
    public GroupResponse addVkGroupToStorage(UUID systemUserId, String vkGroupId) {
        GroupRequest group = new GroupRequest();
        group.setUserId(systemUserId);
        group.setSocialNetwork(SocialNetwork.VK);
        group.setVkId(vkGroupId);
        group.setFbId("");
        VkGroupResponse vkGroup =  vkService.getGroupInfoById(systemUserId, vkGroupId);
        group.setName(vkGroup.getName());

        return storageClient.addGroup(group, storageAuthToken());
    }

    @Override
    public GroupResponse addFBGroupToStorage(UUID systemUserId, String fbGroupId) {
        GroupRequest group = new GroupRequest();
        group.setUserId(systemUserId);
        group.setSocialNetwork(SocialNetwork.FACEBOOK);
        group.setFbId(fbGroupId);
        FacebookGroupResponse response = facebookService.getGroup(systemUserId, fbGroupId);
        group.setName(response.getName());

        return storageClient.addGroup(group, storageAuthToken());
    }

    @Override
    public void deleteGroupByFbId(UUID systemUserId, String fbGroupId) {
        storageClient.deleteByFBId(systemUserId, fbGroupId, storageAuthToken());
    }

    @Override
    public void deleteGroupByVKId(UUID systemUserId, String vkGroupId) {
        storageClient.deleteByVkId(systemUserId, vkGroupId, storageAuthToken());
    }

    @Override
    public void deleteFacebookGroupsByUserId(UUID systemUserId) {
        storageClient.deleteFacebookGroupsByUserId(systemUserId, storageAuthToken());
    }

    @Override
    public void deleteVkGroupsByUserId(UUID systemUserId) {
        storageClient.deleteVkGroupsByUserId(systemUserId, storageAuthToken());
    }
}
