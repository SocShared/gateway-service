package ml.socshared.gateway.service;

import ml.socshared.gateway.domain.stat.errorstat.ErrorsStatResponse;
import ml.socshared.gateway.domain.stat.userstat.UsersStatResponse;
import ml.socshared.gateway.domain.stat.usingsocial.UsingSocialNetworkResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

public interface StatService {

    UsingSocialNetworkResponse getUsingSocialNetworkStat();
    UsersStatResponse getOnlineUsersStat();
    List<UsersStatResponse> getOnlineUsersStatTimeline();
    UsersStatResponse getActiveUsersStat();
    List<UsersStatResponse> getActiveUsersStatTimeline();
    UsersStatResponse getNewUsersStat();
    List<UsersStatResponse> getNewUsersStatTimeline();
    UsersStatResponse getAllUsersStat();
    List<UsersStatResponse> getAllUsersStatTimeline();
    ErrorsStatResponse getErrorsStat();

}
