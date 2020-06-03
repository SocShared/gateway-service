package ml.socshared.gateway.domain.storage.response;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;
import ml.socshared.gateway.domain.storage.GroupPostStatus;
import ml.socshared.gateway.domain.storage.PostType;

import java.util.Date;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
public class Publication extends BaseEntity {


    private UUID publicationId;
    private UUID userId;
    private String text;

    @JsonManagedReference
    private Set<Group> groups;
    private Date publicationDateTime;
    private PostType postType;
    @JsonManagedReference
    private Set<GroupPostStatus> postStatus;
}
