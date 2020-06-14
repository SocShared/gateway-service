package ml.socshared.gateway.service;

import ml.socshared.gateway.domain.bstat.response.GroupInfoResponse;
import ml.socshared.gateway.domain.bstat.response.PostInfoByTimeResponse;
import ml.socshared.gateway.domain.storage.SocialNetwork;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

public interface BStatService {
    GroupInfoResponse getGroupInfo(UUID systemUserId, UUID systemGroupId,SocialNetwork soc, Long begin, Long end);
    PostInfoByTimeResponse getInfoVariabilityByTimeOfPost(UUID systemUserId, UUID systemGroupId, UUID systemPostId, SocialNetwork soc, Long begin, Long end);
}
