package ml.socshared.gateway.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ml.socshared.gateway.client.FacebookClient;
import ml.socshared.gateway.client.StorageServiceClient;
import ml.socshared.gateway.client.VkServiceClient;
import ml.socshared.gateway.domain.RestResponsePage;
import ml.socshared.gateway.domain.SuccessResponse;
import ml.socshared.gateway.domain.facebook.FacebookPage;
import ml.socshared.gateway.domain.facebook.response.FacebookGroupResponse;
import ml.socshared.gateway.domain.request.PostRequest;
import ml.socshared.gateway.domain.storage.PostType;
import ml.socshared.gateway.domain.storage.SocialNetwork;
import ml.socshared.gateway.domain.storage.request.GroupRequest;
import ml.socshared.gateway.domain.storage.request.PublicationRequest;
import ml.socshared.gateway.domain.storage.response.GroupResponse;
import ml.socshared.gateway.domain.storage.response.PublicationResponse;
import ml.socshared.gateway.domain.vk.PageAdapter;
import ml.socshared.gateway.domain.vk.response.VkGroupResponse;
import ml.socshared.gateway.domain.vk_adapter.response.VkAdapterGroupResponse;
import ml.socshared.gateway.exception.impl.HttpBadRequestException;
import ml.socshared.gateway.security.model.TokenObject;
import ml.socshared.gateway.service.StorageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class StorageServiceImpl implements StorageService {

    private final StorageServiceClient storageClient;
    private final VkServiceClient vkClient;
    private final FacebookClient facebookClient;

    @Value("#{tokenGetter.tokenStorageService}")
    private TokenObject tokenStorage;

    @Value("#{tokenGetter.tokenVK}")
    private TokenObject tokenVK;

    @Value("#{tokenGetter.tokenFB}")
    private TokenObject tokenFB;

    private String storageAuthToken() {
        return "Bearer " + tokenStorage.getToken();
    }

    private String fbToken() {
        return "Bearer " + tokenFB.getToken();
    }

    private String vkToken() {
        return "Bearer " + tokenVK.getToken();
    }

    // TODO: Логика вывода странная, делаешь запрос на получения всех групп, зачем все, если у нас разедльны ВК и FB
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
        for (String str : request.getHashTags())
            builder.append('#').append(str.replaceAll("\\s", "_")).append(" ");
        req.setText(builder.toString());
        if (request.getType() == PostType.DEFERRED && request.getPublicationDateTime() != null)
            req.setPublicationDateTime(request.getPublicationDateTime());
        else
            throw new HttpBadRequestException("publication date time for deferred is null");
        req.setUserId(systemUserId.toString());

        return storageClient.savePost(req, storageAuthToken());
    }

    @Override
    public FacebookPage<FacebookGroupResponse> getGroupsFacebookWithSelected(UUID systemUserId, Pageable pageable) {
        FacebookPage<FacebookGroupResponse> facebookGroupPage = facebookClient.getGroups(systemUserId, pageable.getPageNumber(), pageable.getPageSize(), fbToken());
        RestResponsePage<GroupResponse> groupResponsesPage = storageClient.getGroupsByUserAndSocial(systemUserId, SocialNetwork.FACEBOOK, pageable.getPageNumber(), pageable.getPageSize(), storageAuthToken());

        List<FacebookGroupResponse> facebookGroups = facebookGroupPage.getObjects();
        List<GroupResponse> groupResponses = groupResponsesPage.getContent();


        for (FacebookGroupResponse facebookGroup : facebookGroups) {
            facebookGroup.setSelected(false);
            for (GroupResponse group : groupResponses) {
                if (group.getFacebookId().equals(facebookGroup.getGroupId())) {
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
        PageAdapter<VkGroupResponse> vkResponse = vkClient.getGroups(systemUserId, pageable.getPageNumber(),
                pageable.getPageSize(), vkToken());

        RestResponsePage<GroupResponse> storageResponse = storageClient.getGroupsByUserAndSocial(systemUserId, SocialNetwork.VK,
                pageable.getPageNumber(), pageable.getPageSize(), storageAuthToken());

        for(VkGroupResponse vkg : vkResponse.getObject()) {
            for(GroupResponse gr : storageResponse.getContent()) {
                vkg.setSelected(false);
                if(vkg.getGroupId().equals(gr.getVkId())) {
                    vkg.setSystemUserId(gr.getUserId().toString());
                    vkg.setSystemGroupId(gr.getGroupId());
                    vkg.setSelected(true);
                    break;
                }
            }
        }

        Page<VkGroupResponse> pageOfGroup = new PageImpl<>(vkResponse.getObject());
        return pageOfGroup;
    }


    @Override
    public GroupResponse addVkGroupToStorage(UUID systemUserId, String vkGroupId) {
        GroupRequest group = new GroupRequest();
        group.setUserId(systemUserId);
        group.setSocialNetwork(SocialNetwork.VK);
        group.setVkId(vkGroupId);
        group.setFbId("");
        VkGroupResponse vkGroup =  vkClient.getGroupInfoById(systemUserId, vkGroupId, vkToken());
        group.setName(vkGroup.getName());

        GroupResponse res = storageClient.addGroup(group, storageAuthToken());
        return res;
    }

    @Override
    public GroupResponse addFBGroupToStorage(UUID systemUserId, String fbGroupId) {
        GroupRequest group = new GroupRequest();
        group.setUserId(systemUserId);
        group.setSocialNetwork(SocialNetwork.FACEBOOK);
        group.setFbId(fbGroupId);
        FacebookGroupResponse response = facebookClient.getGroup(systemUserId, fbGroupId, fbToken());
        group.setName(response.getName());

        return storageClient.addGroup(group, storageAuthToken());
    }

    @Override
    public void deleteGroupByFbId(UUID systemUserId, String fbGroupId) {
        storageClient.deleteByFBId(systemUserId, fbGroupId, fbToken());
    }

    @Override
    public void deleteGroupByVKId(UUID systemUserId, String vkGroupId) {
        storageClient.deleteByVkId(systemUserId, vkGroupId, vkToken());
    }
}
