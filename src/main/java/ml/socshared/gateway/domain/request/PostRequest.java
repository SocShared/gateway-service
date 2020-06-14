package ml.socshared.gateway.domain.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ml.socshared.gateway.domain.storage.PostType;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter
@Setter
@ToString
public class PostRequest {

    private Date publicationDateTime;
    @NotEmpty
    private String[] groupIds;
    @NotNull
    private PostType type;
    @NotNull
    private String text;
    private String[] hashTags;

}
