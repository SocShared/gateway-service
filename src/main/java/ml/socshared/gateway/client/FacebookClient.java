package ml.socshared.gateway.client;

import ml.socshared.gateway.domain.SuccessResponse;
import ml.socshared.gateway.domain.facebook.response.AccessUrlResponse;
import ml.socshared.gateway.domain.response.UserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.UUID;

@FeignClient(name = "fb-client", url = "${feign.url.fb:}")
public interface FacebookClient {

    @GetMapping(value = "/api/v1/private/access", produces = MediaType.APPLICATION_JSON_VALUE)
    AccessUrlResponse getAccessUrl(@RequestHeader("Authorization") String token);

    @GetMapping(value = "/api/v1/private/users/{systemUserId}/code/{authorizationCode}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    SuccessResponse saveAccountFacebook(@PathVariable UUID systemUserId, @PathVariable String authorizationCode,
                                        @RequestHeader("Authorization") String token);

    @GetMapping(value = "/api/v1/private/users/{systemUserId}/facebook/data", produces = MediaType.APPLICATION_JSON_VALUE)
    UserResponse getUserDataFacebookAccount(@PathVariable UUID systemUserId, @RequestHeader("Authorization") String token);

    @DeleteMapping(value = "/api/v1/private/users/{systemUserId}/facebook")
    void deleteFacebookAccount(@PathVariable UUID systemUserId, @RequestHeader("Authorization") String token);
}
