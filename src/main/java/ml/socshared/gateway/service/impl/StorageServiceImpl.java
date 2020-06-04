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
    TokenObject tokenStorageService;

    @Value("#{tokenGetter.tokenVK}")
    TokenObject tokenVkAdapter;

    final StorageServiceClient storageClient;
    final VkServiceClient vkClient;

    @Override
    public Page<GroupResponse> getGroupsList(UUID systemUserId, Pageable pageable) {

        return storageClient.getGroupsByUserId(systemUserId, pageable.getPageNumber(), pageable.getPageSize(), storageToken());
    }

    @Override
    public Page<PublicationResponse> getPostList(UUID systemUserId, UUID groupId, Pageable pageable) {
        //TODO проверка на точ то данному пользователю принадлежит данная группа
        return storageClient.getPublicationsByGroupId(groupId, pageable.getPageNumber(), pageable.getPageSize());
    }

    @Override
    public void addVkGroupToStorage(UUID systemUserId, String socGroupId) {
        GroupRequest group = new GroupRequest();
        group.setSocialNetwork(SocialNetwork.VK);
        group.setVkId(socGroupId);
        group.setFbId("");
        VkAdapterGroupResponse vkGroup =  vkClient.getGroupInfoById(systemUserId, socGroupId, vkToken());
        group.setName(vkGroup.getName());
        storageClient.addGroup(group, storageToken());
    }

    private String storageToken() {
        return "Bearer " + tokenStorageService.getToken();
    }

    private String vkToken()
    {
        return "Bearer " + tokenVkAdapter.getToken();
    }
}
