package ml.socshared.gateway.service;

import ml.socshared.gateway.domain.user.UserResponse;

import java.util.UUID;

public interface AuthInfoService {
    UserResponse getClientInfoById(UUID clientId);
}
