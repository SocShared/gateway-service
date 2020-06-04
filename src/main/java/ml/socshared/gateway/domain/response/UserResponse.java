package ml.socshared.gateway.domain.response;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ml.socshared.gateway.domain.storage.SocialNetwork;

import java.util.UUID;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class UserResponse {
    private UUID systemUserId;
    private String accountId;
    private String firstName;
    private String lastName;
    private String email;
    private SocialNetwork socialNetwork;
}
