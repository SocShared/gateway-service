package ml.socshared.gateway.service;

import ml.socshared.gateway.domain.tech_support.response.Comment;
import ml.socshared.gateway.domain.tech_support.response.FullQuestionResponse;
import ml.socshared.gateway.domain.tech_support.response.Question;
import ml.socshared.gateway.domain.tech_support.response.ShortQuestion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TechSupportService {

    Integer createQuestion(Question q);
    Page<ShortQuestion> getQuestionList(Pageable pageable);
    FullQuestionResponse getFullQuestion(Integer questionId, Pageable pageable);
    Integer addCommentToQuestion(Integer questionId, Comment comm);
    void removeQuestion(Integer questionId);
    void removeComment(Integer QestionId, Integer commentId);

}
