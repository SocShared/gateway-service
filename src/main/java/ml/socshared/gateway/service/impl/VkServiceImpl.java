package ml.socshared.gateway.service.impl;

import lombok.RequiredArgsConstructor;
import ml.socshared.gateway.client.StorageServiceClient;
import ml.socshared.gateway.client.VkServiceClient;
import ml.socshared.gateway.domain.RestResponsePage;
import ml.socshared.gateway.domain.response.UserResponse;
import ml.socshared.gateway.domain.storage.SocialNetwork;
import ml.socshared.gateway.domain.storage.response.GroupResponse;
import ml.socshared.gateway.domain.vk.PageAdapter;
import ml.socshared.gateway.domain.vk.VkAdapterGroupResponse;
import ml.socshared.gateway.domain.vk.response.VkGroupResponse;
import ml.socshared.gateway.security.model.TokenObject;
import ml.socshared.gateway.service.VkService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class VkServiceImpl implements VkService {

    private final VkServiceClient vkClient;
    private final StorageServiceClient storageClient;

    @Value("#{tokenGetter.tokenVK}")
    private TokenObject tokenVk;

    @Value("#{tokenGetter.tokenStorageService}")
    private TokenObject tokenStorageService;

    @Override
    public void setAccessTokenApp(UUID systemUserId, String accessToken) {
        vkClient.appRegister(systemUserId, accessToken, vkAuthToken());
    }

    @Override
    public void unsetAccessTokenApp(UUID systemUserId) {
        //storageClient.deleteVkGroupsByUserId(systemUserId, storageToken());
        vkClient.appReset(systemUserId, vkAuthToken());
    }

    @Override
    public UserResponse getUserDataVkAccount(UUID systemUserId) {
        return vkClient.getUserDataVkAccount(systemUserId, vkAuthToken());
    }

    @Override
    public PageAdapter<VkGroupResponse> getGroups(UUID systemUserId, int pageNumber, int pageSize) {
        return vkClient.getGroups(systemUserId, pageNumber, pageSize, vkAuthToken());
    }

    @Override
    public VkGroupResponse getGroupInfoById(UUID systemUserId, String vkGroupId) {
        return vkClient.getGroupInfoById(systemUserId, vkGroupId, vkAuthToken());
    }

    private String storageToken() {
        return "Bearer " + tokenStorageService.getToken();
    }

    private String vkAuthToken() {
        return "Bearer " + tokenVk.getToken();
    }

}
