package ml.socshared.gateway.client.decoder;

import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;
import ml.socshared.gateway.exception.AbstractRestHandleableException;
import ml.socshared.gateway.exception.impl.HttpBadRequestException;
import ml.socshared.gateway.exception.impl.HttpForbiddenException;
import ml.socshared.gateway.exception.impl.HttpNotFoundException;
import ml.socshared.gateway.exception.impl.HttpUnauthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

//@Component
@Slf4j
public class FeignErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {
        String msg = "error: ";
        if (response.body() != null)
            msg += response.body().toString();
        else
            msg += response.status() + " undefined";

        if (response.status() == 404) {
            log.warn(msg);
            return new HttpNotFoundException(msg);
        } else if (response.status() == 401) {
            return new HttpUnauthorizedException("unauthorized");
        } else if (response.status() == 403) {
            return new HttpForbiddenException("forbidden");
        } else if (response.status() == 400) {
            log.warn(msg);
            return new HttpBadRequestException(msg);
        }

        return new AbstractRestHandleableException(msg, HttpStatus.valueOf(response.status())) {
            @Override
            public HttpStatus getHttpStatus() {
                return super.getHttpStatus();
            }
        };
    }


}


