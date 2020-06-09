package ml.socshared.gateway.service.impl;

import lombok.RequiredArgsConstructor;
import ml.socshared.gateway.client.FacebookClient;
import ml.socshared.gateway.domain.facebook.FacebookPage;
import ml.socshared.gateway.domain.facebook.response.FacebookCommentResponse;
import ml.socshared.gateway.domain.facebook.response.FacebookGroupResponse;
import ml.socshared.gateway.domain.facebook.response.FacebookPostResponse;
import ml.socshared.gateway.domain.response.SuccessResponse;
import ml.socshared.gateway.domain.facebook.response.AccessUrlResponse;
import ml.socshared.gateway.domain.response.UserResponse;
import ml.socshared.gateway.security.model.TokenObject;
import ml.socshared.gateway.service.FacebookService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FacebookServiceImpl implements FacebookService {

    private final FacebookClient client;

    @Value("#{tokenGetter.tokenFB}")
    private TokenObject tokenFB;

    private String tokenFB() {
        return "Bearer " + tokenFB.getToken();
    }

    @Override
    public AccessUrlResponse getURLForAccess() {
        return client.getAccessUrl(tokenFB());
    }

    @Override
    public SuccessResponse saveFacebookAccount(UUID systemUserId, String authorizationCode) {
        return client.saveFacebookAccount(systemUserId, authorizationCode, tokenFB());
    }

    @Override
    public UserResponse getUserDataFacebookAccount(UUID systemUserId) {
        return client.getUserDataFacebookAccount(systemUserId, tokenFB());
    }

    @Override
    public void deleteFacebookAccount(UUID systemUserId) {
        client.deleteFacebookAccount(systemUserId, tokenFB());
    }

    @Override
    public FacebookPage<FacebookGroupResponse> getGroups(UUID systemUserId, Integer page, Integer size) {
        return client.getGroups(systemUserId, page, size, tokenFB());
    }

    @Override
    public FacebookGroupResponse getGroup(UUID systemUserId, String pageId) {
        return client.getGroup(systemUserId, pageId, tokenFB());
    }

    @Override
    public FacebookPostResponse getPost(UUID systemUserId, String groupId, String postId) {
        return client.getPost(systemUserId, groupId, postId, tokenFB());
    }

    @Override
    public FacebookPage<FacebookPostResponse> getPosts(UUID systemUserId, String groupId, Integer page, Integer size) {
        return client.getPosts(systemUserId, groupId, page, size, tokenFB());
    }

    @Override
    public FacebookCommentResponse getCommentOfPost(UUID systemUserId, String groupId, String postId, String commentId) {
        return client.getCommentOfPost(systemUserId, groupId, postId, commentId, tokenFB());
    }

    @Override
    public FacebookPage<FacebookCommentResponse> getCommentsByPostId(UUID systemUserId, String groupId, String postId, Integer page, Integer size) {
        return client.getCommentsByPostId(systemUserId, groupId, postId, page, size, tokenFB());
    }

    @Override
    public FacebookCommentResponse getSubCommentOfComment(UUID systemUserId, String groupId, String postId, String commentId, String subCommentId) {
        return client.getSubCommentOfComment(systemUserId, groupId, postId, commentId, subCommentId, tokenFB());
    }

    @Override
    public FacebookPage<FacebookCommentResponse> getSubCommentsByCommentId(UUID systemUserId, String groupId, String postId, String commentId, Integer page, Integer size) {
        return client.getSubCommentsByCommentId(systemUserId, groupId, postId, commentId, page, size, tokenFB());
    }
}
