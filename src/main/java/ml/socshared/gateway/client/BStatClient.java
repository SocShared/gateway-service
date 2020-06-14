package ml.socshared.gateway.client;

import ml.socshared.gateway.domain.bstat.response.GroupInfoResponse;
import ml.socshared.gateway.domain.bstat.response.PostInfoByTimeResponse;
import ml.socshared.gateway.domain.storage.SocialNetwork;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@FeignClient(name="BStatApi", url="${feign.url.bstat}")
public interface BStatClient {


    @GetMapping("api/v1/private/users/{systemUserId}/social/{soc}/groups/{systemGroupId}/info")
    GroupInfoResponse getGroupInfo(@PathVariable UUID systemUserId,
                                   @PathVariable UUID systemGroupId, @PathVariable SocialNetwork soc,
                                   @RequestParam(value = "begin") Long begin,
                                   @RequestParam(value = "end") Long end,
                                   @RequestHeader("Authorization") String token);

    @GetMapping("api/v1/private/users/{systemUserId}/social/{soc}/groups/{systemGroupId}/posts/{systemPostId}/time_series")
    PostInfoByTimeResponse getInfoVariabilityByTimeOfPost(@PathVariable UUID systemUserId,
                                                          @PathVariable UUID systemGroupId,
                                                          @PathVariable UUID systemPostId,
                                                          @PathVariable SocialNetwork soc,
                                                          @RequestParam(name = "begin") Long begin,
                                                          @RequestParam(name = "end") Long end,
                                                          @RequestHeader("Authorization") String token);
}