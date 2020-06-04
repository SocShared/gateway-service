package ml.socshared.gateway.controller.v1;

import lombok.RequiredArgsConstructor;
import ml.socshared.gateway.domain.response.UserResponse;
import ml.socshared.gateway.security.jwt.JwtTokenProvider;
import ml.socshared.gateway.service.SocialService;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1")
@RequiredArgsConstructor
@PreAuthorize("isAuthenticated()")
public class SocialController {

    private final SocialService socialService;
    private final JwtTokenProvider jwtTokenProvider;

    @PreAuthorize("hasRole('CONTENT_MANAGER')")
    @GetMapping(value = "/protected/social/accounts", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<UserResponse> getAccounts(HttpServletRequest request) {
        return socialService.getAccountsSocialNetwork(jwtTokenProvider.getUserId(jwtTokenProvider.resolveToken(request)));
    }

}
