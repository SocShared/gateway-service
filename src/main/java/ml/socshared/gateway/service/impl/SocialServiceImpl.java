package ml.socshared.gateway.service.impl;

import lombok.RequiredArgsConstructor;
import ml.socshared.gateway.domain.response.UserResponse;
import ml.socshared.gateway.service.FacebookService;
import ml.socshared.gateway.service.SocialService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SocialServiceImpl implements SocialService {

    private final FacebookService facebookService;

    @Override
    public List<UserResponse> getAccountsSocialNetwork(UUID systemUserId) {
        UserResponse facebookAccount = facebookService.getUserDataFacebookAccount(systemUserId);

        List<UserResponse> accounts = new ArrayList<>();
        if (facebookAccount != null) {
            accounts.add(facebookAccount);
        }

        // TODO: Требуется добавить получение аккаунта VK

        return accounts;
    }
}
