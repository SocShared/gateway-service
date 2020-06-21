package ml.socshared.gateway.client;


import ml.socshared.gateway.domain.user.UserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.UUID;

@FeignClient(name = "auth-client", url = "${feign.url.auth:}")
public interface AuthInfoClient {

    @GetMapping(value = "/private/users/{userId}")
    UserResponse getUserById(@PathVariable UUID userId,
                             @RequestHeader("Authorization") String token);

}
