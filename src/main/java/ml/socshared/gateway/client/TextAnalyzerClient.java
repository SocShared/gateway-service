package ml.socshared.gateway.client;

import ml.socshared.gateway.domain.text_analyzer.request.TextRequest;
import ml.socshared.gateway.domain.text_analyzer.response.KeyWordResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "text-analyzer", url = "${feign.url.text}")
public interface TextAnalyzerClient {

    @PostMapping("/api/v1/private/key_words")
    List<KeyWordResponse> extractKeyWords(@RequestBody TextRequest text,
                                                 @RequestParam(value = "min_len", defaultValue = "2") Integer minLength,
                                                 @RequestParam(value = "max_len", defaultValue  = "4") Integer maxLength,
                                                 @RequestHeader("Authorization") String token);

}
