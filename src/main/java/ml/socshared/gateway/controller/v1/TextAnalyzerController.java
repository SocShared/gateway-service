package ml.socshared.gateway.controller.v1;

import lombok.RequiredArgsConstructor;
import ml.socshared.gateway.domain.facebook.response.AccessUrlResponse;
import ml.socshared.gateway.domain.text_analyzer.response.KeyWordResponse;
import ml.socshared.gateway.service.TextAnalyzerService;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1")
@RequiredArgsConstructor
@PreAuthorize("isAuthenticated()")
@Validated
public class TextAnalyzerController {

    private final TextAnalyzerService textAnalyzerService;

    @PreAuthorize("hasRole('CONTENT_MANAGER')")
    @GetMapping(value = "/protected/text/keywords", produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public List<KeyWordResponse> getKeyWords(@NotNull @RequestBody String text,
                                             @RequestParam(value = "min_len", defaultValue = "2") Integer minLength,
                                             @RequestParam(value = "max_len", defaultValue  = "4") Integer maxLength) {
        return textAnalyzerService.getKeyWords(text, minLength, maxLength);
    }

}
