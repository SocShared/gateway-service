package ml.socshared.gateway.controller.v1;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@Slf4j
@RequestMapping("api/v1")
@PreAuthorize("isAuthenticated()")
public class BstatController {

    @GetMapping("/protected/groups/{groupId}/stat")
    void getStatisticOfGroup(@PathVariable UUID groupId) {

    }
}
