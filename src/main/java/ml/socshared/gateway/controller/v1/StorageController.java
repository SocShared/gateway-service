package ml.socshared.gateway.controller.v1;

import lombok.extern.slf4j.Slf4j;
import ml.socshared.gateway.domain.storage.SocialNetwork;
import ml.socshared.gateway.domain.storage.request.GroupRequest;
import ml.socshared.gateway.domain.storage.response.Group;
import ml.socshared.gateway.domain.storage.response.GroupResponse;
import ml.socshared.gateway.domain.storage.response.PublicationResponse;
import ml.socshared.gateway.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/v1")
public class StorageController {
    //TODO получение systemUserId из токена
    UUID systemUserId = UUID.fromString("75853635-c91a-492b-9276-2b7d3511e0de");

    @Autowired
    StorageService service;


    @GetMapping("/groups/vk")
    Page<GroupResponse> getGroupsList(Pageable pageable,
                                      @RequestHeader(value = "Authorization", required = false) String authToken) {
        log.info("Request get groups list");
        Page<GroupResponse> res = service.getGroupsList(systemUserId, pageable);
        return res;
    }

    @GetMapping("/groups/{systemGroupId}/posts")
    Page<PublicationResponse> getPostList( @PathVariable UUID systemGroupId,
            Pageable pageable,
            @RequestHeader(value = "Authorization", required = false) String authToken) {
        log.info("request get post list of group id " + systemUserId);
        Page<PublicationResponse>  res = service.getPostList(systemUserId, systemGroupId, pageable);
        return res;
    }

    @PostMapping("/groups/vk/{socGroupId}")
    void addGroupToStorage(@PathVariable String socGroupId,
                           @RequestHeader(value = "Authorization", required = false) String authToken) {
        log.info("Request to add group to storage");
        service.addVkGroupToStorage(systemUserId, socGroupId);
    }

    //TODO add post
    //TODO add group;

}
