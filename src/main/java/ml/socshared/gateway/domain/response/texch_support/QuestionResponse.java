package ml.socshared.gateway.domain.response.texch_support;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuestionResponse {
    Integer questionId;
    @JsonProperty(required = true)
    UUID authorId;
    @JsonProperty(required = true)
    String title;
}
