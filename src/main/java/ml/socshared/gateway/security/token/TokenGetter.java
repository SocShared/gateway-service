package ml.socshared.gateway.security.token;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ml.socshared.gateway.security.jwt.JwtTokenProvider;
import ml.socshared.gateway.security.model.TokenObject;
import ml.socshared.gateway.security.request.ServiceTokenRequest;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
@Aspect
@Getter
public class TokenGetter {

    private final JwtTokenProvider jwtTokenProvider;

    public TokenGetter(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
        init();
    }

    private TokenObject tokenFB;
    private TokenObject tokenVK;
    private TokenObject tokenBSTAT;
    private TokenObject tokenServiceWorker;
    private TokenObject tokenSystemStatistic;
    private TokenObject tokenTechSupport;
    private TokenObject tokenStorageService;
    private TokenObject tokenTextService;
    private TokenObject tokenMailSender;
    private TokenObject tokenAuth;

    private void init() {
        tokenFB = new TokenObject();
        tokenVK = new TokenObject();
        tokenBSTAT = new TokenObject();
        tokenServiceWorker = new TokenObject();
        tokenSystemStatistic = new TokenObject();
        tokenTechSupport = new TokenObject();
        tokenStorageService = new TokenObject();
        tokenTextService = new TokenObject();
        tokenMailSender = new TokenObject();
        tokenAuth = new TokenObject();

    }

    @Before("execution(* ml.socshared.gateway.service.impl.FacebookServiceImpl.*(..))")
    public TokenObject initTokenFB() {
        if (tokenFB.getToken() != null && jwtTokenProvider.validateServiceToken(tokenFB.getToken())) {
            log.debug(tokenFB.getToken());
            return tokenFB;
        }

        ServiceTokenRequest request = new ServiceTokenRequest();
        request.setFromServiceId(UUID.fromString("9e671e7d-976f-40d6-a8c4-67912ae12ede"));
        request.setToServiceId(UUID.fromString("f7e14d85-415c-4ab9-b285-a6481d79f507"));
        request.setToSecretService(UUID.fromString("427d82bb-b367-40b4-bee8-b18e32480899"));

        this.tokenFB.setToken(jwtTokenProvider.buildServiceToken(request).getToken());
        log.debug(tokenFB.getToken());
        return tokenFB;
    }

    @Before("execution(* ml.socshared.gateway.service.impl.VkServiceImpl.*(..))")
    public TokenObject initTokenVK() {
        if (tokenVK != null && jwtTokenProvider.validateServiceToken(tokenVK.getToken())) {
            return tokenVK;
        }

        ServiceTokenRequest request = new ServiceTokenRequest();
        request.setFromServiceId(UUID.fromString("9e671e7d-976f-40d6-a8c4-67912ae12ede"));
        request.setToServiceId(UUID.fromString("cb43eee3-3468-4cc2-b6ed-63419e8726ce"));
        request.setToSecretService(UUID.fromString("f769cb1c-bf08-478d-8218-0bb347369dd7"));

        this.tokenVK.setToken(jwtTokenProvider.buildServiceToken(request).getToken());

        return tokenVK;
    }

    @Before("execution(* ml.socshared.gateway.service.impl.BStatServiceImpl.*(..))")
    public TokenObject initTokenBSTAT() {
        if (tokenBSTAT != null && jwtTokenProvider.validateServiceToken(tokenBSTAT.getToken())) {
            return tokenBSTAT;
        }

        ServiceTokenRequest request = new ServiceTokenRequest();
        request.setFromServiceId(UUID.fromString("9e671e7d-976f-40d6-a8c4-67912ae12ede"));
        request.setToServiceId(UUID.fromString("e7ee788d-c59e-4a96-bdaf-52d6b33df1f3"));
        request.setToSecretService(UUID.fromString("b8500899-b1a1-4b99-984f-08aed46d1aea"));

        this.tokenBSTAT.setToken(jwtTokenProvider.buildServiceToken(request).getToken());

        return tokenBSTAT;
    }

    public TokenObject initTokenServiceWorker() {
        if (tokenServiceWorker != null && jwtTokenProvider.validateServiceToken(tokenServiceWorker.getToken())) {
            return tokenServiceWorker;
        }

        ServiceTokenRequest request = new ServiceTokenRequest();
        request.setFromServiceId(UUID.fromString("9e671e7d-976f-40d6-a8c4-67912ae12ede"));
        request.setToServiceId(UUID.fromString("25086e71-269b-46ff-aa48-23f7ffba3bf9"));
        request.setToSecretService(UUID.fromString("880bc772-a207-4357-b7c9-821fcee85662"));

        this.tokenServiceWorker.setToken(jwtTokenProvider.buildServiceToken(request).getToken());

        return tokenServiceWorker;
    }


    @Before("execution(* ml.socshared.gateway.service.impl.StatServiceImpl.*(..))")
    public TokenObject initTokenSystemStatistic() {
        if (tokenSystemStatistic != null && jwtTokenProvider.validateServiceToken(tokenSystemStatistic.getToken())) {
            return tokenSystemStatistic;
        }

        ServiceTokenRequest request = new ServiceTokenRequest();
        request.setFromServiceId(UUID.fromString("9e671e7d-976f-40d6-a8c4-67912ae12ede"));
        request.setToServiceId(UUID.fromString("eeb4585c-1d8f-463c-b441-e5dbb27ec94d"));
        request.setToSecretService(UUID.fromString("fcf25e23-fe55-4df7-b8f1-e5e56d1277fc"));

        this.tokenSystemStatistic.setToken(jwtTokenProvider.buildServiceToken(request).getToken());

        return tokenSystemStatistic;
    }

    @Before("execution(* ml.socshared.gateway.service.impl.TechSupportServiceImpl.*(..))")
    public TokenObject initTokenTechSupport() {
        if (tokenTechSupport != null && jwtTokenProvider.validateServiceToken(tokenTechSupport.getToken())) {
            return tokenTechSupport;
        }

        ServiceTokenRequest request = new ServiceTokenRequest();
        request.setFromServiceId(UUID.fromString("9e671e7d-976f-40d6-a8c4-67912ae12ede"));
        request.setToServiceId(UUID.fromString("31a2ee92-0e6c-45b7-b6cb-810eec2f1041"));
        request.setToSecretService(UUID.fromString("48733b84-9434-4893-9091-cb855c586ca2"));

        this.tokenTechSupport.setToken(jwtTokenProvider.buildServiceToken(request).getToken());

        return tokenTechSupport;
    }

    @Before("execution(* ml.socshared.gateway.service.impl.StorageServiceImpl.*(..))")
    public TokenObject initTokenStorageService() {
        if (tokenStorageService != null && jwtTokenProvider.validateServiceToken(tokenStorageService.getToken())) {
            return tokenStorageService;
        }

        ServiceTokenRequest request = new ServiceTokenRequest();
        request.setFromServiceId(UUID.fromString("9e671e7d-976f-40d6-a8c4-67912ae12ede"));
        request.setToServiceId(UUID.fromString("64141ce5-5604-4ade-ada2-e38cf7d2522c"));
        request.setToSecretService(UUID.fromString("5b21977e-166f-471b-a7a7-c60b20e18cf9"));

        this.tokenStorageService.setToken(jwtTokenProvider.buildServiceToken(request).getToken());

        return tokenStorageService;
    }

    @Before("execution(* ml.socshared.gateway.service.impl.TextAnalyzerServiceImpl.*(..))")
    public TokenObject initTokenTextService() {
        if (tokenTextService != null && jwtTokenProvider.validateServiceToken(tokenTextService.getToken())) {
            return tokenTextService;
        }

        ServiceTokenRequest request = new ServiceTokenRequest();
        request.setFromServiceId(UUID.fromString("9e671e7d-976f-40d6-a8c4-67912ae12ede"));
        request.setToServiceId(UUID.fromString("58aeed0d-d092-455b-a1a6-dccfea5b89c6"));
        request.setToSecretService(UUID.fromString("98650932-32df-495a-afeb-9c08bdccd200"));

        this.tokenTextService.setToken(jwtTokenProvider.buildServiceToken(request).getToken());

        return tokenTextService;
    }

    public TokenObject initTokenMailSender() {
        if (tokenMailSender != null && jwtTokenProvider.validateServiceToken(tokenMailSender.getToken())) {
            return tokenMailSender;
        }

        ServiceTokenRequest request = new ServiceTokenRequest();
        request.setFromServiceId(UUID.fromString("9e671e7d-976f-40d6-a8c4-67912ae12ede"));
        request.setToServiceId(UUID.fromString("68c5c6d9-fb18-4adb-800e-faac3ac745b9"));
        request.setToSecretService(UUID.fromString("a981045d-e269-4b28-b7b7-af4a885b9dc4"));

        this.tokenMailSender.setToken(jwtTokenProvider.buildServiceToken(request).getToken());

        return tokenMailSender;
    }


    @Before("execution(* ml.socshared.gateway.service.impl.AuthServiceImpl.*(..))")
    public TokenObject initTokenAuth() {
        if (tokenAuth.getToken() != null && jwtTokenProvider.validateServiceToken(tokenAuth.getToken())) {
            return tokenAuth;
        }

        ServiceTokenRequest request = new ServiceTokenRequest();
        request.setFromServiceId(UUID.fromString("9e671e7d-976f-40d6-a8c4-67912ae12ede"));
        request.setToServiceId(UUID.fromString("58c2b3d5-dfad-41af-9451-d0a26fdc9019"));
        request.setToSecretService(UUID.fromString("0cb9bb2e-ee6a-48b7-b36a-23fb07f3fa28"));

        this.tokenAuth.setToken(jwtTokenProvider.buildServiceToken(request).getToken());
        log.debug(tokenAuth.getToken());
        return tokenAuth;
    }

}
