package ml.socshared.gateway.service.impl;

import lombok.RequiredArgsConstructor;
import ml.socshared.gateway.client.VkServiceClient;
import ml.socshared.gateway.domain.response.UserResponse;
import ml.socshared.gateway.domain.vk.GroupResponse;
import ml.socshared.gateway.domain.vk.PageAdapter;
import ml.socshared.gateway.security.model.TokenObject;
import ml.socshared.gateway.service.VkService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class VkServiceImpl implements VkService {

    private final VkServiceClient vkClient;

    @Value("#{tokenGetter.tokenVK}")
    private TokenObject tokenVk;

    @Override
    public void setAccessTokenApp(UUID systemUserId, String accessToken) {
        vkClient.appRegister(systemUserId, accessToken, vkAuthToken());
    }

    @Override
    public Page<GroupResponse> getGroupsOfUser(UUID systemUserId, Pageable pageable) {
        PageAdapter<GroupResponse> res = vkClient.getGroups(systemUserId, pageable.getPageNumber(),
                pageable.getPageSize(), vkAuthToken());
        Page<GroupResponse> pageOfGroup = new PageImpl<>(res.getObject());

        return pageOfGroup;
    }

    @Override
    public UserResponse getUserDataFacebookAccount(UUID systemUserId) {
        return vkClient.getUserDataVkAccount(systemUserId, vkAuthToken());
    }

    private String vkAuthToken() {
        return "Bearer " + tokenVk.getToken();
    }

}
