package la.niub.abcapi.invest.indicatordatamigration.component.util;
//package la.niub.abcapi.invest.seller.component.util;
//
//import com.alibaba.fastjson.JSON;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.dao.DataAccessException;
//import org.springframework.data.redis.connection.RedisConnection;
//import org.springframework.data.redis.core.HashOperations;
//import org.springframework.data.redis.core.RedisCallback;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.data.redis.serializer.RedisSerializer;
//import org.springframework.stereotype.Component;
//
//import java.util.List;
//import java.util.Map;
//import java.util.concurrent.TimeUnit;
//import java.util.function.Predicate;
//
//@Component
//public class RedisUtil {
//
//    static Logger logger = LogManager.getLogger(RedisUtil.class);
//
//    @Autowired
//    private RedisTemplate<String, ?> redisTemplate;
//
///**
//     * 序列化value值
//     *
//     * @param value
//     * @return
//     *//*
//
//    private static <T> String value2String(T value) {
//        String v = null;
//        if (value == null) {
//            return null;
//        }
//        try {
//            if (value instanceof String) {
//                v = (String) value;
//            } else {
//                v = JSON.toJSONString(value);
//            }
//        } catch (Exception e) {
//            logger.error("value2String error with value=" + value);
//        }
//        return v;
//    }
//
//    */
///**
//     * 反序列化value值
//     *
//     * @param value
//     * @param clazz
//     * @return
//     *//*
//
//    @SuppressWarnings("unchecked")
//    private static <T> T string2Value(String value, Class<T> clazz) {
//        if (value == null) {
//            return null;
//        }
//        try {
//            if (clazz.getName().equalsIgnoreCase("java.lang.String")) {
//                return (T) value;
//            }
//            return (T) JSON.parseObject(value, clazz);
//        } catch (Exception e) {
//            logger.error("string2Value error with value=" + value + ", clazz=" + clazz.getName());
//        }
//        return null;
//    }
//
//    public <T> T get(final String key, Class<T> clazz) {
//        logger.info("redis getting key: "+key);
//        String result = redisTemplate.execute(new RedisCallback<String>() {
//            @Override
//            public String doInRedis(RedisConnection connection) throws DataAccessException {
//                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
//                byte[] value = connection.get(serializer.serialize(key));
//                return serializer.deserialize(value);
//            }
//        });
//        return string2Value(result, clazz);
//    }
//
//    public String get(final String key) {
//        logger.info("redis getting key: "+key);
//        String result = redisTemplate.execute(new RedisCallback<String>() {
//            @Override
//            public String doInRedis(RedisConnection connection) throws DataAccessException {
//                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
//                byte[] value = connection.get(serializer.serialize(key));
//                return serializer.deserialize(value);
//            }
//        });
//        return result;
//    }
//
//    public <T> boolean set(String key, T value, long expire, Predicate<T> predicate) {
//        boolean result = predicate.test(value);
//        if (!result){
//            return false;
//        }
//        return set(key, value, expire);
//    }
//
//    public <T> boolean set(String key, T value, long expire) {
//        if (value == null){
//            return false;
//        }
//        boolean result = set(key, value);
//        if (!result){
//            return false;
//        }
//        return expire(key, expire);
//    }
//
//    public <T> boolean set(String key, T value) {
//        logger.info("redis setting key: "+key);
//        String v = value2String(value);
//        boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {
//            @Override
//            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
//                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
//                connection.set(serializer.serialize(key), serializer.serialize(v));
//                return true;
//            }
//        });
//        return result;
//    }
//
//    public boolean set(String key, String value) {
//        logger.info("redis setting key: "+key);
//        boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {
//            @Override
//            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
//                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
//                connection.set(serializer.serialize(key), serializer.serialize(value));
//                return true;
//            }
//        });
//        return result;
//    }
//
//    public boolean expire(final String key, long expire) {
//        return redisTemplate.expire(key, expire, TimeUnit.SECONDS);
//    }
//
//    public <T> boolean setList(String key, List<T> list) {
//        String value = JSON.toJSONString(list);
//        return set(key, value);
//    }
//
//    public <T> List<T> getList(String key, Class<T> clz) {
//        String json = get(key);
//        if (json != null) {
//            List<T> list = JSON.parseArray(json, clz);
//            return list;
//        }
//        return null;
//    }
//
//    public <T> T hget(String key, String field, Class<T> clz) {
//        logger.info("redis getting hash key: {} field: {}",key,field);
//        HashOperations<String, String, T> hash = redisTemplate.opsForHash();
//        return hash.get(key,field);
//    }
//
//    public <T> Map<String, T> hgetall(String key, Class<T> clz) {
//        logger.info("redis getting hash key: {} all key-value",key);
//        HashOperations<String, String, T> hash = redisTemplate.opsForHash();
//        return hash.entries(key);
//    }
//
//    public Boolean hexists(String key, String field){
//        HashOperations<String, String, Object> hash = redisTemplate.opsForHash();
//        return hash.hasKey(key,field);
//    }
//
//    public <T> void hset(String key, String field, T value){
//        logger.info("redis setting hash key: {} field: {}",key,field);
//        HashOperations<String, String, T> hash = redisTemplate.opsForHash();
//        hash.put(key,field,value);
//    }
//
//    public long lpush(final String key, Object obj) {
//        final String value = JSON.toJSONString(obj);
//        long result = redisTemplate.execute(new RedisCallback<Long>() {
//            @Override
//            public Long doInRedis(RedisConnection connection) throws DataAccessException {
//                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
//                long count = connection.lPush(serializer.serialize(key), serializer.serialize(value));
//                return count;
//            }
//        });
//        return result;
//    }
//
//    public long rpush(final String key, Object obj) {
//        final String value = JSON.toJSONString(obj);
//        long result = redisTemplate.execute(new RedisCallback<Long>() {
//            @Override
//            public Long doInRedis(RedisConnection connection) throws DataAccessException {
//                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
//                long count = connection.rPush(serializer.serialize(key), serializer.serialize(value));
//                return count;
//            }
//        });
//        return result;
//    }
//
//    public String lpop(final String key) {
//        String result = redisTemplate.execute(new RedisCallback<String>() {
//            @Override
//            public String doInRedis(RedisConnection connection) throws DataAccessException {
//                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
//                byte[] res = connection.lPop(serializer.serialize(key));
//                return serializer.deserialize(res);
//            }
//        });
//        return result;
//    }
//
//}
//*/
