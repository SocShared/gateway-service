package ml.socshared.gateway.service.impl;

import lombok.RequiredArgsConstructor;
import ml.socshared.gateway.client.FacebookClient;
import ml.socshared.gateway.domain.SuccessResponse;
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

    @Override
    public AccessUrlResponse getURLForAccess() {
        return client.getAccessUrl("Bearer " + tokenFB.getToken());
    }

    @Override
    public SuccessResponse saveAccountFacebook(UUID systemUserId, String authorizationCode) {
        return client.saveAccountFacebook(systemUserId, authorizationCode, "Bearer " + tokenFB.getToken());
    }

    @Override
    public UserResponse getUserDataFacebookAccount(UUID systemUserId) {
        return client.getUserDataFacebookAccount(systemUserId, "Bearer " + tokenFB.getToken());
    }

    @Override
    public void deleteFacebookAccount(UUID systemUserId) {
        client.deleteFacebookAccount(systemUserId, "Bearer " + tokenFB.getToken());
    }
}
