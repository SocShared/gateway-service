package ml.socshared.gateway.controller.v1;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ml.socshared.gateway.domain.storage.response.GroupResponse;
import ml.socshared.gateway.domain.storage.response.PublicationResponse;
import ml.socshared.gateway.security.jwt.JwtTokenProvider;
import ml.socshared.gateway.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@PreAuthorize("isAuthenticated()")
public class StorageController {

    private final JwtTokenProvider jwtTokenProvider;
    private final StorageService service;


    @GetMapping("/groups")
    @PreAuthorize("hasRole('CONTENT_MANAGER')")
    Page<GroupResponse> getGroupsList(Pageable pageable,
                                      HttpServletRequest request) {
        log.info("Request get groups list");
        UUID systemUserId = jwtTokenProvider.getUserId(jwtTokenProvider.resolveToken(request));
        Page<GroupResponse> res = service.getGroupsList(systemUserId, pageable);
        return res;
    }

    @PreAuthorize("hasRole('CONTENT_MANAGER')")
    @GetMapping("/groups/{systemGroupId}/posts")
    Page<PublicationResponse> getPostList( @PathVariable UUID systemGroupId,
                                            Pageable pageable,
                                           HttpServletRequest request) {
        log.info("request get post list of group id");
        UUID systemUserId = jwtTokenProvider.getUserId(jwtTokenProvider.resolveToken(request));
        Page<PublicationResponse>  res = service.getPostList(systemUserId, systemGroupId, pageable);
        return res;
    }

    //TODO add post
    //TODO add group;

}
