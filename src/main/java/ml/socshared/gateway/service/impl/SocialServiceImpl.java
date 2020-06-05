package ml.socshared.gateway.service.impl;

import lombok.RequiredArgsConstructor;
import ml.socshared.gateway.domain.response.UserResponse;
import ml.socshared.gateway.service.FacebookService;
import ml.socshared.gateway.service.SocialService;
import ml.socshared.gateway.service.VkService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SocialServiceImpl implements SocialService {

    private final FacebookService facebookService;
    private final VkService vkService;

    @Override
    public List<UserResponse> getAccountsSocialNetwork(UUID systemUserId) {
        UserResponse facebookAccount = facebookService.getUserDataFacebookAccount(systemUserId);

        List<UserResponse> accounts = new ArrayList<>();
        if (facebookAccount != null) {
            accounts.add(facebookAccount);
        }

        UserResponse vkAccount = vkService.getUserDataVkAccount(systemUserId);
        if(vkAccount != null) {
            accounts.add(vkAccount);
        }

        return accounts;
    }
}
