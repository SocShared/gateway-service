package ml.socshared.gateway.api.v1.rest;

import ml.socshared.gateway.domain.SuccessResponse;
import ml.socshared.gateway.domain.vk_adapter.response.VkAdapterGroupResponse;
import org.springframework.data.domain.Page;

public interface VkApi {
    SuccessResponse setToken(String accessToken, String token);
    Page<VkAdapterGroupResponse> getGroupUserList(Integer page, Integer size, String token);
}
