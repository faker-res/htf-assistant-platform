package com.htf.bigdata.invest.platform.config.configuration;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisNode;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * redis配置文件
 * @author wb-wuxiao 
 */
@Configuration
public class RedisInvestConfiguration {

    @Value("${spring.redis.invest.host}")
    private String host;

    @Value("${spring.redis.invest.port}")
    private Integer port;

    @Value("${spring.redis.invest.password}")
    private String password;

    @Value("${spring.redis.invest.database}")
    private Integer database;

    @Value("${spring.redis.invest.timeout}")
    private Integer timeout;

//    @Value("${spring.redis.invest.masterName}")
//    private String masterName;
//
//    @Value("${spring.redis.invest.nodes}")
//    private String nodes;

    public final static String NAME = "investRedis";

    @Primary
    @Bean(name=NAME)
    public RedisTemplate redisTemplate(@Qualifier("investConnectionFactory") RedisConnectionFactory connectionFactory){
        RedisTemplate template = new RedisTemplate();
        template.setConnectionFactory(connectionFactory);
        template.afterPropertiesSet();

        //禁止序列化
        RedisSerializer stringSerializer = new StringRedisSerializer();
        template.setKeySerializer(stringSerializer);
        template.setValueSerializer(stringSerializer);
        template.setHashKeySerializer(stringSerializer);
        template.setHashValueSerializer(stringSerializer);

        return template;
    }

//    /**
//     * 哨兵模式
//     * @return
//     */
//    @Primary
//    @Bean(name="investSentinelConfiguration")
//    public RedisSentinelConfiguration sentinelConfiguration(){
//        RedisSentinelConfiguration configuration = new RedisSentinelConfiguration();
//        String[] hosts = nodes.split(",");
//        for (String host : hosts){
//            String[] items = host.split(":");
//            String ip = items[0];
//            Integer port = Integer.parseInt(items[1]);
//            configuration.addSentinel(new RedisNode(ip,port));
//        }
//        configuration.setMaster(masterName);
//        return configuration;
//    }

    @Primary
    @Bean(name="investConnectionFactory")
    public RedisConnectionFactory connectionFactory(){
        JedisConnectionFactory jedis = new JedisConnectionFactory();
        jedis.setHostName(host);
        jedis.setPort(port);
        if (StringUtils.isNotEmpty(password)){
            jedis.setPassword(password);
        }
        if (database != null){
            jedis.setDatabase(database);
        }
        jedis.setTimeout(timeout);
        //初始化连接pool
        RedisConnectionFactory factory = jedis;
        return factory;
    }
}
