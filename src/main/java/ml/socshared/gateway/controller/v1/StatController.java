package ml.socshared.gateway.controller.v1;

import lombok.RequiredArgsConstructor;
import ml.socshared.gateway.domain.RestResponsePage;
import ml.socshared.gateway.domain.stat.SocCountResponse;
import ml.socshared.gateway.domain.stat.errorstat.ErrorsStatResponse;
import ml.socshared.gateway.domain.stat.userstat.UsersStatResponse;
import ml.socshared.gateway.domain.stat.usingsocial.UsingSocialNetworkResponse;
import ml.socshared.gateway.domain.user.AuthUserResponse;
import ml.socshared.gateway.service.StatService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1")
@RequiredArgsConstructor
@PreAuthorize("isAuthenticated()")
@Validated
public class StatController {

    private final StatService statService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(value = "/protected/stat/social")
    public UsingSocialNetworkResponse getUsingSocialNetworkStat() {
        return statService.getUsingSocialNetworkStat();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(value = "/protected/stat/users/online/count")
    public UsersStatResponse getOnlineUsersCount() {
        return statService.getOnlineUsersCount();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(value = "/protected/stat/users/online/count/timeline")
    public List<UsersStatResponse> getOnlineUsersStatTimeline() {
        return statService.getOnlineUsersStatTimeline();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(value = "/protected/stat/users/online")
    public RestResponsePage<AuthUserResponse> getOnlineUsers(@RequestParam(name = "page", defaultValue = "0") Integer page,
                                                             @RequestParam(name = "size", defaultValue = "10") Integer size) {
        return statService.getOnlineUsers(page, size);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(value = "/protected/stat/users/active/count")
    public UsersStatResponse getActiveUsersCount() {
        return statService.getActiveUsersCount();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(value = "/protected/stat/users/active/count/timeline")
    public List<UsersStatResponse> getActiveUsersStatTimeline() {
        return statService.getActiveUsersStatTimeline();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(value = "/protected/stat/users/active")
    public RestResponsePage<AuthUserResponse> getActiveUsersStat(@RequestParam(name = "page", defaultValue = "0") Integer page,
                                                                 @RequestParam(name = "size", defaultValue = "10") Integer size) {
        return statService.getActiveUsers(page, size);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(value = "/protected/stat/users/new/count")
    public UsersStatResponse getNewUsersCount() {
        return statService.getNewUsersCount();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(value = "/protected/stat/users/new/count/timeline")
    public List<UsersStatResponse> getNewUsersStatTimeline() {
        return statService.getNewUsersStatTimeline();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(value = "/protected/stat/users/new")
    public RestResponsePage<AuthUserResponse> getNewUsers(@RequestParam(name = "page", defaultValue = "0") Integer page,
                                                          @RequestParam(name = "size", defaultValue = "10") Integer size) {
        return statService.getNewUsers(page, size);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(value = "/protected/stat/users/all/count")
    public UsersStatResponse getAllUsersCount() {
        return statService.getAllUsersCount();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(value = "/protected/stat/users/all/count/timeline")
    public List<UsersStatResponse> getAllUsersStatTimeline() {
        return statService.getAllUsersStatTimeline();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(value = "/protected/stat/users/all")
    public RestResponsePage<AuthUserResponse> getAllUsers(@RequestParam(name = "page", defaultValue = "0") Integer page,
                                                          @RequestParam(name = "size", defaultValue = "10") Integer size) {
        return statService.getAllUsers(page, size);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(value = "/protected/stat/errors")
    public ErrorsStatResponse getErrorsStat() {
        return statService.getErrorsStat();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(value = "/protected/stat/soc/count")
    public SocCountResponse getSocCount() {
        return statService.getSocCount();
    }

}
