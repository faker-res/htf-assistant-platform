package com.htf.bigdata.gateway.config.configuration;

import com.netflix.hystrix.HystrixCommand;
import feign.codec.Decoder;
import feign.hystrix.HystrixFeign;
import feign.jackson.JacksonDecoder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;

@ConditionalOnClass({ HystrixCommand.class, HystrixFeign.class })
public class FeignDecoderConfiguration {

//    @Bean
//    public Retryer retryer() {
//        return new Retryer.Default(5000, 5000, 3);
//    }

    @Bean
    public Decoder decoder() {
        return new JacksonDecoder();
    }
}
