package ml.socshared.gateway.domain.client.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ml.socshared.gateway.domain.client.response.ClientResponse;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
public class NewClientRequest {

    private String name;

    @NotNull
    private ClientResponse.AccessType accessType;

    private String validRedirectUri;

}
