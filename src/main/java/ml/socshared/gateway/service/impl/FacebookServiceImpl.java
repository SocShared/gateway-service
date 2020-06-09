package ml.socshared.gateway.service.impl;

import lombok.RequiredArgsConstructor;
import ml.socshared.gateway.client.FacebookClient;
import ml.socshared.gateway.client.StorageServiceClient;
import ml.socshared.gateway.domain.facebook.FacebookPage;
import ml.socshared.gateway.domain.facebook.response.FacebookCommentResponse;
import ml.socshared.gateway.domain.facebook.response.FacebookGroupResponse;
import ml.socshared.gateway.domain.facebook.response.FacebookPostResponse;
import ml.socshared.gateway.domain.response.SuccessResponse;
import ml.socshared.gateway.domain.facebook.response.AccessUrlResponse;
import ml.socshared.gateway.domain.response.UserResponse;
import ml.socshared.gateway.security.model.TokenObject;
import ml.socshared.gateway.service.FacebookService;
import ml.socshared.gateway.service.StorageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FacebookServiceImpl implements FacebookService {

    private final FacebookClient facebookClient;
    private final StorageServiceClient storageClient;

    @Value("#{tokenGetter.tokenFB}")
    private TokenObject tokenFB;

    @Value("#{tokenGetter.tokenStorageService}")
    private TokenObject tokenStorage;

    private String tokenFB() {
        return "Bearer " + tokenFB.getToken();
    }

    private String tokenStorage() {
        return "Bearer " + tokenStorage.getToken();
    }

    @Override
    public AccessUrlResponse getURLForAccess() {
        return facebookClient.getAccessUrl(tokenFB());
    }

    @Override
    public SuccessResponse saveFacebookAccount(UUID systemUserId, String authorizationCode) {
        return facebookClient.saveFacebookAccount(systemUserId, authorizationCode, tokenFB());
    }

    @Override
    public UserResponse getUserDataFacebookAccount(UUID systemUserId) {
        return facebookClient.getUserDataFacebookAccount(systemUserId, tokenFB());
    }

    @Override
    public void deleteFacebookAccount(UUID systemUserId) {
        storageClient.deleteFacebookGroupsByUserId(systemUserId, tokenStorage.getToken());
        facebookClient.deleteFacebookAccount(systemUserId, tokenFB());
    }

    @Override
    public FacebookPage<FacebookGroupResponse> getGroups(UUID systemUserId, Integer page, Integer size) {
        return facebookClient.getGroups(systemUserId, page, size, tokenFB());
    }

    @Override
    public FacebookGroupResponse getGroup(UUID systemUserId, String pageId) {
        return facebookClient.getGroup(systemUserId, pageId, tokenFB());
    }

    @Override
    public FacebookPostResponse getPost(UUID systemUserId, String groupId, String postId) {
        return facebookClient.getPost(systemUserId, groupId, postId, tokenFB());
    }

    @Override
    public FacebookPage<FacebookPostResponse> getPosts(UUID systemUserId, String groupId, Integer page, Integer size) {
        return facebookClient.getPosts(systemUserId, groupId, page, size, tokenFB());
    }

    @Override
    public FacebookCommentResponse getCommentOfPost(UUID systemUserId, String groupId, String postId, String commentId) {
        return facebookClient.getCommentOfPost(systemUserId, groupId, postId, commentId, tokenFB());
    }

    @Override
    public FacebookPage<FacebookCommentResponse> getCommentsByPostId(UUID systemUserId, String groupId, String postId, Integer page, Integer size) {
        return facebookClient.getCommentsByPostId(systemUserId, groupId, postId, page, size, tokenFB());
    }

    @Override
    public FacebookCommentResponse getSubCommentOfComment(UUID systemUserId, String groupId, String postId, String commentId, String subCommentId) {
        return facebookClient.getSubCommentOfComment(systemUserId, groupId, postId, commentId, subCommentId, tokenFB());
    }

    @Override
    public FacebookPage<FacebookCommentResponse> getSubCommentsByCommentId(UUID systemUserId, String groupId, String postId, String commentId, Integer page, Integer size) {
        return facebookClient.getSubCommentsByCommentId(systemUserId, groupId, postId, commentId, page, size, tokenFB());
    }
}
