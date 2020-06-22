package ml.socshared.gateway.controller.v1;

import lombok.RequiredArgsConstructor;
import ml.socshared.gateway.domain.RestResponsePage;
import ml.socshared.gateway.domain.client.request.NewClientRequest;
import ml.socshared.gateway.domain.client.response.ClientResponse;
import ml.socshared.gateway.security.jwt.JwtTokenProvider;
import ml.socshared.gateway.service.AuthService;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api/v1", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@RequiredArgsConstructor
@Validated
@PreAuthorize("isAuthenticated()")
public class ClientController {

    private final AuthService authService;
    private final JwtTokenProvider jwtTokenProvider;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(value = "/protected/clients")
    public RestResponsePage<ClientResponse> findAllClients(@RequestParam(name = "page", defaultValue = "0") Integer page,
                                                           @RequestParam(name = "size", defaultValue = "10") Integer size) {
        return authService.findAllClients(page, size);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(value = "/protected/clients/{clientId}")
    public ClientResponse findByClientId(@PathVariable UUID clientId) {
        return authService.findByClientId(clientId);
    }

    @PreAuthorize("hasRole('CONTENT_MANAGER')")
    @GetMapping(value = "/protected/users/clients/{clientId}")
    public ClientResponse findByUserIdAndClientId(@PathVariable UUID clientId, HttpServletRequest request) {
        return authService.findByUserIdAndClientId(jwtTokenProvider.getUserId(jwtTokenProvider.resolveToken(request)), clientId);
    }

    @PreAuthorize("hasRole('CONTENT_MANAGER')")
    @GetMapping(value = "/protected/users/clients")
    public RestResponsePage<ClientResponse> findByUserId(@RequestParam(name = "page", defaultValue = "0") Integer page,
                                             @RequestParam(name = "size", defaultValue = "10") Integer size,
                                             HttpServletRequest request) {
        return authService.findByUserId(jwtTokenProvider.getUserId(jwtTokenProvider.resolveToken(request)), page, size);
    }

    @PreAuthorize("hasRole('CONTENT_MANAGER')")
    @PostMapping(value = "/protected/users/clients")
    public ClientResponse addClient(@Valid @RequestBody NewClientRequest newClientRequest,
                                    HttpServletRequest request) {
        return authService.addClient(jwtTokenProvider.getUserId(jwtTokenProvider.resolveToken(request)), newClientRequest);
    }

    @PreAuthorize("hasRole('CONTENT_MANAGER')")
    @PutMapping(value = "/protected/users/clients/{clientId}")
    public void updateClient(@PathVariable UUID clientId, @Valid @RequestBody NewClientRequest newClientRequest,
                                       HttpServletRequest request) {
        authService.updateClient(jwtTokenProvider.getUserId(jwtTokenProvider.resolveToken(request)), clientId, newClientRequest);
    }

}
