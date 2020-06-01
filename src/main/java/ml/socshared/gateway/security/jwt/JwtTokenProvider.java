package ml.socshared.gateway.security.jwt;

import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ml.socshared.gateway.domain.model.SpringUserDetails;
import ml.socshared.gateway.security.model.ServiceDetails;
import ml.socshared.gateway.security.client.AuthClient;
import ml.socshared.gateway.security.request.CheckTokenRequest;
import ml.socshared.gateway.security.request.ServiceTokenRequest;
import ml.socshared.gateway.security.response.ServiceTokenResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class JwtTokenProvider {

    @Value("${jwt.secret}")
    private String secretKey;

    private final AuthClient authClient;

    public UserDetails getUserDetails(String token) {
        Claims claims =  Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
        ArrayList<String> roles = claims.get("roles", ArrayList.class);

        return SpringUserDetails.builder()
                .authorities(roles.stream().map(role -> new SimpleGrantedAuthority("ROLE_" + role)).collect(Collectors.toList()))
                .username(claims.get("username", String.class))
                .firstName(claims.get("firstname", String.class))
                .lastName(claims.get("lastname", String.class))
                .email(claims.get("email", String.class))
                .accountNonLocked(claims.get("account_non_locked", Boolean.class))
                .build();
    }

    public boolean validateAccessToken(String token) {
        try {
            Jws<Claims> claims = getJwsClaimsFromToken(token);

            Date date = claims.getBody().getExpiration();
            if (date.before(new Date())) {
                log.warn("JWT Token is expired.");
                return false;
            }

            CheckTokenRequest request = new CheckTokenRequest();
            request.setToken(token);

            return authClient.send(request).getSuccess();
        } catch (JwtException | IllegalArgumentException exc) {
            if (exc instanceof ExpiredJwtException) {
                log.warn("JWT Token is expired.");
            } else {
                log.warn("JWT Token is invalid.");
            }
            return false;
        }
    }

    public boolean validateServiceToken(String token) {
        try {
            Jws<Claims> claims = getJwsClaimsFromToken(token);

            Date date = claims.getBody().getExpiration();
            if (date.before(new Date())) {
                log.warn("JWT Token is expired.");
                return false;
            }

            return true;
        } catch (JwtException | IllegalArgumentException exc) {
            if (exc instanceof ExpiredJwtException) {
                log.warn("JWT Token is expired.");
            } else {
                log.warn("JWT Token is invalid.");
            }
            return false;
        }
    }

    public ServiceTokenResponse buildServiceToken(ServiceTokenRequest request) {
        return authClient.getServiceToken(request);
    }

    private Jws<Claims> getJwsClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
    }

    public String resolveToken(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer")) {
            return token.substring(6).trim();
        }
        return null;
    }
}
