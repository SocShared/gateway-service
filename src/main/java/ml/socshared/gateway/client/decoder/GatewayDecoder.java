package ml.socshared.gateway.client.decoder;

import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;
import ml.socshared.gateway.exception.impl.HttpBadRequestException;
import ml.socshared.gateway.exception.impl.HttpNotFoundException;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class GatewayDecoder implements ErrorDecoder {
    @Override
    public Exception decode(String methodKey, Response response) {
        //HashMap<Integer, >
        if (response.status() == 404) {
            String msg = methodKey + " return error: " + response.body().toString();
            log.warn(msg);
            return new HttpNotFoundException(msg);
        } else if(response.status() == 401) {
            log.warn("forbidden");
            return new HttpBadRequestException("forbidden");
        }
        String msg = "Unexpected error. Service client return status code: " + response.status();
        log.warn(msg);
        return new Exception(msg);
    }


}


