package ml.socshared.gateway.client;

import ml.socshared.gateway.domain.response.facebook.AccessUrlResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "fb-client", url = "${feign.url.fb:}")
public interface FacebookClient {

    @GetMapping(value = "/api/v1/private/access", produces = MediaType.APPLICATION_JSON_VALUE)
    AccessUrlResponse getAccessUrl(@RequestHeader("Authorization") String token);

}
