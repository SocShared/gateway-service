package ml.socshared.gateway.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ml.socshared.gateway.client.StatClient;
import ml.socshared.gateway.domain.stat.errorstat.ErrorsStatResponse;
import ml.socshared.gateway.domain.stat.userstat.UsersStatResponse;
import ml.socshared.gateway.domain.stat.usingsocial.UsingSocialNetworkResponse;
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
    public UsersStatResponse getOnlineUsersStat() {
        return statClient.getOnlineUsersStat(tokenSystemStat());
    }

    @Override
    public List<UsersStatResponse> getOnlineUsersStatTimeline() {
        return statClient.getOnlineUsersStatTimeline(tokenSystemStat());
    }

    @Override
    public UsersStatResponse getActiveUsersStat() {
        return statClient.getActiveUsersStat(tokenSystemStat());
    }

    @Override
    public List<UsersStatResponse> getActiveUsersStatTimeline() {
        return statClient.getActiveUsersStatTimeline(tokenSystemStat());
    }

    @Override
    public UsersStatResponse getNewUsersStat() {
        return statClient.getNewUsersStat(tokenSystemStat());
    }

    @Override
    public List<UsersStatResponse> getNewUsersStatTimeline() {
        return statClient.getNewUsersStatTimeline(tokenSystemStat());
    }

    @Override
    public UsersStatResponse getAllUsersStat() {
        return statClient.getAllUsersStat(tokenSystemStat());
    }

    @Override
    public List<UsersStatResponse> getAllUsersStatTimeline() {
        return statClient.getAllUsersStatTimeline(tokenSystemStat());
    }

    @Override
    public ErrorsStatResponse getErrorsStat() {
        return statClient.getErrorsStat(tokenSystemStat());
    }
}
