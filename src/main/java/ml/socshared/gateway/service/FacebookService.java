package ml.socshared.gateway.service;

import ml.socshared.gateway.domain.SuccessResponse;
import ml.socshared.gateway.domain.facebook.FacebookPage;
import ml.socshared.gateway.domain.facebook.response.AccessUrlResponse;
import ml.socshared.gateway.domain.facebook.response.FacebookGroupResponse;
import ml.socshared.gateway.domain.response.UserResponse;

import java.util.UUID;

public interface FacebookService {

    AccessUrlResponse getURLForAccess();
    SuccessResponse saveAccountFacebook(UUID systemUserId, String authorization);
    UserResponse getUserDataFacebookAccount(UUID systemUserId);
    FacebookPage<FacebookGroupResponse> getGroups(UUID systemUserId, Integer page, Integer size);
    void deleteFacebookAccount(UUID systemUserId);

}
