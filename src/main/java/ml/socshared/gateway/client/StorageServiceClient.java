package ml.socshared.gateway.client;

import ml.socshared.gateway.domain.RestResponsePage;
import ml.socshared.gateway.domain.storage.request.GroupRequest;
import ml.socshared.gateway.domain.storage.request.PublicationRequest;
import ml.socshared.gateway.domain.storage.response.GroupResponse;
import ml.socshared.gateway.domain.storage.response.PublicationResponse;
import ml.socshared.gateway.domain.storage.SocialNetwork;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@FeignClient(name="StorageServiceClient", url = "${feign.url.storage}/api/v1")
public interface StorageServiceClient {

    @GetMapping(value = "/private/groups/{groupId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public GroupResponse getGroupInfoById(@PathVariable UUID groupId,
                                          @RequestHeader("Authorization") String token);


    @GetMapping(value = "/private/users/{userId}/groups", produces = MediaType.APPLICATION_JSON_VALUE)
    public RestResponsePage<GroupResponse> getGroupsByUserId(@PathVariable UUID userId,
                                                 @NotNull @RequestParam(name = "page", required = false) Integer page,
                                                 @NotNull @RequestParam(name = "size", required = false) Integer size,
                                                             @RequestHeader("Authorization") String token);


    @GetMapping(value = "/private/users/{userId}/groups/social_network/{socialNetwork}/groups", produces = MediaType.APPLICATION_JSON_VALUE)
    public RestResponsePage<GroupResponse> getGroupsByUserAndSocial(@PathVariable UUID userId, @PathVariable SocialNetwork socialNetwork,
                                                         @NotNull @RequestParam(name = "page", required = false) Integer page,
                                                         @NotNull @RequestParam(name = "size", required = false) Integer size,
                                                                    @RequestHeader("Authorization") String token);

    @GetMapping(value = "/private/users/{userId}/groups/facebook/{facebookId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public GroupResponse getUserFbGroupByFbId(@PathVariable UUID userId, @PathVariable String facebookId,
                                              @RequestHeader("Authorization") String token);

    @GetMapping(value = "/private/users/{userId}/groups/vk/{vkId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public GroupResponse getUserVkGroupsByVkId(@PathVariable UUID userId, @PathVariable String vkId,
                                               @RequestHeader("Authorization") String token);

    @PostMapping(value = "/private/groups", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public GroupResponse addGroup(@RequestBody GroupRequest request,
                                  @RequestHeader("Authorization") String token);

    @DeleteMapping(value = "/private/groups/{groupId}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void deleteGroup(@PathVariable UUID groupId,
                            @RequestHeader("Authorization") String token);


    @PostMapping(value = "/private/publications", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public PublicationResponse addNewPublication(@RequestBody PublicationRequest request,
                                                 @RequestHeader("Authorization") String token);


    @GetMapping(value = "/private/publications")
    public RestResponsePage<PublicationResponse> getAllPublicationAfterDate(@NotNull @RequestParam(name = "after", required = false) Long afterDate,
                                            @NotNull @RequestParam(name = "page", required = false) Integer page,
                                            @NotNull @RequestParam(name = "size", required = false) Integer size,
                                                                            @RequestHeader("Authorization") String token);


    @GetMapping(value = "/private/publications/status/not_publishing")
    public RestResponsePage<PublicationResponse> getNotPublishing(@NotNull @RequestParam(name = "page", required = false) Integer page,
                                                    @NotNull @RequestParam(name = "size", required = false) Integer size,
                                                                  @RequestHeader("Authorization") String token);


    @GetMapping(value = "/private/groups/{systemGroupId}/publications/status/not_publishing")
    public RestResponsePage<PublicationResponse> getPublicationsByGroupId(@PathVariable UUID systemGroupId,
                                                @NotNull @RequestParam(name = "page", required = false) Integer page,
                                                @NotNull @RequestParam(name = "size", required = false) Integer size,
                                                                          @RequestHeader("Authorization") String token);
}
