package ml.socshared.gateway.client;


import ml.socshared.gateway.domain.vk_adapter.response.PageAdapter;
import ml.socshared.gateway.domain.vk_adapter.response.VkAdapterGroupResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@FeignClient(name = "VkServiceClient", url = "${feign.url.vk-adapter}")
public interface VkServiceClient {

    @PostMapping(value = "/api/v1/private/users/{systemUserId}/app/",
    consumes = MediaType.APPLICATION_JSON_VALUE)
    void appRegister(@PathVariable UUID systemUserId,
                     @RequestBody String accessToken,
                     @RequestHeader("Authorization") String authToken);

    @GetMapping("api/v1/private/users/{systemUserId}/groups?page={page}&size={size}")
    PageAdapter<VkAdapterGroupResponse> getGroups(@PathVariable UUID systemUserId,
                                                  @PathVariable Integer page,
                                                  @PathVariable Integer size,
                                                  @RequestHeader("Authorization") String authToken);

    @GetMapping("api/v1/private/users/{systemUserId}/groups/{groupId}")
    VkAdapterGroupResponse getGroupInfoById(@PathVariable UUID systemUserId,
                                            @PathVariable String socGroupId,
                                            @RequestHeader("Authorization") String authToken);
}
