package ml.socshared.gateway.service.impl;

import lombok.RequiredArgsConstructor;
import ml.socshared.gateway.client.FacebookClient;
import ml.socshared.gateway.client.StorageServiceClient;
import ml.socshared.gateway.client.VkServiceClient;
import ml.socshared.gateway.domain.RestResponsePage;
import ml.socshared.gateway.domain.SuccessResponse;
import ml.socshared.gateway.domain.facebook.FacebookPage;
import ml.socshared.gateway.domain.facebook.response.FacebookGroupResponse;
import ml.socshared.gateway.domain.storage.SocialNetwork;
import ml.socshared.gateway.domain.storage.request.GroupRequest;
import ml.socshared.gateway.domain.storage.response.GroupResponse;
import ml.socshared.gateway.domain.storage.response.PublicationResponse;
import ml.socshared.gateway.domain.vk_adapter.response.VkAdapterGroupResponse;
import ml.socshared.gateway.security.model.TokenObject;
import ml.socshared.gateway.service.StorageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
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

    @Override
    public Page<GroupResponse> getGroups(UUID systemUserId, Pageable pageable) {
        return storageClient.getGroupsByUserId(systemUserId, pageable.getPageNumber(), pageable.getPageSize(), storageAuthToken());
    }

    @Override
    public Page<PublicationResponse> getPosts(UUID systemUserId, UUID groupId, Pageable pageable) {
        return storageClient.getPublicationsByGroupId(groupId, pageable.getPageNumber(), pageable.getPageSize(), storageAuthToken());
    }

    @Override
    public FacebookPage<FacebookGroupResponse> getGroupsFacebookWithSelected(UUID systemUserId, Pageable pageable) {
        FacebookPage<FacebookGroupResponse> facebookGroupPage = facebookClient.getGroups(systemUserId, pageable.getPageNumber(), pageable.getPageSize(), fbToken());
        RestResponsePage<GroupResponse> groupResponsesPage = storageClient.getGroupsByUserAndSocial(systemUserId, SocialNetwork.FACEBOOK, pageable.getPageNumber(), pageable.getPageSize(), storageAuthToken());

        List<FacebookGroupResponse> facebookGroups = facebookGroupPage.getObjects();
        List<GroupResponse> groupResponses = groupResponsesPage.getContent();

        for (FacebookGroupResponse facebookGroup : facebookGroups) {
            GroupResponse groupResponse = new GroupResponse();
            groupResponse.setFbId(facebookGroup.getGroupId());
            groupResponse.setName(facebookGroup.getName());
            groupResponse.setSocialNetwork(SocialNetwork.FACEBOOK);
            groupResponse.setUserId(facebookGroup.getSystemUserId());
            if (groupResponses.contains(groupResponse))
                facebookGroup.setSelected(true);
        }

        return facebookGroupPage;
    }

    @Override
    public GroupResponse addVkGroupToStorage(UUID systemUserId, String vkGroupId) {
//        GroupRequest group = new GroupRequest();
//        group.setUserId(systemUserId);
//        group.setSocialNetwork(SocialNetwork.VK);
//        group.setVkId(vkGroupId);
//        group.setFbId("");
//        VkAdapterGroupResponse vkGroup =  vkClient.getGroupInfoById(systemUserId, socGroupId, vkToken());
//        group.setName(vkGroup.getName());
//        client.addGroup(group, storageAuthToken());
        return null;
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
}
