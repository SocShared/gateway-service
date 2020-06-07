package ml.socshared.gateway.api.v1.rest;

import ml.socshared.gateway.domain.response.SuccessResponse;
import ml.socshared.gateway.domain.vk.response.VkGroupResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpServletRequest;

public interface VkApi {

    SuccessResponse setToken(String accessToken, HttpServletRequest request);
    Page<VkGroupResponse> getGroupUserList(Pageable pageable, HttpServletRequest request);
}
