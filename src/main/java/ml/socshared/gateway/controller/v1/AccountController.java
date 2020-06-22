package ml.socshared.gateway.controller.v1;

import lombok.RequiredArgsConstructor;
import ml.socshared.gateway.domain.response.UserResponse;
import ml.socshared.gateway.domain.user.AuthUserResponse;
import ml.socshared.gateway.security.jwt.JwtTokenProvider;
import ml.socshared.gateway.service.AuthService;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = "/api/v1", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@RequiredArgsConstructor
@Validated
@PreAuthorize("isAuthenticated()")
public class AccountController {

    private final AuthService authService;
    private final JwtTokenProvider jwtTokenProvider;

    @PreAuthorize("hasRole('CONTENT_MANAGER')")
    @GetMapping(value = "/protected/users/info")
    public AuthUserResponse getUserResponseInfo(HttpServletRequest request) {
        return authService.getUserInfoById(jwtTokenProvider.getUserId(jwtTokenProvider.resolveToken(request)));
    }

}
