package ml.socshared.gateway.service;

import ml.socshared.gateway.domain.client.request.NewClientRequest;
import ml.socshared.gateway.domain.client.response.ClientResponse;
import org.springframework.data.domain.Page;

import java.util.UUID;

public interface AuthService {

    Page<ClientResponse> findAllClients(Integer page, Integer size);
    ClientResponse findByClientId(UUID clientId);
    ClientResponse findByUserIdAndClientId(UUID userId, UUID clientId);
    Page<ClientResponse> findByUserId(UUID userId, Integer page, Integer size);
    ClientResponse addClient(UUID userId, NewClientRequest request);
    ClientResponse updateClient(UUID userId, UUID clientId, NewClientRequest request);
    void deleteClient(UUID clientId);

}