package ml.socshared.gateway.security.token;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ml.socshared.gateway.security.jwt.JwtTokenProvider;
import ml.socshared.gateway.security.request.ServiceTokenRequest;
import org.springframework.stereotype.Service;

import java.util.UUID;

@RequiredArgsConstructor
@Service
@Slf4j
public class TokenGetter {

    private final JwtTokenProvider jwtTokenProvider;

    private String tokenFB;
    private String tokenVK;
    private String tokenBSTAT;
    private String tokenServiceWorker;
    private String tokenSystemStatistic;
    private String tokenTechSupport;
    private String tokenStorageService;
    private String tokenTextService;
    private String tokenMailSender;

    public String getTokenFB() {
        if (tokenFB != null && jwtTokenProvider.validateServiceToken(tokenFB)) {
            return tokenFB;
        }

        ServiceTokenRequest request = new ServiceTokenRequest();
        request.setFromServiceId(UUID.fromString("9e671e7d-976f-40d6-a8c4-67912ae12ede"));
        request.setToServiceId(UUID.fromString("f7e14d85-415c-4ab9-b285-a6481d79f507"));
        request.setToSecretService(UUID.fromString("427d82bb-b367-40b4-bee8-b18e32480899"));

        return "Bearer " + jwtTokenProvider.buildServiceToken(request).getToken();
    }

    public String getTokenVK() {
        if (tokenVK != null && jwtTokenProvider.validateServiceToken(tokenVK)) {
            return tokenVK;
        }

        ServiceTokenRequest request = new ServiceTokenRequest();
        request.setFromServiceId(UUID.fromString("9e671e7d-976f-40d6-a8c4-67912ae12ede"));
        request.setToServiceId(UUID.fromString("cb43eee3-3468-4cc2-b6ed-63419e8726ce"));
        request.setToSecretService(UUID.fromString("f769cb1c-bf08-478d-8218-0bb347369dd7"));

        return "Bearer " + jwtTokenProvider.buildServiceToken(request).getToken();
    }

    public String getTokenBSTAT() {
        if (tokenBSTAT != null && jwtTokenProvider.validateServiceToken(tokenBSTAT)) {
            return tokenBSTAT;
        }

        ServiceTokenRequest request = new ServiceTokenRequest();
        request.setFromServiceId(UUID.fromString("9e671e7d-976f-40d6-a8c4-67912ae12ede"));
        request.setToServiceId(UUID.fromString("e7ee788d-c59e-4a96-bdaf-52d6b33df1f3"));
        request.setToSecretService(UUID.fromString("b8500899-b1a1-4b99-984f-08aed46d1aea"));

        return "Bearer " + jwtTokenProvider.buildServiceToken(request).getToken();
    }

    public String getTokenServiceWorker() {
        if (tokenServiceWorker != null && jwtTokenProvider.validateServiceToken(tokenServiceWorker)) {
            return tokenServiceWorker;
        }

        ServiceTokenRequest request = new ServiceTokenRequest();
        request.setFromServiceId(UUID.fromString("9e671e7d-976f-40d6-a8c4-67912ae12ede"));
        request.setToServiceId(UUID.fromString("25086e71-269b-46ff-aa48-23f7ffba3bf9"));
        request.setToSecretService(UUID.fromString("880bc772-a207-4357-b7c9-821fcee85662"));

        return "Bearer " + jwtTokenProvider.buildServiceToken(request).getToken();
    }

    public String getTokenSystemStatistic() {
        if (tokenSystemStatistic != null && jwtTokenProvider.validateServiceToken(tokenSystemStatistic)) {
            return tokenSystemStatistic;
        }

        ServiceTokenRequest request = new ServiceTokenRequest();
        request.setFromServiceId(UUID.fromString("9e671e7d-976f-40d6-a8c4-67912ae12ede"));
        request.setToServiceId(UUID.fromString("eeb4585c-1d8f-463c-b441-e5dbb27ec94d"));
        request.setToSecretService(UUID.fromString("fcf25e23-fe55-4df7-b8f1-e5e56d1277fc"));

        return "Bearer " + jwtTokenProvider.buildServiceToken(request).getToken();
    }

    public String getTokenTechSupport() {
        if (tokenTechSupport != null && jwtTokenProvider.validateServiceToken(tokenTechSupport)) {
            return tokenTechSupport;
        }

        ServiceTokenRequest request = new ServiceTokenRequest();
        request.setFromServiceId(UUID.fromString("9e671e7d-976f-40d6-a8c4-67912ae12ede"));
        request.setToServiceId(UUID.fromString("31a2ee92-0e6c-45b7-b6cb-810eec2f1041"));
        request.setToSecretService(UUID.fromString("48733b84-9434-4893-9091-cb855c586ca2"));

        return "Bearer " + jwtTokenProvider.buildServiceToken(request).getToken();
    }

    public String getTokenStorageService() {
        if (tokenStorageService != null && jwtTokenProvider.validateServiceToken(tokenStorageService)) {
            return tokenStorageService;
        }

        ServiceTokenRequest request = new ServiceTokenRequest();
        request.setFromServiceId(UUID.fromString("9e671e7d-976f-40d6-a8c4-67912ae12ede"));
        request.setToServiceId(UUID.fromString("64141ce5-5604-4ade-ada2-e38cf7d2522c"));
        request.setToSecretService(UUID.fromString("5b21977e-166f-471b-a7a7-c60b20e18cf9"));

        return "Bearer " + jwtTokenProvider.buildServiceToken(request).getToken();
    }

    public String getTokenTextService() {
        if (tokenTextService != null && jwtTokenProvider.validateServiceToken(tokenTextService)) {
            return tokenTextService;
        }

        ServiceTokenRequest request = new ServiceTokenRequest();
        request.setFromServiceId(UUID.fromString("9e671e7d-976f-40d6-a8c4-67912ae12ede"));
        request.setToServiceId(UUID.fromString("58aeed0d-d092-455b-a1a6-dccfea5b89c6"));
        request.setToSecretService(UUID.fromString("98650932-32df-495a-afeb-9c08bdccd200"));

        return "Bearer " + jwtTokenProvider.buildServiceToken(request).getToken();
    }

    public String getTokenMailSender() {
        if (tokenMailSender != null && jwtTokenProvider.validateServiceToken(tokenMailSender)) {
            return tokenMailSender;
        }

        ServiceTokenRequest request = new ServiceTokenRequest();
        request.setFromServiceId(UUID.fromString("9e671e7d-976f-40d6-a8c4-67912ae12ede"));
        request.setToServiceId(UUID.fromString("68c5c6d9-fb18-4adb-800e-faac3ac745b9"));
        request.setToSecretService(UUID.fromString("a981045d-e269-4b28-b7b7-af4a885b9dc4"));

        return "Bearer " + jwtTokenProvider.buildServiceToken(request).getToken();
    }

}
