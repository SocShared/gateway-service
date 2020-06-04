package ml.socshared.gateway.service;

import ml.socshared.gateway.domain.vk.GroupResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface VkService {
    void setAccessTokenApp(UUID systemUserId, String accessToken);
    Page<GroupResponse> getGroupsOfUser(UUID systemUserId, Pageable pageable);
}
