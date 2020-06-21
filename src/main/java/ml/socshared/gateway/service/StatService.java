package ml.socshared.gateway.service;

import ml.socshared.gateway.domain.RestResponsePage;
import ml.socshared.gateway.domain.stat.errorstat.ErrorsStatResponse;
import ml.socshared.gateway.domain.stat.userstat.UsersStatResponse;
import ml.socshared.gateway.domain.stat.usingsocial.UsingSocialNetworkResponse;
import ml.socshared.gateway.domain.user.UserResponse;

import java.util.List;

public interface StatService {

    UsingSocialNetworkResponse getUsingSocialNetworkStat();
    UsersStatResponse getOnlineUsersCount();
    List<UsersStatResponse> getOnlineUsersStatTimeline();
    RestResponsePage<UserResponse> getOnlineUsers(Integer page, Integer size);
    UsersStatResponse getActiveUsersCount();
    List<UsersStatResponse> getActiveUsersStatTimeline();
    RestResponsePage<UserResponse> getActiveUsers(Integer page, Integer size);
    UsersStatResponse getNewUsersCount();
    List<UsersStatResponse> getNewUsersStatTimeline();
    RestResponsePage<UserResponse> getNewUsers(Integer page, Integer size);
    UsersStatResponse getAllUsersCount();
    List<UsersStatResponse> getAllUsersStatTimeline();
    RestResponsePage<UserResponse> getAllUsers(Integer page, Integer size);
    ErrorsStatResponse getErrorsStat();

}
