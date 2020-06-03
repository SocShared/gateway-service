package ml.socshared.gateway.service;

import ml.socshared.gateway.domain.tech_support.response.Comment;
import ml.socshared.gateway.domain.tech_support.response.FullQuestionResponse;
import ml.socshared.gateway.domain.tech_support.response.Question;
import ml.socshared.gateway.domain.tech_support.response.ShortQuestion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TechSupportService {

    Integer createQuestion(Question q, String authToken);
    Page<ShortQuestion> getQuestionList(Pageable pageable, String authToken);
    FullQuestionResponse getFullQuestion(Integer questionId, Pageable pageable, String authToken);
    Integer addCommentToQuestion(Integer questionId, Comment comm, String authToken);
    void removeQuestion(Integer questionId, String authToken);
    void removeComment(Integer QestionId, Integer commentId, String authToken);

}