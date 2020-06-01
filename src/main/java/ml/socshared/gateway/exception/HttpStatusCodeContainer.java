package ml.socshared.gateway.exception;

import org.springframework.http.HttpStatus;

public interface HttpStatusCodeContainer {
    HttpStatus getHttpStatus();
}

