package ml.socshared.gateway.client;

import ml.socshared.gateway.domain.RestResponsePage;
import ml.socshared.gateway.domain.stat.errorstat.ErrorsStatResponse;
import ml.socshared.gateway.domain.stat.userstat.UsersStatResponse;
import ml.socshared.gateway.domain.stat.usingsocial.UsingSocialNetworkResponse;
import ml.socshared.gateway.domain.user.UserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "stat-client", url = "${feign.url.stat:}")
public interface StatClient {

    @GetMapping(value = "/api/v1/private/stat/social")
    UsingSocialNetworkResponse getUsingSocialNetworkStat(@RequestHeader("Authorization") String token);

    @GetMapping(value = "/api/v1/private/stat/users/online/count")
    UsersStatResponse getOnlineUsersCount(@RequestHeader("Authorization") String token);

    @GetMapping(value = "/api/v1/private/stat/users/online/count/timeline")
    List<UsersStatResponse> getOnlineUsersStatTimeline(@RequestHeader("Authorization") String token);

    @GetMapping(value = "/api/v1/private/stat/users/online")
    RestResponsePage<UserResponse> getOnlineUsers(@RequestParam(name = "page") Integer page,
                                                  @RequestParam(name = "size") Integer size,
                                                  @RequestHeader("Authorization") String token);

    @GetMapping(value = "/api/v1/private/stat/users/active/count")
    UsersStatResponse getActiveUsersCount(@RequestHeader("Authorization") String token);

    @GetMapping(value = "/api/v1/private/stat/users/active/count/timeline")
    List<UsersStatResponse> getActiveUsersStatTimeline(@RequestHeader("Authorization") String token);

    @GetMapping(value = "/api/v1/private/stat/users/active")
    RestResponsePage<UserResponse> getActiveUsers(@RequestParam(name = "page") Integer page,
                                                  @RequestParam(name = "size") Integer size,
                                                  @RequestHeader("Authorization") String token);

    @GetMapping(value = "/api/v1/private/stat/users/new/count")
    UsersStatResponse getNewUsersCount(@RequestHeader("Authorization") String token);

    @GetMapping(value = "/api/v1/private/stat/users/new/count/timeline")
    List<UsersStatResponse> getNewUsersStatTimeline(@RequestHeader("Authorization") String token);

    @GetMapping(value = "/api/v1/private/stat/users/new")
    RestResponsePage<UserResponse> getNewUsers(@RequestParam(name = "page") Integer page,
                                               @RequestParam(name = "size") Integer size,
                                               @RequestHeader("Authorization") String token);

    @GetMapping(value = "/api/v1/private/stat/users/all/count")
    UsersStatResponse getAllUsersCount(@RequestHeader("Authorization") String token);

    @GetMapping(value = "/api/v1/private/stat/users/all/count/timeline")
    List<UsersStatResponse> getAllUsersStatTimeline(@RequestHeader("Authorization") String token);

    @GetMapping(value = "/api/v1/private/stat/users/all")
    RestResponsePage<UserResponse> getAllUsers(@RequestParam(name = "page") Integer page,
                                               @RequestParam(name = "size") Integer size,
                                               @RequestHeader("Authorization") String token);

    @GetMapping(value = "/api/v1/private/stat/errors")
    ErrorsStatResponse getErrorsStat(@RequestHeader("Authorization") String token);

}
