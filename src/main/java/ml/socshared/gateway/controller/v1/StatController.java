package ml.socshared.gateway.controller.v1;

import lombok.RequiredArgsConstructor;
import ml.socshared.gateway.domain.stat.errorstat.ErrorsStatResponse;
import ml.socshared.gateway.domain.stat.userstat.UsersStatResponse;
import ml.socshared.gateway.domain.stat.usingsocial.UsingSocialNetworkResponse;
import ml.socshared.gateway.service.StatService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    @GetMapping(value = "/protected/stat/users/online")
    public UsersStatResponse getOnlineUsersStat() {
        return statService.getOnlineUsersStat();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(value = "/protected/stat/users/online/timeline")
    public List<UsersStatResponse> getOnlineUsersStatTimeline() {
        return statService.getOnlineUsersStatTimeline();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(value = "/protected/stat/users/active")
    public UsersStatResponse getActiveUsersStat() {
        return statService.getActiveUsersStat();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(value = "/protected/stat/users/active/timeline")
    public List<UsersStatResponse> getActiveUsersStatTimeline() {
        return statService.getActiveUsersStatTimeline();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(value = "/protected/stat/users/new")
    public UsersStatResponse getNewUsersStat() {
        return statService.getNewUsersStat();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(value = "/protected/stat/users/new/timeline")
    public List<UsersStatResponse> getNewUsersStatTimeline() {
        return statService.getNewUsersStatTimeline();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(value = "/protected/stat/users/all")
    public UsersStatResponse getAllUsersStat() {
        return statService.getAllUsersStat();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(value = "/protected/stat/users/all/timeline")
    public List<UsersStatResponse> getAllUsersStatTimeline() {
        return statService.getAllUsersStatTimeline();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(value = "/protected/stat/errors")
    public ErrorsStatResponse getErrorsStat() {
        return statService.getErrorsStat();
    }

}
