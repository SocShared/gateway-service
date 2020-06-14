package ml.socshared.gateway.client;

import ml.socshared.gateway.domain.facebook.response.FacebookCommentResponse;
import ml.socshared.gateway.domain.facebook.response.FacebookPostResponse;
import ml.socshared.gateway.domain.response.SuccessResponse;
import ml.socshared.gateway.domain.facebook.FacebookPage;
import ml.socshared.gateway.domain.facebook.response.AccessUrlResponse;
import ml.socshared.gateway.domain.facebook.response.FacebookGroupResponse;
import ml.socshared.gateway.domain.response.UserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@FeignClient(name = "fb-client", url = "${feign.url.fb:}")
public interface FacebookClient {

    @GetMapping(value = "/api/v1/private/access", produces = MediaType.APPLICATION_JSON_VALUE)
    AccessUrlResponse getAccessUrl(@RequestHeader("Authorization") String token);

    @GetMapping(value = "/api/v1/private/users/{systemUserId}/code/{authorizationCode}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    SuccessResponse saveFacebookAccount(@PathVariable UUID systemUserId, @PathVariable String authorizationCode,
                                        @RequestHeader("Authorization") String token);

    @GetMapping(value = "/api/v1/private/users/{systemUserId}/facebook/data", produces = MediaType.APPLICATION_JSON_VALUE)
    UserResponse getUserDataFacebookAccount(@PathVariable UUID systemUserId, @RequestHeader("Authorization") String token);

    @DeleteMapping(value = "/api/v1/private/users/{systemUserId}/facebook")
    void deleteFacebookAccount(@PathVariable UUID systemUserId, @RequestHeader("Authorization") String token);

    @GetMapping(value = "/api/v1/private/users/{systemUserId}/groups", produces = MediaType.APPLICATION_JSON_VALUE)
    FacebookPage<FacebookGroupResponse> getGroups(@PathVariable UUID systemUserId,
                                                  @RequestParam(name = "page") Integer page,
                                                  @RequestParam(name = "size") Integer size,
                                                  @RequestHeader("Authorization") String token);

    @GetMapping(value = "/api/v1/private/users/{systemUserId}/groups/{pageId}", produces = MediaType.APPLICATION_JSON_VALUE)
    FacebookGroupResponse getGroup(@PathVariable UUID systemUserId, @PathVariable String pageId, @RequestHeader("Authorization") String token);

    @GetMapping(value = "/api/v1/private/users/{systemUserId}/groups/{groupId}/posts/{postId}", produces = MediaType.APPLICATION_JSON_VALUE)
    FacebookPostResponse getPost(@PathVariable UUID systemUserId, @PathVariable String groupId, @PathVariable String postId,
                                 @RequestHeader("Authorization") String token);

    @GetMapping(value = "/api/v1/private/users/{systemUserId}/groups/{groupId}/posts", produces = MediaType.APPLICATION_JSON_VALUE)
    FacebookPage<FacebookPostResponse> getPosts(@PathVariable UUID systemUserId, @PathVariable String groupId,
                                                @RequestParam(name = "page") Integer page,
                                                @RequestParam(name = "size") Integer size,
                                                @RequestHeader("Authorization") String token);

    @GetMapping(value = "/api/v1/private/users/{systemUserId}/groups/{groupId}/posts/{postId}/comments/{commentId}", produces = MediaType.APPLICATION_JSON_VALUE)
    FacebookCommentResponse getCommentOfPost(@PathVariable UUID systemUserId, @PathVariable String groupId,
                                             @PathVariable String postId, @PathVariable String commentId,
                                             @RequestHeader("Authorization") String token);

    @GetMapping(value = "/api/v1/private/users/{systemUserId}/groups/{groupId}/posts/{postId}/comments", produces = MediaType.APPLICATION_JSON_VALUE)
    FacebookPage<FacebookCommentResponse> getCommentsByPostId(@PathVariable UUID systemUserId, @PathVariable String groupId,
                                                              @PathVariable String postId,
                                                              @RequestParam(value = "page") Integer page,
                                                              @RequestParam(value = "size") Integer size,
                                                              @RequestHeader("Authorization") String token);

    @GetMapping(value = "/api/v1/private/users/{systemUserId}/groups/{groupId}/posts/{postId}/comments/{commentId}/sub_comments/{subCommentId}")
    FacebookCommentResponse getSubCommentOfComment(@PathVariable UUID systemUserId, @PathVariable String groupId, @PathVariable String postId,
                                                   @PathVariable String commentId, @PathVariable String subCommentId,
                                                   @RequestHeader("Authorization") String token);

    @GetMapping(value = "/api/v1/private/users/{systemUserId}/groups/{groupId}/posts/{postId}/comments/{commentId}/sub_comments")
    FacebookPage<FacebookCommentResponse> getSubCommentsByCommentId(@PathVariable UUID systemUserId, @PathVariable String groupId,
                                                                    @PathVariable String postId, @PathVariable String commentId,
                                                                    @RequestParam(value = "page") Integer page,
                                                                    @RequestParam(value = "size") Integer size,
                                                                    @RequestHeader("Authorization") String token);
}
