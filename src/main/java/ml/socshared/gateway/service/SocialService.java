package ml.socshared.gateway.service;

import ml.socshared.gateway.domain.response.UserResponse;

import java.util.List;
import java.util.UUID;

public interface SocialService {

    List<UserResponse> getAccountsSocialNetwork(UUID systemUserId);

}
