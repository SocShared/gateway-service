package ml.socshared.gateway.service.impl;

import lombok.RequiredArgsConstructor;
import ml.socshared.gateway.client.FacebookClient;
import ml.socshared.gateway.domain.response.facebook.AccessUrlResponse;
import ml.socshared.gateway.security.model.TokenObject;
import ml.socshared.gateway.service.FacebookService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

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
}
