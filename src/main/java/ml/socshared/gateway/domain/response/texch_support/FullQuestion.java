package ml.socshared.gateway.domain.response.texch_support;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FullQuestion extends QuestionResponse {
    PageResponse<Comment> comments;
}
