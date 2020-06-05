package ml.socshared.gateway.controller.v1;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ml.socshared.gateway.api.v1.rest.VkApi;
import ml.socshared.gateway.domain.SuccessResponse;
import ml.socshared.gateway.domain.vk.GroupResponse;
import ml.socshared.gateway.security.jwt.JwtTokenProvider;
import ml.socshared.gateway.service.VkService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@PreAuthorize("isAuthenticated()")
public class VkController implements VkApi {

    private final VkService vkService;
    private final JwtTokenProvider jwtTokenProvider;


    @Override
    @PostMapping("/protected/social/vk/app")
    @PreAuthorize("hasRole('CONTENT_MANAGER')")
    public SuccessResponse setToken( @RequestBody String accessToken,
                                     HttpServletRequest request) {
       log.info("Request add vk app");
        UUID systemUserId = jwtTokenProvider.getUserId(jwtTokenProvider.resolveToken(request));
        vkService.setAccessTokenApp(systemUserId, accessToken);
        return new SuccessResponse(true);
    }

    @Override
    @GetMapping("/protected/social/vk/groups")
    @PreAuthorize("hasRole('CONTENT_MANAGER')")
    public Page<GroupResponse> getGroupUserList(Pageable pageable,
                                                HttpServletRequest request) {
        log.info("Request get groups from vk social");
        UUID systemUserId = jwtTokenProvider.getUserId(jwtTokenProvider.resolveToken(request));
        return vkService.getGroupsOfUser(systemUserId, pageable);
    }

}
