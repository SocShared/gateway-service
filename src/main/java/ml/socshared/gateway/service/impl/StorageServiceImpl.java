package ml.socshared.gateway.service.impl;

import lombok.RequiredArgsConstructor;
import ml.socshared.gateway.client.StorageServiceClient;
import ml.socshared.gateway.domain.storage.response.GroupResponse;
import ml.socshared.gateway.domain.storage.response.PublicationResponse;
import ml.socshared.gateway.security.model.TokenObject;
import ml.socshared.gateway.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StorageServiceImpl implements StorageService {


    @Value("#{tokenGetter.tokenStorageService}")
    TokenObject tokenStorage;
    private final StorageServiceClient client;

    @Override
    public Page<GroupResponse> getGroupsList(UUID systemUserId, Pageable pageable) {
        return client.getGroupsByUserId(systemUserId, pageable.getPageNumber(), pageable.getPageSize(), storageAuthToken());
    }

    @Override
    public Page<PublicationResponse> getPostList(UUID systemUserId, UUID groupId, Pageable pageable) {
        return client.getPublicationsByGroupId(groupId, pageable.getPageNumber(), pageable.getPageSize(),
                                                storageAuthToken());
    }

    private String storageAuthToken() {
        return "Bearer " + tokenStorage.getToken();
    }
}
