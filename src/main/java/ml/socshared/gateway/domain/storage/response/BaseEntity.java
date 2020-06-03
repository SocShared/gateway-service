package ml.socshared.gateway.domain.storage.response;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;


@Getter
@Setter
public abstract class BaseEntity {
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Status status;
    public BaseEntity() {
        this.status = Status.ACTIVE;
    }
}
