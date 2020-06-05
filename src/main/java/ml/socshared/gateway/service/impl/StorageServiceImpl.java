package ml.socshared.gateway.service.impl;

import lombok.RequiredArgsConstructor;
import ml.socshared.gateway.client.StorageServiceClient;
import ml.socshared.gateway.client.VkServiceClient;
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

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StorageServiceImpl implements StorageService {


    @Value("#{tokenGetter.tokenStorageService}")
    TokenObject tokenStorage;

//    @Value("#{tokenGetter.tokenVK}")
//    TokenObject tokenVk;

    private final StorageServiceClient client;

    @Override
    public Page<GroupResponse> getGroupsList(UUID systemUserId, Pageable pageable) {
        return client.getGroupsByUserId(systemUserId, pageable.getPageNumber(), pageable.getPageSize(), storageAuthToken());

    }


//
//    @Override
//    public void addVkGroupToStorage(UUID systemUserId, String socGroupId) {
//        GroupRequest group = new GroupRequest();
//        group.setUserId(systemUserId);
//        group.setSocialNetwork(SocialNetwork.VK);
//        group.setVkId(socGroupId);
//        group.setFbId("");
//        VkAdapterGroupResponse vkGroup =  vkClient.getGroupInfoById(systemUserId, socGroupId, vkToken());
//        group.setName(vkGroup.getName());
//        client.addGroup(group, storageAuthToken());
//    }


        @Override
    public Page<PublicationResponse> getPostList(UUID systemUserId, UUID groupId, Pageable pageable) {
        return client.getPublicationsByGroupId(groupId, pageable.getPageNumber(), pageable.getPageSize(),
                                                storageAuthToken());
    }

    private String storageAuthToken() {
        return "Bearer " + tokenStorage.getToken();
    }
}
