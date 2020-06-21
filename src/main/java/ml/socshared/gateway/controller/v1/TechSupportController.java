package ml.socshared.gateway.controller.v1;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ml.socshared.gateway.api.v1.rest.TechSupportApi;
import ml.socshared.gateway.domain.tech_support.response.Comment;
import ml.socshared.gateway.domain.tech_support.response.FullQuestionResponse;
import ml.socshared.gateway.domain.tech_support.response.Question;
import ml.socshared.gateway.domain.tech_support.response.ShortQuestion;
import ml.socshared.gateway.exception.impl.HttpBadRequestException;
import ml.socshared.gateway.security.jwt.JwtTokenProvider;
import ml.socshared.gateway.service.TechSupportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;


@Slf4j
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@PreAuthorize("isAuthenticated()")
public class TechSupportController  implements TechSupportApi {

    private final TechSupportService service;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    @PostMapping("/protected/support/questions")
    @PreAuthorize("hasRole('CONTENT_MANAGER')")
    public Integer addQuestion(@RequestBody Question question,
                               HttpServletRequest request) {
        log.info("Request create of question");
        UUID systemUserId = jwtTokenProvider.getUserId(jwtTokenProvider.resolveToken(request));
        question.setAuthorId(systemUserId);
        return service.createQuestion(question);
    }

    @Override
    @GetMapping("/protected/support")
    @PreAuthorize("hasRole('CONTENT_MANAGER')")
    public Page<ShortQuestion> getQuestionsList(Pageable pageable) {
        log.info("Request of get support page " + pageable.toString());
        return service.getQuestionList(pageable);
    }

    @Override
    @PreAuthorize("hasRole('CONTENT_MANAGER')")
    @GetMapping("/protected/support/questions/{questionId}")
    public FullQuestionResponse getFullQuestion(@PathVariable Integer questionId, Pageable pageable) {
        log.info("Request get of page with question " + questionId);
        return service.getFullQuestion(questionId, pageable);
    }

    @Override
    @PreAuthorize("hasRole('CONTENT_MANAGER')")
    @PostMapping("/protected/support/questions/{questionId}/comments")
    public Integer addCommentToQuestion(@PathVariable Integer questionId, @RequestBody Comment comment,
                                        HttpServletRequest request) {
        UUID systemUserId = jwtTokenProvider.getUserId(jwtTokenProvider.resolveToken(request));
        comment.setAuthorId(systemUserId);
        log.info("Request create of comment to question " + questionId);
        return service.addCommentToQuestion(questionId, comment);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/protected/support/questions/{questionId}")
    public void removeQuestion(@PathVariable Integer questionId) {
        log.info("Request remove question " + questionId);
        service.removeQuestion(questionId);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/protected/support/questions/{questionId}/comments/{commentId}")
    public void removeComment(@PathVariable Integer questionId,@PathVariable Integer commentId) {
        log.info("Request delete comment " + commentId + " of question " + questionId);
        service.removeComment(questionId, commentId);
    }
}
