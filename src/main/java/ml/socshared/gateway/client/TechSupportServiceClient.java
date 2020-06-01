package ml.socshared.gateway.client;


import feign.Headers;
import ml.socshared.gateway.domain.response.texch_support.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Pageable;

@FeignClient(name = "TechSupportServiceClient", url = "${feign.tech-support.url}")
public interface TechSupportServiceClient {

    @GetMapping("/api/v1/private/questions?page={page}&size={size}")
    PageResponse<ShortQuestion> getQuestionList(@PathVariable Integer page,
                                                @PathVariable Integer size,
                                                @RequestHeader("Authorization") String authToken);

    @GetMapping("/api/v1/private/questions/{questionId}?page={page}&size={size}")
    FullQuestion getFullQuestion(@PathVariable Integer questionId,
                                 @PathVariable Integer page,
                                 @PathVariable Integer size,
                                 @RequestHeader("Authorization") String authToken);

    @PostMapping("api/v1/private/questions")
    Integer addQuestion(@RequestBody Question question,
                        @RequestHeader("Authorization") String authToken);

    @PostMapping("api/v1/private/questions/{questionId}/comments")
    Integer addCommentToQuestion(@PathVariable Integer questionId, @RequestBody Comment comment,
                                 @RequestHeader("Authorization") String authToken);

    @DeleteMapping("api/v1/private/questions/{questionId}")
    void removeQuestion(@PathVariable Integer questionId,
                               @RequestHeader("Authorization") String authToken);

    @DeleteMapping("api/v1/private/questions/{questionId}/comments/{commentId}")
    void removeComment(@PathVariable Integer questionId,@PathVariable Integer commentId,
                              @RequestHeader("Authorization") String authToken);


}
