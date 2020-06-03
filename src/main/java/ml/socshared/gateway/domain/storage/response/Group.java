package ml.socshared.gateway.domain.storage.response;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import ml.socshared.gateway.domain.storage.SocialNetwork;

import javax.persistence.*;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
public class Group extends BaseEntity {

    private UUID groupId;

    private UUID userId;

    private String name;

    private SocialNetwork socialNetwork;

    private String facebookId;

    private String vkId;
    private Set<Publication> publications;

}
