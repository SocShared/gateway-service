package ml.socshared.gateway.service;

import ml.socshared.gateway.domain.tech_support.response.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface TechSupportService {

    Integer createQuestion(Question q);
    QuestionsPage getQuestionList(Pageable pageable, UUID systemUserID);
    FullQuestionResponse getFullQuestion(Integer questionId, Pageable pageable, UUID systemUserID);
    Integer addCommentToQuestion(Integer questionId, Comment comm);
    void removeQuestion(Integer questionId);
    void removeComment(Integer QestionId, Integer commentId);

}
