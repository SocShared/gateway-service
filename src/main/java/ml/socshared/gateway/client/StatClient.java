package ml.socshared.gateway.client;

import ml.socshared.gateway.domain.stat.errorstat.ErrorsStatResponse;
import ml.socshared.gateway.domain.stat.userstat.UsersStatResponse;
import ml.socshared.gateway.domain.stat.usingsocial.UsingSocialNetworkResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

@FeignClient(name = "stat-client", url = "${feign.url.stat:}")
public interface StatClient {

    @PreAuthorize("hasRole('SERVICE')")
    @GetMapping(value = "/api/v1/private/stat/social")
    UsingSocialNetworkResponse getUsingSocialNetworkStat(@RequestHeader("Authorization") String token);

    @PreAuthorize("hasRole('SERVICE')")
    @GetMapping(value = "/api/v1/private/stat/users/online")
    UsersStatResponse getOnlineUsersStat(@RequestHeader("Authorization") String token);

    @PreAuthorize("hasRole('SERVICE')")
    @GetMapping(value = "/api/v1/private/stat/users/online/timeline")
    List<UsersStatResponse> getOnlineUsersStatTimeline(@RequestHeader("Authorization") String token);

    @PreAuthorize("hasRole('SERVICE')")
    @GetMapping(value = "/api/v1/private/stat/users/active")
    UsersStatResponse getActiveUsersStat(@RequestHeader("Authorization") String token);

    @PreAuthorize("hasRole('SERVICE')")
    @GetMapping(value = "/api/v1/private/stat/users/active/timeline")
    List<UsersStatResponse> getActiveUsersStatTimeline(@RequestHeader("Authorization") String token);

    @PreAuthorize("hasRole('SERVICE')")
    @GetMapping(value = "/api/v1/private/stat/users/new")
    UsersStatResponse getNewUsersStat(@RequestHeader("Authorization") String token);

    @PreAuthorize("hasRole('SERVICE')")
    @GetMapping(value = "/api/v1/private/stat/users/new/timeline")
    List<UsersStatResponse> getNewUsersStatTimeline(@RequestHeader("Authorization") String token);

    @PreAuthorize("hasRole('SERVICE')")
    @GetMapping(value = "/api/v1/private/stat/users/all")
    UsersStatResponse getAllUsersStat(@RequestHeader("Authorization") String token);

    @PreAuthorize("hasRole('SERVICE')")
    @GetMapping(value = "/api/v1/private/stat/users/all/timeline")
    List<UsersStatResponse> getAllUsersStatTimeline(@RequestHeader("Authorization") String token);

    @PreAuthorize("hasRole('SERVICE')")
    @GetMapping(value = "/api/v1/private/stat/errors")
    ErrorsStatResponse getErrorsStat(@RequestHeader("Authorization") String token);

}
