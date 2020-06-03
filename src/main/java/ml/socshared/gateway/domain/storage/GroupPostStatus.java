package ml.socshared.gateway.domain.storage;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;
import ml.socshared.gateway.domain.storage.response.Publication;

import javax.persistence.*;
import java.util.UUID;

@Getter
@Setter

public class GroupPostStatus {

    private UUID groupId;
    private PostStatus postStatus;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "publication_id", referencedColumnName = "publication_id")
    private Publication publication;

    public enum PostStatus {
        PUBLISHED,
        AWAITING,
        NOT_SUCCESSFUL,
        PROCESSING
    }

    public GroupPostStatus() {
        postStatus = PostStatus.AWAITING;
    }

}
