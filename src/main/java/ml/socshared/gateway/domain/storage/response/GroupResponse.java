package ml.socshared.gateway.domain.storage.response;

import lombok.*;
import ml.socshared.gateway.domain.storage.SocialNetwork;

import java.util.UUID;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class GroupResponse {

    private String groupId;
    private SocialNetwork socialNetwork;
    private String name;
    private String vkId;
    private String fbId;
    private UUID userId;

}