package ml.socshared.gateway.domain.storage.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ml.socshared.gateway.domain.storage.SocialNetwork;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GroupRequest {

    @NotEmpty
    private String userId;
    private String vkId;
    private String fbId;
    @NotNull
    private String name;
    @NotNull
    private SocialNetwork socialNetwork;

}
