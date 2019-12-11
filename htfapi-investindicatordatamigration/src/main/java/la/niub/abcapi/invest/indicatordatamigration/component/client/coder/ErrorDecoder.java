package la.niub.abcapi.invest.indicatordatamigration.component.client.coder;

import com.netflix.hystrix.exception.HystrixBadRequestException;
import feign.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

//@Configuration
public class ErrorDecoder implements feign.codec.ErrorDecoder {

    static Logger logger = LogManager.getLogger(ErrorDecoder.class);

    @Override
    public Exception decode(String methodKey, Response response) {
        logger.error(response.reason());
        if(response.status() >= 400 && response.status() <= 499){
            return new HystrixBadRequestException("request exception wrapper");
        }
        return feign.FeignException.errorStatus(methodKey, response);
    }
}
