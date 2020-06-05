package ml.socshared.gateway.domain.text_analyzer.response;

import lombok.Data;

@Data
public class KeyWordResponse {
    private String keyWord;
    private double score;
}
