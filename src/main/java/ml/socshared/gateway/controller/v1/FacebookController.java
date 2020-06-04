package ml.socshared.gateway.controller.v1;

import lombok.RequiredArgsConstructor;
import ml.socshared.gateway.domain.SuccessResponse;
import ml.socshared.gateway.domain.facebook.response.AccessUrlResponse;
import ml.socshared.gateway.domain.response.UserResponse;
import ml.socshared.gateway.security.jwt.JwtTokenProvider;
import ml.socshared.gateway.service.FacebookService;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ml.socshared.gateway.api.v1.rest.FacebookApi;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api/v1")
@RequiredArgsConstructor
@PreAuthorize("isAuthenticated()")
public class FacebookController implements FacebookApi {

    private final FacebookService facebookService;
    private final JwtTokenProvider jwtTokenProvider;

    @PreAuthorize("hasRole('CONTENT_MANAGER')")
    @GetMapping(value = "/protected/facebook/access", produces = MediaType.APPLICATION_JSON_VALUE)
    public AccessUrlResponse getAccessUrl() {
        return facebookService.getURLForAccess();
    }

    @PreAuthorize("hasRole('CONTENT_MANAGER')")
    @GetMapping(value = "/protected/facebook/connect/{authorizationCode}", produces = MediaType.APPLICATION_JSON_VALUE)
    public SuccessResponse saveAccountFacebook(@PathVariable String authorizationCode, HttpServletRequest request) {
        return facebookService.saveAccountFacebook(jwtTokenProvider.getUserId(jwtTokenProvider.resolveToken(request)), authorizationCode);
    }

    @PreAuthorize("hasRole('CONTENT_MANAGER')")
    @GetMapping(value = "/protected/facebook/account", produces = MediaType.APPLICATION_JSON_VALUE)
    public UserResponse getUserDataFacebookAccount(HttpServletRequest request) {
        return facebookService.getUserDataFacebookAccount(jwtTokenProvider.getUserId(jwtTokenProvider.resolveToken(request)));
    }

    @PreAuthorize("hasRole('CONTENT_MANAGER')")
    @DeleteMapping(value = "/protected/facebook/account")
    public void deleteFacebookAccount(HttpServletRequest request) {
        facebookService.deleteFacebookAccount(jwtTokenProvider.getUserId(jwtTokenProvider.resolveToken(request)));
    }

}
