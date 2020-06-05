package ml.socshared.gateway.service;

import ml.socshared.gateway.domain.response.UserResponse;
import ml.socshared.gateway.domain.vk.response.VkGroupResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface VkService {
    void setAccessTokenApp(UUID systemUserId, String accessToken);
    UserResponse getUserDataVkAccount(UUID systemUserId);
}
