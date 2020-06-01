package ml.socshared.gateway.service;

import ml.socshared.gateway.domain.response.facebook.AccessUrlResponse;

public interface FacebookService {

    AccessUrlResponse getURLForAccess();

}
