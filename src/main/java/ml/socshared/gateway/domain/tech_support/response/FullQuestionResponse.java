package ml.socshared.gateway.domain.tech_support.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FullQuestionResponse extends QuestionResponse {
    Page<Comment> comments;
    Boolean canCreateComment;
    Boolean canDeleteQuestion;
}
