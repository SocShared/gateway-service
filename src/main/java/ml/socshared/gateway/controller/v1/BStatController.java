package ml.socshared.gateway.controller.v1;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ml.socshared.gateway.domain.bstat.response.GroupInfoResponse;
import ml.socshared.gateway.domain.bstat.response.PostInfoByTimeResponse;
import ml.socshared.gateway.domain.storage.SocialNetwork;
import ml.socshared.gateway.service.BStatService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ml.socshared.gateway.security.jwt.JwtTokenProvider;

import javax.servlet.http.HttpServletRequest;
import javax.websocket.server.PathParam;
import java.util.UUID;

@RestController
@Slf4j
@RequestMapping("api/v1")
@PreAuthorize("isAuthenticated()")
@RequiredArgsConstructor
public class BStatController {

    private final JwtTokenProvider jwtTokenProvider;

    private final BStatService bStatService;


    @GetMapping("/protected/social/{soc}/groups/{systemGroupId}/stat")
    GroupInfoResponse getStatisticOfGroup(@PathVariable UUID systemGroupId, @PathVariable SocialNetwork soc,
                                          @PathParam("begin") Long begin, @PathParam("end") Long end,
                                          HttpServletRequest request) {
        log.info("Request of get group statistics");
        UUID systemUserId = jwtTokenProvider.getUserId(jwtTokenProvider.resolveToken(request));
        return bStatService.getGroupInfo(systemUserId, systemGroupId, soc, begin, end);
    }

    @GetMapping("/protected/social/{soc}/groups/{systemGroupId}/posts/{systemPostId}/stat")
    PostInfoByTimeResponse getStatisticOfPost(@PathVariable UUID systemGroupId, @PathVariable UUID systemPostId,
                                              @PathVariable SocialNetwork soc,
                                              @RequestParam("begin") Long begin, @RequestParam("end") Long end,
                                              HttpServletRequest request) {
        log.info("Request get of post statistic");
        UUID systemUserId = jwtTokenProvider.getUserId(jwtTokenProvider.resolveToken(request));
        return bStatService.getInfoVariabilityByTimeOfPost(systemUserId, systemGroupId, systemPostId, soc, begin, end);
    }
}
