package ml.socshared.gateway.client;



import ml.socshared.gateway.domain.response.UserResponse;
import ml.socshared.gateway.domain.vk.GroupResponse;
import ml.socshared.gateway.domain.vk.PageAdapter;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@FeignClient(name = "VkServiceClient", url = "${feign.url.vk}")
public interface VkServiceClient {

    @PostMapping(value = "/api/v1/private/users/{systemUserId}/app/",
    consumes = MediaType.APPLICATION_JSON_VALUE)
    void appRegister(@PathVariable UUID systemUserId,
                     @RequestBody String accessToken,
                     @RequestHeader("Authorization") String authToken);

    @GetMapping("api/v1/private/users/{systemUserId}/groups?page={page}&size={size}")

    PageAdapter<GroupResponse> getGroups(@PathVariable UUID systemUserId,
                                         @PathVariable Integer page,
                                         @PathVariable Integer size,
                                         @RequestHeader("Authorization") String authToken);

    @GetMapping(value = "/api/v1/private/users/{systemUserId}/vk/data", produces = MediaType.APPLICATION_JSON_VALUE)
    UserResponse getUserDataFacebookAccount(@PathVariable UUID systemUserId, @RequestHeader("Authorization") String token);

}
