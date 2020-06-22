package ml.socshared.gateway.domain.user;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import lombok.Setter;
import ml.socshared.gateway.config.CustomLocalDateTimeSerializer;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
public class AuthUserResponse {

    private UUID userId;
    private String username;
    private String email;
    private boolean emailVerified;
    private String firstname;
    private String lastname;
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    private LocalDateTime timeOnline;
    private boolean accountNonLocked;
    private boolean resetPassword;
    private Set<RoleResponse> roles;

}
