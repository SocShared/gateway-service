package ml.socshared.gateway.service;

import ml.socshared.gateway.domain.response.UserResponse;
import ml.socshared.gateway.domain.vk.PageAdapter;
import ml.socshared.gateway.domain.vk.response.VkGroupResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface VkService {

    void setAccessTokenApp(UUID systemUserId, String accessToken);
    void unsetAccessTokenApp(UUID systemUserId);
    UserResponse getUserDataVkAccount(UUID systemUserId);
    PageAdapter<VkGroupResponse> getGroups(UUID systemUserId, int pageNumber, int pageSize);
    VkGroupResponse getGroupInfoById(UUID systemUserId, String vkGroupId);

}
