package ml.socshared.gateway.controller.v1;

import lombok.RequiredArgsConstructor;
import ml.socshared.gateway.domain.response.SuccessResponse;
import ml.socshared.gateway.domain.facebook.FacebookPage;
import ml.socshared.gateway.domain.facebook.response.AccessUrlResponse;
import ml.socshared.gateway.domain.facebook.response.FacebookGroupResponse;
import ml.socshared.gateway.domain.response.UserResponse;
import ml.socshared.gateway.security.jwt.JwtTokenProvider;
import ml.socshared.gateway.service.FacebookService;
import ml.socshared.gateway.service.StorageService;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ml.socshared.gateway.api.v1.rest.FacebookApi;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping(value = "/api/v1")
@RequiredArgsConstructor
@PreAuthorize("isAuthenticated()")
@Validated
public class FacebookController implements FacebookApi {

    private final FacebookService facebookService;
    private final StorageService storageService;
    private final JwtTokenProvider jwtTokenProvider;

    @PreAuthorize("hasRole('CONTENT_MANAGER')")
    @GetMapping(value = "/protected/facebook/access", produces = MediaType.APPLICATION_JSON_VALUE)
    public AccessUrlResponse getAccessUrl() {
        return facebookService.getURLForAccess();
    }

    @PreAuthorize("hasRole('CONTENT_MANAGER')")
    @GetMapping(value = "/protected/facebook/connect/{authorizationCode}", produces = MediaType.APPLICATION_JSON_VALUE)
    public SuccessResponse saveAccountFacebook(@PathVariable String authorizationCode, HttpServletRequest request) {
        return facebookService.saveFacebookAccount(jwtTokenProvider.getUserId(jwtTokenProvider.resolveToken(request)), authorizationCode);
    }

    @PreAuthorize("hasRole('CONTENT_MANAGER')")
    @GetMapping(value = "/protected/facebook/account", produces = MediaType.APPLICATION_JSON_VALUE)
    public UserResponse getUserDataFacebookAccount(HttpServletRequest request) {
        return facebookService.getUserDataFacebookAccount(jwtTokenProvider.getUserId(jwtTokenProvider.resolveToken(request)));
    }

    @PreAuthorize("hasRole('CONTENT_MANAGER')")
    @GetMapping(value = "/protected/facebook/groups", produces = MediaType.APPLICATION_JSON_VALUE)
    public FacebookPage<FacebookGroupResponse> getGroups(@Min(0) @NotNull @RequestParam(name = "page", required = false) Integer page,
                                                         @Min(0) @Max(100) @NotNull @RequestParam(name = "size", required = false) Integer size,
                                                         HttpServletRequest request) {
        return storageService.getGroupsFacebookWithSelected(jwtTokenProvider.getUserId(jwtTokenProvider.resolveToken(request)), PageRequest.of(page, size));
    }

    @PreAuthorize("hasRole('CONTENT_MANAGER')")
    @DeleteMapping(value = "/protected/facebook/account")
    public void deleteFacebookAccount(HttpServletRequest request) {
        facebookService.deleteFacebookAccount(jwtTokenProvider.getUserId(jwtTokenProvider.resolveToken(request)));
    }

}
