package com.htf.bigdata.gateway.component.client.fallback;

import feign.hystrix.FallbackFactory;
import com.htf.bigdata.gateway.component.client.IAssistanPlatformClient;
import com.htf.bigdata.gateway.model.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class AssistanPlatformFallbackFactory implements IAssistanPlatformClient,FallbackFactory<IAssistanPlatformClient> {

    static Logger logger = LogManager.getLogger(AssistanPlatformFallbackFactory.class);

    private Throwable throwable;

    private void handleError(Object request){
        logger.error(this.getClass().getSimpleName()+" for request "+ request);
        logger.error(throwable.getMessage(),throwable);
    }

    @Override
    public IAssistanPlatformClient create(Throwable throwable) {
        this.throwable = throwable;
        return this;
    }

    @Override
    public Response setToken(String username, String ticket) {
        handleError("username:"+username+" ticket:"+ticket);
        return null;
    }

    @Override
    public Response removeToken(String token) {
        handleError("token:"+token);
        return null;
    }

    @Override
    public Response verifyToken(String userId, String token) {
        handleError("userId:"+userId+" token:"+token);
        return null;
    }
}