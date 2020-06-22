package ml.socshared.gateway.service.impl;

import lombok.RequiredArgsConstructor;
import ml.socshared.gateway.client.AuthClient;
import ml.socshared.gateway.domain.RestResponsePage;
import ml.socshared.gateway.domain.client.request.NewClientRequest;
import ml.socshared.gateway.domain.client.response.ClientResponse;
import ml.socshared.gateway.domain.response.SuccessResponse;
import ml.socshared.gateway.domain.user.AuthUserResponse;
import ml.socshared.gateway.security.model.TokenObject;
import ml.socshared.gateway.service.AuthService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthClient authClient;

    @Value("#{tokenGetter.tokenAuth}")
    private TokenObject tokenAuth;

    private String tokenAuth() {
        return "Bearer " + tokenAuth.getToken();
    }

    @Override
    public RestResponsePage<ClientResponse> findAllClients(Integer page, Integer size) {
        return authClient.findAllClients(page, size, tokenAuth());
    }

    @Override
    public ClientResponse findByClientId(UUID clientId) {
        return authClient.findByClientId(clientId, tokenAuth());
    }

    @Override
    public ClientResponse findByUserIdAndClientId(UUID userId, UUID clientId) {
        return authClient.findByUserIdAndClientId(userId, clientId, tokenAuth());
    }

    @Override
    public RestResponsePage<ClientResponse> findByUserId(UUID userId, Integer page, Integer size) {
        return authClient.findByUserId(userId, page, size, tokenAuth());
    }

    @Override
    public ClientResponse addClient(UUID userId, NewClientRequest request) {
        return authClient.addClient(userId, request, tokenAuth());
    }

    @Override
    public void updateClient(UUID userId, UUID clientId, NewClientRequest request) {
        authClient.updateClient(userId, clientId, request, tokenAuth());
    }

    @Override
    public void deleteClient(UUID clientId) {
        authClient.deleteClient(clientId, tokenAuth());
    }

    @Override
    public AuthUserResponse getUserInfoById(UUID userId) {
        return authClient.getUserById(userId,  tokenAuth());
    }

    @Override
    public SuccessResponse sendMailConfirmed(UUID userId) {
        return authClient.sendMailConfirmed(userId, tokenAuth());
    }
}
