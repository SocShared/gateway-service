package ml.socshared.gateway.api.v1.rest;

import ml.socshared.gateway.domain.vk.GroupResponse;
import ml.socshared.gateway.domain.SuccessResponse;
import org.springframework.data.domain.Page;

public interface VkApi {
    SuccessResponse setToken(String accessToken, String token);
    Page<GroupResponse> getGroupUserList(Integer page, Integer size, String token);
}
