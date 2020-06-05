package ml.socshared.gateway.service;

import ml.socshared.gateway.domain.text_analyzer.response.KeyWordResponse;

import java.util.List;

public interface TextAnalyzerService {

    List<KeyWordResponse> getKeyWords(String text, Integer minLength, Integer maxLength);

}
