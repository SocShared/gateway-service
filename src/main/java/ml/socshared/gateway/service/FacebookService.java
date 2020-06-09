package ml.socshared.gateway.service;

import ml.socshared.gateway.domain.facebook.FacebookPage;
import ml.socshared.gateway.domain.facebook.response.FacebookCommentResponse;
import ml.socshared.gateway.domain.facebook.response.FacebookGroupResponse;
import ml.socshared.gateway.domain.facebook.response.FacebookPostResponse;
import ml.socshared.gateway.domain.response.SuccessResponse;
import ml.socshared.gateway.domain.facebook.response.AccessUrlResponse;
import ml.socshared.gateway.domain.response.UserResponse;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

public interface FacebookService {

    AccessUrlResponse getURLForAccess();
    SuccessResponse saveFacebookAccount(UUID systemUserId, String authorization);
    UserResponse getUserDataFacebookAccount(UUID systemUserId);
    void deleteFacebookAccount(UUID systemUserId);
    FacebookPage<FacebookGroupResponse> getGroups(UUID systemUserId, Integer page, Integer size);
    FacebookGroupResponse getGroup(UUID systemUserId, String pageId);
    FacebookPostResponse getPost(UUID systemUserId, String groupId, String postId);
    FacebookPage<FacebookPostResponse> getPosts(UUID systemUserId, String groupId, Integer page, Integer size);
    FacebookCommentResponse getCommentOfPost(UUID systemUserId, String groupId, String postId, String commentId);
    FacebookPage<FacebookCommentResponse> getCommentsByPostId(UUID systemUserId, String groupId, String postId, Integer page, Integer size);
    FacebookCommentResponse getSubCommentOfComment(UUID systemUserId, String groupId, String postId, String commentId, String subCommentId);
    FacebookPage<FacebookCommentResponse> getSubCommentsByCommentId(UUID systemUserId, String groupId, String postId, String commentId, Integer page, Integer size);

}
