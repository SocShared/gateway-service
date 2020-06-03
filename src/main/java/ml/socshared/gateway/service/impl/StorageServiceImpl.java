package ml.socshared.gateway.service.impl;

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
public class StorageServiceImpl implements StorageService {


    @Value("#{tokenGetter.tokenStorageService}")
    TokenObject tokenStorage;

    StorageServiceClient client;

    @Autowired
    StorageServiceImpl(StorageServiceClient client) {
        this.client = client;
    }

    @Override
    public Page<GroupResponse> getGroupsList(UUID systemUserId, Pageable pageable) {

        return client.getGroupsByUserId(systemUserId, pageable.getPageNumber(), pageable.getPageSize(), serviceAuthToken());
    }

    @Override
    public Page<PublicationResponse> getPostList(UUID systemUserId, UUID groupId, Pageable pageable) {
        //TODO проверка на точ то данному пользователю принадлежит данная группа
        return client.getPublicationsByGroupId(groupId, pageable.getPageNumber(), pageable.getPageSize());
    }

    private String serviceAuthToken() {
        return "Bearer " + tokenStorage.getToken();
    }
}
