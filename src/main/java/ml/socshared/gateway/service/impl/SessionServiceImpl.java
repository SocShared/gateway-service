package ml.socshared.gateway.service.impl;

import lombok.RequiredArgsConstructor;
import ml.socshared.gateway.security.online.OnlineUsersStore;
import ml.socshared.gateway.service.SessionService;
import ml.socshared.gateway.service.sentry.SentrySender;
import ml.socshared.gateway.service.sentry.SentryTag;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class SessionServiceImpl implements SessionService {

    private final OnlineUsersStore onlineUsersStore;
    private final SentrySender sentrySender;

    @Override
    @Scheduled(fixedDelay = 10000)
    public void analyzeOnlineStatistic() {
        Map<String, Object> additionalData = new HashMap<>();
        additionalData.put("online_users", onlineUsersStore.getUsers().size());

        sentrySender.sentryMessage("online users", additionalData, Collections.singletonList(SentryTag.ONLINE_USERS));
    }
}
