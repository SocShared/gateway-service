package ml.socshared.gateway.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ml.socshared.gateway.domain.response.UserResponse;
import ml.socshared.gateway.service.FacebookService;
import ml.socshared.gateway.service.SocialService;
import ml.socshared.gateway.service.VkService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class SocialServiceImpl implements SocialService {

    private final FacebookService facebookService;
    private final VkService vkService;

    @Override
    public List<UserResponse> getAccountsSocialNetwork(UUID systemUserId) {
        List<UserResponse> accounts = new ArrayList<>();
        try {
           UserResponse facebookAccount = facebookService.getUserDataFacebookAccount(systemUserId);

           if (facebookAccount != null) {
               accounts.add(facebookAccount);
           }
       } catch (Exception exp) {
           log.warn("FB returned error -> {}", exp);
       }




        try {
            UserResponse vkAccount = vkService.getUserDataVkAccount(systemUserId);
            if(vkAccount != null) {
                accounts.add(vkAccount);
            }
        } catch (Exception exp) {
            log.warn("Vk returned error -> {}", exp.getMessage());
        }


        return accounts;
    }
}
