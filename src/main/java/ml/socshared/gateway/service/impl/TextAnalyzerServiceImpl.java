package ml.socshared.gateway.service.impl;

import lombok.RequiredArgsConstructor;
import ml.socshared.gateway.client.TextAnalyzerClient;
import ml.socshared.gateway.domain.text_analyzer.request.TextRequest;
import ml.socshared.gateway.domain.text_analyzer.response.KeyWordResponse;
import ml.socshared.gateway.security.model.TokenObject;
import ml.socshared.gateway.service.TextAnalyzerService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TextAnalyzerServiceImpl implements TextAnalyzerService {

    private final TextAnalyzerClient client;

    @Value("#{tokenGetter.tokenTextService}")
    private TokenObject textAnalyzerToken;

    @Override
    public List<KeyWordResponse> getKeyWords(String text, Integer minLength, Integer maxLength) {
        return client.extractKeyWords(new TextRequest(text), minLength, maxLength, "Bearer " + textAnalyzerToken.getToken());
    }
}
