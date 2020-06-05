package ml.socshared.gateway.client;



import ml.socshared.gateway.domain.response.UserResponse;
import ml.socshared.gateway.domain.vk.VkAdapterGroupResponse;
import ml.socshared.gateway.domain.vk.PageAdapter;
import ml.socshared.gateway.domain.vk.response.VkGroupResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@FeignClient(name = "VkServiceClient", url = "${feign.url.vk}")
public interface VkServiceClient {

    @PostMapping(value = "/api/v1/private/users/{systemUserId}/app/",
    consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    void appRegister(@PathVariable UUID systemUserId,
                     @RequestBody String accessToken,
                     @RequestHeader("Authorization") String authToken);

    @GetMapping(value = "api/v1/private/users/{systemUserId}/groups",
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    PageAdapter<VkGroupResponse> getGroups(@PathVariable UUID systemUserId,
                                           @RequestParam Integer page,
                                           @RequestParam Integer size,
                                           @RequestHeader("Authorization") String authToken);

    @GetMapping(value = "/api/v1/private/users/{systemUserId}/vk/data",
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    UserResponse getUserDataVkAccount(@PathVariable UUID systemUserId, @RequestHeader("Authorization") String token);

    @GetMapping(value = "api/v1/private/users/{systemUserId}/groups/{socGroupId}",
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    VkGroupResponse getGroupInfoById(@PathVariable UUID systemUserId,
                                     @PathVariable String socGroupId,
                                     @RequestHeader("Authorization") String token);
}
