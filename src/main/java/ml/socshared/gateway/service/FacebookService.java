package ml.socshared.gateway.service;

import ml.socshared.gateway.domain.response.SuccessResponse;
import ml.socshared.gateway.domain.facebook.response.AccessUrlResponse;
import ml.socshared.gateway.domain.response.UserResponse;

import java.util.UUID;

public interface FacebookService {

    AccessUrlResponse getURLForAccess();
    SuccessResponse saveAccountFacebook(UUID systemUserId, String authorization);
    UserResponse getUserDataFacebookAccount(UUID systemUserId);
    void deleteFacebookAccount(UUID systemUserId);

}
