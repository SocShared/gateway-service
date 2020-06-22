package ml.socshared.gateway.service;

import ml.socshared.gateway.domain.RestResponsePage;
import ml.socshared.gateway.domain.stat.errorstat.ErrorsStatResponse;
import ml.socshared.gateway.domain.stat.userstat.UsersStatResponse;
import ml.socshared.gateway.domain.stat.usingsocial.UsingSocialNetworkResponse;
import ml.socshared.gateway.domain.user.AuthUserResponse;

import java.util.List;

public interface StatService {

    UsingSocialNetworkResponse getUsingSocialNetworkStat();
    UsersStatResponse getOnlineUsersCount();
    List<UsersStatResponse> getOnlineUsersStatTimeline();
    RestResponsePage<AuthUserResponse> getOnlineUsers(Integer page, Integer size);
    UsersStatResponse getActiveUsersCount();
    List<UsersStatResponse> getActiveUsersStatTimeline();
    RestResponsePage<AuthUserResponse> getActiveUsers(Integer page, Integer size);
    UsersStatResponse getNewUsersCount();
    List<UsersStatResponse> getNewUsersStatTimeline();
    RestResponsePage<AuthUserResponse> getNewUsers(Integer page, Integer size);
    UsersStatResponse getAllUsersCount();
    List<UsersStatResponse> getAllUsersStatTimeline();
    RestResponsePage<AuthUserResponse> getAllUsers(Integer page, Integer size);
    ErrorsStatResponse getErrorsStat();

}
