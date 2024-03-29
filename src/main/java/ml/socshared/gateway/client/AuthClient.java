package ml.socshared.gateway.client;

import ml.socshared.gateway.domain.RestResponsePage;
import ml.socshared.gateway.domain.client.request.NewClientRequest;
import ml.socshared.gateway.domain.client.response.ClientResponse;
import ml.socshared.gateway.domain.response.SuccessResponse;
import ml.socshared.gateway.domain.user.AuthUserResponse;
import ml.socshared.gateway.domain.user.UpdateUserRequest;
import ml.socshared.gateway.security.request.CheckTokenRequest;
import ml.socshared.gateway.security.request.ServiceTokenRequest;
import ml.socshared.gateway.security.response.ServiceTokenResponse;
import org.springframework.cloud.openfeign.FeignClient;
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
    RestResponsePage<ClientResponse> findAllClients(@RequestParam(name = "page", defaultValue = "0") Integer page,
                                        @RequestParam(name = "size", defaultValue = "10") Integer size,
                                        @RequestHeader("Authorization") String token);

    @GetMapping(value = "/api/v1/private/clients/{clientId}")
    ClientResponse findByClientId(@PathVariable UUID clientId, @RequestHeader("Authorization") String token);

    @GetMapping(value = "/api/v1/private/users/{userId}/clients/{clientId}")
    ClientResponse findByUserIdAndClientId(@PathVariable UUID userId, @PathVariable UUID clientId,
                                           @RequestHeader("Authorization") String token);

    @GetMapping(value = "/api/v1/private/users/{userId}/clients")
    RestResponsePage<ClientResponse> findByUserId(@PathVariable UUID userId,
                                                  @RequestParam(name = "page", defaultValue = "0") Integer page,
                                                  @RequestParam(name = "size", defaultValue = "10") Integer size,
                                                  @RequestHeader("Authorization") String token);

    @PostMapping(value = "/api/v1/private/users/{userId}/clients")
    ClientResponse addClient(@PathVariable UUID userId, @Valid @RequestBody NewClientRequest request,
                             @RequestHeader("Authorization") String token);

    @PutMapping(value = "/api/v1/private/users/{userId}/clients/{clientId}")
    void updateClient(@PathVariable UUID userId, @PathVariable UUID clientId,
                                @Valid @RequestBody NewClientRequest request,
                                @RequestHeader("Authorization") String token);

    @DeleteMapping(value = "/api/v1/private/clients/{clientId}")
    void deleteClient(@PathVariable UUID clientId, @RequestHeader("Authorization") String token);

    @GetMapping(value = "/api/v1/private/users/{userId}")
    AuthUserResponse getUserById(@PathVariable UUID userId, @RequestHeader("Authorization") String token);

    @PostMapping(value = "/api/v1/private/users/{userId}/mail/confirmed")
    SuccessResponse sendMailConfirmed(@PathVariable UUID userId, @RequestHeader("Authorization") String token);

    @PutMapping(value = "/api/v1/private/users/{userId}")
    void updateUser(@PathVariable UUID userId, @RequestBody UpdateUserRequest request,
                    @RequestHeader("Authorization") String token);

}
