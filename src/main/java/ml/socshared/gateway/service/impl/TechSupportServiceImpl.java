package ml.socshared.gateway.service.impl;


import ml.socshared.gateway.client.TechSupportServiceClient;
import ml.socshared.gateway.domain.response.texch_support.*;
import ml.socshared.gateway.service.TechSupportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class TechSupportServiceImpl implements TechSupportService {

    TechSupportServiceClient client;

    @Autowired
    TechSupportServiceImpl(TechSupportServiceClient client) {
        this.client = client;
    }

    @Override
    public Integer createQuestion(Question q, String authToken) {

        return client.addQuestion(q, authToken);
    }

    @Override
    public Page<ShortQuestion> getQuestionList(Pageable pageable, String authToken) {
        PageResponse<ShortQuestion> questions = client.getQuestionList(pageable.getPageNumber(), pageable.getPageSize(), authToken);
        Pageable p = PageRequest.of(questions.getPage(), pageable.getPageSize());
        return new PageImpl(questions.getData(), p, 0);

    }

    @Override
    public FullQuestionResponse getFullQuestion(Integer questionId, Pageable pageable, String authToken) {
        FullQuestion res = client.getFullQuestion(questionId, pageable.getPageNumber(), pageable.getPageSize(), authToken);
        Pageable p = PageRequest.of(res.getComments().getPage(), res.getComments().getSize());
        Page<Comment> page = new PageImpl(res.getComments().getData(), p, 0);
        FullQuestionResponse response = new FullQuestionResponse();
        response.setComments(page);
        response.setAuthorId(res.getAuthorId());
        response.setQuestionId(res.getQuestionId());
        response.setTitle(res.getTitle());
        return response;
    }

    @Override
    public Integer addCommentToQuestion(Integer questionId, Comment comm, String authToken) {
        return client.addCommentToQuestion(questionId, comm, authToken);
    }

    @Override
    public void removeQuestion(Integer questionId, String authToken) {
        client.removeQuestion(questionId, authToken);
    }

    @Override
    public void removeComment(Integer questionId, Integer commentId, String authToken) {
        client.removeComment(questionId, commentId, authToken);
    }
}
