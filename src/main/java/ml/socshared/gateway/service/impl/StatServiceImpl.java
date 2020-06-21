package ml.socshared.gateway.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ml.socshared.gateway.client.StatClient;
import ml.socshared.gateway.domain.RestResponsePage;
import ml.socshared.gateway.domain.stat.errorstat.ErrorsStatResponse;
import ml.socshared.gateway.domain.stat.userstat.UsersStatResponse;
import ml.socshared.gateway.domain.stat.usingsocial.UsingSocialNetworkResponse;
import ml.socshared.gateway.domain.user.UserResponse;
import ml.socshared.gateway.security.model.TokenObject;
import ml.socshared.gateway.service.StatService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class StatServiceImpl implements StatService {

    private final StatClient statClient;

    @Value("#{tokenGetter.tokenSystemStatistic}")
    private TokenObject tokenSystemStat;

    private String tokenSystemStat() {
        return "Bearer " + tokenSystemStat.getToken();
    }

    @Override
    public UsingSocialNetworkResponse getUsingSocialNetworkStat() {
        return statClient.getUsingSocialNetworkStat(tokenSystemStat());
    }

    @Override
    public UsersStatResponse getOnlineUsersCount() {
        return statClient.getOnlineUsersCount(tokenSystemStat());
    }

    @Override
    public List<UsersStatResponse> getOnlineUsersStatTimeline() {
        return statClient.getOnlineUsersStatTimeline(tokenSystemStat());
    }

    @Override
    public UsersStatResponse getActiveUsersCount() {
        return statClient.getActiveUsersCount(tokenSystemStat());
    }

    @Override
    public List<UsersStatResponse> getActiveUsersStatTimeline() {
        return statClient.getActiveUsersStatTimeline(tokenSystemStat());
    }

    @Override
    public UsersStatResponse getNewUsersCount() {
        return statClient.getNewUsersCount(tokenSystemStat());
    }

    @Override
    public List<UsersStatResponse> getNewUsersStatTimeline() {
        return statClient.getNewUsersStatTimeline(tokenSystemStat());
    }

    @Override
    public UsersStatResponse getAllUsersCount() {
        return statClient.getAllUsersCount(tokenSystemStat());
    }

    @Override
    public List<UsersStatResponse> getAllUsersStatTimeline() {
        return statClient.getAllUsersStatTimeline(tokenSystemStat());
    }

    @Override
    public RestResponsePage<UserResponse> getOnlineUsers(Integer page, Integer size) {
        return statClient.getOnlineUsers(page, size, tokenSystemStat());
    }

    @Override
    public RestResponsePage<UserResponse> getActiveUsers(Integer page, Integer size) {
        return statClient.getActiveUsers(page, size, tokenSystemStat());
    }

    @Override
    public RestResponsePage<UserResponse> getNewUsers(Integer page, Integer size) {
        return statClient.getNewUsers(page, size, tokenSystemStat());
    }

    @Override
    public RestResponsePage<UserResponse> getAllUsers(Integer page, Integer size) {
        return statClient.getAllUsers(page, size, tokenSystemStat());
    }

    @Override
    public ErrorsStatResponse getErrorsStat() {
        return statClient.getErrorsStat(tokenSystemStat());
    }
}
