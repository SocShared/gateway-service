package ml.socshared.gateway.service.impl;


import lombok.RequiredArgsConstructor;
import ml.socshared.gateway.client.TechSupportServiceClient;
import ml.socshared.gateway.domain.tech_support.response.*;
import ml.socshared.gateway.domain.user.RoleResponse;
import ml.socshared.gateway.domain.user.UserResponse;
import ml.socshared.gateway.security.client.AuthClient;
import ml.socshared.gateway.security.jwt.JwtTokenProvider;
import ml.socshared.gateway.security.model.TokenObject;
import ml.socshared.gateway.service.AuthInfoService;
import ml.socshared.gateway.service.TechSupportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TechSupportServiceImpl implements TechSupportService {


    private final TechSupportServiceClient client;
    private final AuthInfoService authInfoService;

    @Value("#{tokenGetter.tokenTechSupport}")
    TokenObject tokenTechSupport;

    @Override
    public Integer createQuestion(Question q) {
        return client.addQuestion(q, techSupportAuthToken());
    }

    @Override
    public QuestionsPage getQuestionList(Pageable pageable, UUID systemUserID) {

        PageResponse<ShortQuestion> questions = client.getQuestionList(pageable.getPageNumber(),
                pageable.getPageSize(), techSupportAuthToken());
        Pageable p = PageRequest.of(questions.getPage(), pageable.getPageSize());
        QuestionsPage rp = new QuestionsPage();
        if(userIsAdmin(systemUserID)) {
            rp.setCanDelete(true);
        } else {
            rp.setCanDelete(false);
        }
        rp.setShortQuestions(new PageImpl(questions.getData(), p, 0));
        return rp;

    }

    @Override
    public FullQuestionResponse getFullQuestion(Integer questionId, Pageable pageable, UUID systemUserID) {
        FullQuestion res = client.getFullQuestion(questionId, pageable.getPageNumber(), pageable.getPageSize(),
                techSupportAuthToken());
        Pageable p = PageRequest.of(res.getComments().getPage(), res.getComments().getSize());
        Page<Comment> page = new PageImpl(res.getComments().getData(), p, res.getComments().getTotalElements());
        FullQuestionResponse response = new FullQuestionResponse();
        if(res.getAuthorId() != null && res.getAuthorId().equals(systemUserID)) {
            response.setCanCreateComment(true);
            response.setCanDeleteAnswer(false);
        }

        if(userIsAdmin(systemUserID)) {
            response.setCanCreateComment(true);
            response.setCanDeleteAnswer(true);
        }
        response.setComments(page);
        response.setAuthorId(res.getAuthorId());
        response.setQuestionId(res.getQuestionId());
        response.setTitle(res.getTitle());
        return response;
    }

    @Override
    public Integer addCommentToQuestion(Integer questionId, Comment comm) {
        return client.addCommentToQuestion(questionId, comm, techSupportAuthToken());
    }

    @Override
    public void removeQuestion(Integer questionId) {
        client.removeQuestion(questionId, techSupportAuthToken());
    }

    @Override
    public void removeComment(Integer questionId, Integer commentId) {
        client.removeComment(questionId, commentId, techSupportAuthToken());
    }

    private String techSupportAuthToken() {
        return "Bearer " + tokenTechSupport.getToken();
    }

    private Boolean userIsAdmin(UUID systemUserID) {
        UserResponse user = authInfoService.getClientInfoById(systemUserID);
        for(RoleResponse role : user.getRoles()) {
            if(role.getName() != null && role.getName().equals("ADMIN")) {
               return true;
            }
        }
        return false;
    }
}
