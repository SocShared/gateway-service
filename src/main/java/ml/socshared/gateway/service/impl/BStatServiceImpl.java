package ml.socshared.gateway.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ml.socshared.gateway.client.BStatClient;
import ml.socshared.gateway.domain.bstat.response.GroupInfoResponse;
import ml.socshared.gateway.domain.bstat.response.PostInfoByTimeResponse;
import ml.socshared.gateway.domain.storage.SocialNetwork;
import ml.socshared.gateway.security.model.TokenObject;
import ml.socshared.gateway.service.BStatService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class BStatServiceImpl implements BStatService {

    private final BStatClient bStatClient;

    @Value("#{tokenGetter.tokenBSTAT}")
    TokenObject bStatToken;

    private String authBStatToken() {
        return "Bearer " + bStatToken.getToken();
    }

    @Override
    public GroupInfoResponse getGroupInfo(UUID systemUserId, UUID systemGroupId, SocialNetwork soc, Long begin, Long end) {
        GroupInfoResponse response = bStatClient.getGroupInfo(systemUserId, systemGroupId, soc, begin, end, authBStatToken());
        return  response;
    }

    @Override
    public PostInfoByTimeResponse getInfoVariabilityByTimeOfPost(UUID systemUserId, UUID systemGroupId, UUID systemPostId, SocialNetwork soc, Long begin, Long end) {
        return bStatClient.getInfoVariabilityByTimeOfPost(systemUserId, systemGroupId,systemPostId, soc,  begin, end, authBStatToken());
    }
}
