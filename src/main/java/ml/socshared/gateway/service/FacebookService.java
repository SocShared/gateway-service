package ml.socshared.gateway.service;

import ml.socshared.gateway.domain.SuccessResponse;
import ml.socshared.gateway.domain.facebook.response.AccessUrlResponse;

import java.util.UUID;

public interface FacebookService {

    AccessUrlResponse getURLForAccess();
    SuccessResponse saveAccountFacebook(UUID systemUserId, String authorization);

}
