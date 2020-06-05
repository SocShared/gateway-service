package ml.socshared.gateway.controller.v1;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ml.socshared.gateway.domain.storage.SocialNetwork;
import ml.socshared.gateway.domain.storage.request.GroupRequest;
import ml.socshared.gateway.domain.storage.response.Group;
import ml.socshared.gateway.domain.storage.response.GroupResponse;
import ml.socshared.gateway.domain.storage.response.PublicationResponse;
import ml.socshared.gateway.security.jwt.JwtTokenProvider;
import ml.socshared.gateway.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@PreAuthorize("isAuthenticated()")
public class StorageController {

    private final JwtTokenProvider jwtTokenProvider;
    private final StorageService service;

    @GetMapping("/protected/groups")
    @PreAuthorize("hasRole('CONTENT_MANAGER')")
    public Page<GroupResponse> getGroupsList(@Min(0) @NotNull @RequestParam(name = "page", required = false) Integer page,
                                             @Min(0) @Max(100) @NotNull @RequestParam(name = "size", required = false) Integer size,
                                             HttpServletRequest request) {
        log.info("Request get groups list");
        UUID systemUserId = jwtTokenProvider.getUserId(jwtTokenProvider.resolveToken(request));
        return service.getGroups(systemUserId, PageRequest.of(page, size));
    }

    @PreAuthorize("hasRole('CONTENT_MANAGER')")
    @GetMapping("/protected/groups/{systemGroupId}/posts")
    public Page<PublicationResponse> getPostList(@PathVariable UUID systemGroupId,
                                                 @Min(0) @NotNull @RequestParam(name = "page", required = false) Integer page,
                                                 @Min(0) @Max(100) @NotNull @RequestParam(name = "size", required = false) Integer size,
                                           HttpServletRequest request) {
        log.info("request get post list of group id");
        UUID systemUserId = jwtTokenProvider.getUserId(jwtTokenProvider.resolveToken(request));
        return service.getPosts(systemUserId, systemGroupId, PageRequest.of(page, size));
    }

    @PreAuthorize("hasRole('CONTENT_MANAGER')")
    @PostMapping("/protected/vk/groups/{vkGroupId}")
    public void addVkGroupToStorage(@PathVariable String vkGroupId, HttpServletRequest request) {
        log.info("Request to add vk group to storage");
        UUID systemUserId = jwtTokenProvider.getUserId(jwtTokenProvider.resolveToken(request));
        service.addVkGroupToStorage(systemUserId, vkGroupId);
    }

    @PreAuthorize("hasRole('CONTENT_MANAGER')")
    @PostMapping("/protected/fb/groups/{fbGroupId}")
    public GroupResponse addFbGroupToStorage(@PathVariable String fbGroupId, HttpServletRequest request) {
        return service.addFBGroupToStorage(jwtTokenProvider.getUserId(jwtTokenProvider.resolveToken(request)), fbGroupId);
    }

    @PreAuthorize("hasRole('CONTENT_MANAGER')")
    @DeleteMapping("/protected/fb/groups/{fbGroupId}")
    public void deleteFbGroup(@PathVariable String fbGroupId, HttpServletRequest request) {
        service.deleteGroupByFbId(jwtTokenProvider.getUserId(jwtTokenProvider.resolveToken(request)), fbGroupId);
    }

    @PreAuthorize("hasRole('CONTENT_MANAGER')")
    @DeleteMapping("/protected/vk/groups/{vkGroupId}")
    public void deleteVKGroup(@PathVariable String vkGroupId, HttpServletRequest request) {
        service.deleteGroupByVKId(jwtTokenProvider.getUserId(jwtTokenProvider.resolveToken(request)), vkGroupId);
    }
}
