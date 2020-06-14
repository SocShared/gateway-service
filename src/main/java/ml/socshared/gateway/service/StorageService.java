package ml.socshared.gateway.service;

import ml.socshared.gateway.domain.facebook.FacebookPage;
import ml.socshared.gateway.domain.facebook.response.FacebookGroupResponse;
import ml.socshared.gateway.domain.request.PostRequest;
import ml.socshared.gateway.domain.storage.response.GroupResponse;
import ml.socshared.gateway.domain.storage.response.PublicationResponse;
import ml.socshared.gateway.domain.vk.response.VkGroupResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface StorageService {
    Page<GroupResponse> getGroups(UUID systemUserId, Pageable pageable);
    Page<PublicationResponse> getPosts(UUID systemUserId, UUID groupId, Pageable pageable);
    PublicationResponse savePost(UUID systemUserId, PostRequest request);
    FacebookPage<FacebookGroupResponse> getGroupsFacebookWithSelected(UUID systemUserId, Pageable pageable);
    Page<VkGroupResponse> getGroupsVkWithSelected(UUID systemUserId, Pageable pageable);
    GroupResponse addVkGroupToStorage(UUID systemUserId, String vkGroupId);
    GroupResponse addFBGroupToStorage(UUID systemUserId, String fbGroupId);
    void deleteGroupByFbId(UUID systemUserId, String fbGroupId);
    void deleteGroupByVKId(UUID systemUserId, String vkGroupId);
    void deleteFacebookGroupsByUserId(UUID systemUserId);
    void deleteVkGroupsByUserId(UUID systemUserId);
}
