package ml.socshared.gateway.service;

import ml.socshared.gateway.domain.facebook.response.AccessUrlResponse;

public interface FacebookService {

    AccessUrlResponse getURLForAccess();

}
