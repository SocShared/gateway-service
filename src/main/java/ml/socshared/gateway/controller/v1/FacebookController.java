package ml.socshared.gateway.controller.v1;

import lombok.RequiredArgsConstructor;
import ml.socshared.gateway.domain.response.facebook.AccessUrlResponse;
import ml.socshared.gateway.service.FacebookService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ml.socshared.gateway.api.v1.rest.FacebookApi;

@RestController
@RequestMapping(value = "/api/v1")
@RequiredArgsConstructor
public class FacebookController implements FacebookApi {

    private final FacebookService facebookService;

    @GetMapping(value = "/facebook/access", produces = MediaType.APPLICATION_JSON_VALUE)
    public AccessUrlResponse getAccessUrl() {
        return facebookService.getURLForAccess();
    }


}
