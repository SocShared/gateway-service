package ml.socshared.gateway.controller.v1;


import lombok.extern.slf4j.Slf4j;
import ml.socshared.gateway.api.v1.rest.VkApi;
import ml.socshared.gateway.client.VkServiceClient;
import ml.socshared.gateway.domain.response.vk_adapter.GroupResponse;
import ml.socshared.gateway.domain.response.vk_adapter.PageAdapter;
import ml.socshared.gateway.domain.SuccessResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequestMapping("/api/v1")
@RestController
@Slf4j
public class VkController implements VkApi {

    VkServiceClient client;

    VkController(VkServiceClient c) {
        client = c;
    }


    @Override
    @PostMapping("/protected/social/vk/app")
    public SuccessResponse setToken( @RequestBody String accessToken,
                                    @RequestHeader(value = "Authorization", required = false) String token) {
       log.info("Request add vk app");
        UUID systemUserId = UUID.fromString("75853635-c91a-492b-9276-2b7d3511e0de");
        token = "RequiredToken!";
        client.appRegister(systemUserId, accessToken, token);
        return new SuccessResponse(true);
    }

    @Override
    @GetMapping("/protected/social/vk/groups")
    public Page<GroupResponse> getGroupUserList(@Param("page") Integer page,
                                                @Param("size") Integer size,
                                                @RequestHeader(value = "Authorization", required = false) String token) {
        UUID systemUserId = UUID.fromString("75853635-c91a-492b-9276-2b7d3511e0de");
        token = "RequiredToken!";
        PageAdapter<GroupResponse> res = client.getGroups(systemUserId, page, size, token);
        Page<GroupResponse> pageOfGroup = new PageImpl<>(res.getObject());
        return pageOfGroup;

    }

}
