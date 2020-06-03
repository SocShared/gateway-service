package ml.socshared.gateway.service.impl;


import ml.socshared.gateway.client.TechSupportServiceClient;
import ml.socshared.gateway.domain.tech_support.response.*;
import ml.socshared.gateway.security.client.AuthClient;
import ml.socshared.gateway.security.jwt.JwtTokenProvider;
import ml.socshared.gateway.security.model.TokenObject;
import ml.socshared.gateway.service.TechSupportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class TechSupportServiceImpl implements TechSupportService {

    JwtTokenProvider jwtProvider;

    TechSupportServiceClient client;

    @Value("#{tokenGetter.tokenTechSupport}")
    TokenObject tokenTechSupport;

    @Autowired
    TechSupportServiceImpl(TechSupportServiceClient client, JwtTokenProvider jwtProvider)
    {
        this.client = client;
        this.jwtProvider = jwtProvider;
    }

    @Override
    public Integer createQuestion(Question q, String authToken) {
        UserDetails user =  jwtProvider.getUserDetails(authToken);
        return client.addQuestion(q, serviceAuthToken());
    }

    @Override
    public Page<ShortQuestion> getQuestionList(Pageable pageable, String authToken) {
        PageResponse<ShortQuestion> questions = client.getQuestionList(pageable.getPageNumber(), pageable.getPageSize(), serviceAuthToken());
        Pageable p = PageRequest.of(questions.getPage(), pageable.getPageSize());
        return new PageImpl(questions.getData(), p, 0);

    }

    @Override
    public FullQuestionResponse getFullQuestion(Integer questionId, Pageable pageable, String authToken) {
        FullQuestion res = client.getFullQuestion(questionId, pageable.getPageNumber(), pageable.getPageSize(), serviceAuthToken());
        Pageable p = PageRequest.of(res.getComments().getPage(), res.getComments().getSize());
        Page<Comment> page = new PageImpl(res.getComments().getData(), p, res.getComments().getTotalElements());
        FullQuestionResponse response = new FullQuestionResponse();
        response.setComments(page);
        response.setAuthorId(res.getAuthorId());
        response.setQuestionId(res.getQuestionId());
        response.setTitle(res.getTitle());
        return response;
    }

    @Override
    public Integer addCommentToQuestion(Integer questionId, Comment comm, String authToken) {
        return client.addCommentToQuestion(questionId, comm, serviceAuthToken());
    }

    @Override
    public void removeQuestion(Integer questionId, String authToken) {
        client.removeQuestion(questionId, serviceAuthToken());
    }

    @Override
    public void removeComment(Integer questionId, Integer commentId, String authToken) {
        client.removeComment(questionId, commentId, serviceAuthToken());
    }

    private String serviceAuthToken() {
        return "Bearer " + tokenTechSupport.getToken();
    }
}
