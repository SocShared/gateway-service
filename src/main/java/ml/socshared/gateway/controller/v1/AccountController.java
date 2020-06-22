package ml.socshared.gateway.controller.v1;

import lombok.RequiredArgsConstructor;
import ml.socshared.gateway.domain.response.SuccessResponse;
import ml.socshared.gateway.domain.response.UserResponse;
import ml.socshared.gateway.domain.user.AuthUserResponse;
import ml.socshared.gateway.domain.user.UpdateUserRequest;
import ml.socshared.gateway.security.jwt.JwtTokenProvider;
import ml.socshared.gateway.service.AuthService;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

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

    @PreAuthorize("hasRole('CONTENT_MANAGER')")
    @PostMapping(value = "/protected/users/mail/confirmed")
    public SuccessResponse sendMailConfirmed(HttpServletRequest request) {
        return authService.sendMailConfirmed(jwtTokenProvider.getUserId(jwtTokenProvider.resolveToken(request)));
    }

    @PreAuthorize("hasRole('CONTENT_MANAGER')")
    @PutMapping("/protected/users")
    public void updateUser(@Valid @RequestBody UpdateUserRequest updateUserRequest, HttpServletRequest request) {
       authService.updateUser(jwtTokenProvider.getUserId(jwtTokenProvider.resolveToken(request)), updateUserRequest);
    }
}
