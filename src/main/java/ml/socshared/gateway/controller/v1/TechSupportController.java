package ml.socshared.gateway.controller.v1;

import lombok.extern.slf4j.Slf4j;
import ml.socshared.gateway.api.v1.rest.TechSupportApi;
import ml.socshared.gateway.domain.response.texch_support.*;
import ml.socshared.gateway.service.TechSupportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/api/v1")
public class TechSupportController implements TechSupportApi {

    private TechSupportService service;

    @Autowired
    TechSupportController(TechSupportService service) {
        this.service = service;
    }

    @Override
    @PostMapping("protected/support/questions")
    public Integer addQuestion(@RequestBody Question question,
                               @RequestHeader(value = "Authorization", required = false) String authToken) {
        log.info("Request create of qustion");
        return service.createQuestion(question, authToken);
    }

    @Overridez
    @GetMapping("protected/support")
    public Page<ShortQuestion> getQuestionsList(Pageable pageable,
                                                @RequestHeader(value = "Authorization", required = false) String authToken) {
        log.info("Request of get support page " + pageable.toString());
        return service.getQuestionList(pageable, authToken);
    }

    @Override
    @GetMapping("protected/support/questions/{questionId}")
    public FullQuestionResponse getFullQuestion(@PathVariable Integer questionId, Pageable pageable,
                                        @RequestHeader(value = "Authorization", required = false) String authToken) {
        log.info("Request get of page with question " + String.valueOf(questionId));
        return service.getFullQuestion(questionId, pageable, authToken);
    }

    @Override
    @PostMapping("protected/support/questions/{questionId}/comments")
    public Integer addCommentToQuestion(@PathVariable Integer questionId,@RequestBody Comment comment,
                                        @RequestHeader(value = "Authorization", required = false) String authToken) {
        log.info("Request create of comment to question " + String.valueOf(questionId));
        return service.addCommentToQuestion(questionId, comment, authToken);
    }

    @Override
    @DeleteMapping("protected/support/questions/{questionId}")
    public void removeQuestion(@PathVariable Integer questionId,
                               @RequestHeader(value = "Authorization", required = false) String authToken) {
        log.info("Request remove question " + String.valueOf(questionId));
        service.removeQuestion(questionId, authToken);
    }

    @Override
    @DeleteMapping("protected/support/questions/{questionId}/comments/{commentId}")
    public void removeComment(@PathVariable Integer questionId,@PathVariable Integer commentId,
                              @RequestHeader(value = "Authorization", required = false) String authToken) {
        log.info("Request delete comment " + String.valueOf(commentId) + " of question " + String.valueOf(questionId));
        service.removeComment(questionId, commentId, authToken);
    }
}
