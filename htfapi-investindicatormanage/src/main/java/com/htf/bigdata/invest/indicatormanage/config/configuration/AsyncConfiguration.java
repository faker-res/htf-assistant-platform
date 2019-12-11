package com.htf.bigdata.invest.indicatormanage.config.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

/**
 * @description: 多线程配置
 * @author: panpei
 * @date: 2019/6/13
 */
@Configuration
public class AsyncConfiguration {

    @Value("${async.corePoolSize}")
    private Integer corePoolSize;

    @Value("${async.maxPoolSize}")
    private Integer maxPoolSize;

    @Value("${async.queueCapacity}")
    private Integer queueCapacity;

    @Bean
    public Executor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(corePoolSize);
        executor.setMaxPoolSize(corePoolSize);
        executor.setQueueCapacity(queueCapacity);
        executor.initialize();
        return executor;
    }

}
