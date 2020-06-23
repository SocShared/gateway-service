package ml.socshared.gateway.service;

import ml.socshared.gateway.domain.RestResponsePage;
import ml.socshared.gateway.domain.client.request.NewClientRequest;
import ml.socshared.gateway.domain.client.response.ClientResponse;

import ml.socshared.gateway.domain.response.SuccessResponse;
import ml.socshared.gateway.domain.user.AuthUserResponse;
import ml.socshared.gateway.domain.user.UpdateUserRequest;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;


import java.util.UUID;

public interface AuthService {

    RestResponsePage<ClientResponse> findAllClients(Integer page, Integer size);
    ClientResponse findByClientId(UUID clientId);
    ClientResponse findByUserIdAndClientId(UUID userId, UUID clientId);
    RestResponsePage<ClientResponse> findByUserId(UUID userId, Integer page, Integer size);
    ClientResponse addClient(UUID userId, NewClientRequest request);
    void updateClient(UUID userId, UUID clientId, NewClientRequest request);
    void deleteClient(UUID clientId);
    AuthUserResponse getUserInfoById(UUID userId);
    SuccessResponse sendMailConfirmed(UUID userId);
    void updateUser(UUID userId, UpdateUserRequest request);

}
