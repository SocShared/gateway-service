package ml.socshared.gateway.client;

import ml.socshared.gateway.domain.client.request.NewClientRequest;
import ml.socshared.gateway.domain.client.response.ClientResponse;
import ml.socshared.gateway.domain.response.SuccessResponse;
import ml.socshared.gateway.security.request.CheckTokenRequest;
import ml.socshared.gateway.security.request.ServiceTokenRequest;
import ml.socshared.gateway.security.response.ServiceTokenResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@FeignClient(name = "auth-client", url = "${feign.url.auth:}")
public interface AuthClient {

    @PostMapping(value = "/oauth/validate_token", produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    SuccessResponse send(@RequestBody CheckTokenRequest request);

    @PostMapping(value = "/api/v1/public/service/token", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    ServiceTokenResponse getServiceToken(@RequestBody ServiceTokenRequest request);

    @GetMapping(value = "/api/v1/private/clients")
    Page<ClientResponse> findAllClients(@RequestParam(name = "page", defaultValue = "0") Integer page,
                                        @RequestParam(name = "size", defaultValue = "10") Integer size,
                                        @RequestHeader("Authorization") String token);

    @GetMapping(value = "/api/v1/private/clients/{clientId}")
    ClientResponse findByClientId(@PathVariable UUID clientId, @RequestHeader("Authorization") String token);

    @GetMapping(value = "/api/v1/private/users/{userId}/clients/{clientId}")
    ClientResponse findByUserIdAndClientId(@PathVariable UUID userId, @PathVariable UUID clientId,
                                           @RequestHeader("Authorization") String token);

    @GetMapping(value = "/api/v1/private/users/{userId}/clients")
    Page<ClientResponse> findByUserId(@PathVariable UUID userId,
                                      @RequestParam(name = "page", defaultValue = "0") Integer page,
                                      @RequestParam(name = "size", defaultValue = "10") Integer size,
                                      @RequestHeader("Authorization") String token);

    @PostMapping(value = "/api/v1/private/users/{userId}/clients")
    ClientResponse addClient(@PathVariable UUID userId, @Valid @RequestBody NewClientRequest request,
                             @RequestHeader("Authorization") String token);

    @PatchMapping(value = "/api/v1/private/users/{userId}/clients/{clientId}")
    ClientResponse updateClient(@PathVariable UUID userId, @PathVariable UUID clientId,
                                @Valid @RequestBody NewClientRequest request,
                                @RequestHeader("Authorization") String token);

    @DeleteMapping(value = "/api/v1/private/clients/{clientId}")
    void deleteClient(@PathVariable UUID clientId, @RequestHeader("Authorization") String token);

}
