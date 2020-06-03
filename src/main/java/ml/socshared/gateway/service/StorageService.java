package ml.socshared.gateway.service;

import ml.socshared.gateway.domain.storage.response.GroupResponse;
import ml.socshared.gateway.domain.storage.response.PublicationResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface StorageService {
    Page<GroupResponse> getGroupsList(UUID systemUserId, Pageable pageable);
    Page<PublicationResponse> getPostList(UUID systemUserId, UUID groupId,Pageable pageable);
}
