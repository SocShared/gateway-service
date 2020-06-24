package ml.socshared.gateway.service.impl;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ml.socshared.gateway.client.TechSupportServiceClient;
import ml.socshared.gateway.domain.tech_support.response.*;
import ml.socshared.gateway.domain.user.RoleResponse;
import ml.socshared.gateway.domain.user.AuthUserResponse;
import ml.socshared.gateway.exception.impl.HttpForbiddenException;
import ml.socshared.gateway.security.model.TokenObject;
import ml.socshared.gateway.service.AuthService;
import ml.socshared.gateway.service.TechSupportService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class TechSupportServiceImpl implements TechSupportService {


    private final TechSupportServiceClient client;
    private final AuthService authInfoService;

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
        for(ShortQuestion el : questions.getData()) {
            String userLogin = getUserLogin(el.getAuthorId());
            el.setAuthorLogin(userLogin);
        }
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
        String authorLogin = getUserLogin(res.getAuthorId());
        int i = 0;
        if(!res.getComments().getData().isEmpty() &&
                res.getComments().getData().get(0).getAuthorId() != null &&
                res.getComments().getData().get(0).getAuthorId().equals(res.getAuthorId())) {
            res.getComments().getData().get(0).setAuthorLogin(authorLogin);
            i = 1;
        }
        List<Comment> comments = res.getComments().getData();
        for(; i < comments.size(); i++) {
            Comment c = comments.get(i);
            String userLogin = getUserLogin(c.getAuthorId());
            c.setAuthorLogin(userLogin);
        }


        Pageable p = PageRequest.of(res.getComments().getPage(), res.getComments().getSize());
        Page<Comment> page = new PageImpl(res.getComments().getData(), p, res.getComments().getTotalElements());
        FullQuestionResponse response = new FullQuestionResponse();

        if(res.getAuthorId() != null && res.getAuthorId().equals(systemUserID)) {
            response.setCanCreateComment(true);
            response.setCanDeleteQuestion(false);
        } else {
            response.setCanCreateComment(false);
            response.setCanDeleteQuestion(false);
        }

        if(userIsAdmin(systemUserID)) {
            response.setCanCreateComment(true);
            response.setCanDeleteQuestion(true);
        }
        response.setComments(page);
        response.setAuthorId(res.getAuthorId());
        response.setQuestionId(res.getQuestionId());
        response.setTitle(res.getTitle());
        response.setAuthorLogin(authorLogin);
        return response;
    }

    @Override
    public Integer addCommentToQuestion(Integer questionId, Comment comm) {
        FullQuestion q =  client.getFullQuestion(questionId, 0, 0, techSupportAuthToken());
        if(!(q.getAuthorId() != null && (q.getAuthorId().equals(comm.getAuthorId()) || userIsAdmin(comm.getAuthorId())))) {
            throw new HttpForbiddenException("Create comment to question can be a author question or administrator");
        }
        return client.addCommentToQuestion(questionId, comm, techSupportAuthToken());
    }

    @Override
    public void removeQuestion(Integer questionId, UUID systemUserId) {
        if(!userIsAdmin(systemUserId)) {
            throw new HttpForbiddenException("Only an administrator can delete a question");
        }
        client.removeQuestion(questionId, techSupportAuthToken());
    }

    @Override
    public void removeComment(Integer questionId, Integer commentId, UUID systemUserId) {
        if(!userIsAdmin(systemUserId)) {
            throw new HttpForbiddenException("Only an administrator can delete a comment");
        }
        client.removeComment(questionId, commentId, techSupportAuthToken());
    }

    private String techSupportAuthToken() {
        return "Bearer " + tokenTechSupport.getToken();
    }

    private Boolean userIsAdmin(UUID systemUserID) {
        try {
            AuthUserResponse user = authInfoService.getUserInfoById(systemUserID);
            for(RoleResponse role : user.getRoles()) {
                if(role.getName() != null && role.getName().equals("ADMIN")) {
                    return true;
                }
            }

        } catch(Exception exp) {
            log.error("Error request get UserResponse from AuthService", exp);
        }
        return false;
    }

    private String getUserLogin(UUID systemUserId) {
        try {
            AuthUserResponse user = authInfoService.getUserInfoById(systemUserId);
            return user.getUsername();
        } catch (Exception exp) {
            log.error("Error request get UserResponse from AuthService", exp);
            return "";
        }
    }
}
