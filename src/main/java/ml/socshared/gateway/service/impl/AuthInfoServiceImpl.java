package ml.socshared.gateway.service.impl;

import lombok.RequiredArgsConstructor;
import ml.socshared.gateway.client.AuthInfoClient;
import ml.socshared.gateway.domain.user.UserResponse;
import ml.socshared.gateway.security.model.TokenObject;
import ml.socshared.gateway.service.AuthInfoService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthInfoServiceImpl implements AuthInfoService {

    private final AuthInfoClient authInfoClient;

    @Value("#{tokenGetter.tokenAuth}")
    TokenObject authToken;

    @Override
    public UserResponse getClientInfoById(UUID clientId) {
        return authInfoClient.getUserById(clientId, "Bearer " + authToken.getToken());
    }
}
