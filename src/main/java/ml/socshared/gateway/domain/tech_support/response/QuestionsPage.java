package ml.socshared.gateway.domain.tech_support.response;

import lombok.Data;
import org.springframework.data.domain.Page;

@Data
public class QuestionsPage {
    private Page<ShortQuestion> shortQuestions;
    private Boolean canDelete;
}
