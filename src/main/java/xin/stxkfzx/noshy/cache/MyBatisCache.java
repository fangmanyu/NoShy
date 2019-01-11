package xin.stxkfzx.noshy.cache;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.ibatis.cache.Cache;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import xin.stxkfzx.noshy.util.ApplicationContextUtil;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * MyBatis二级缓存
 *
 * @author fmy
 * @date 2018-12-20 18:10
 */
public class MyBatisCache implements Cache {
    private static final Logger log = LogManager.getLogger(MyBatisCache.class);

    private final String id;
    private RedisTemplate redisTemplate;
    private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    private static ObjectMapper mapper = new ObjectMapper();

    public MyBatisCache(String id) {
        if (id == null) {
            throw new IllegalArgumentException("Cache instances require an ID");
        }
        this.id = id;
    }

    @Override
    public String getId() {
        return id;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void putObject(Object o, Object o1) {
        RedisTemplate redisTemplate = getTemplate();
        ValueOperations valueOperations = redisTemplate.opsForValue();
        valueOperations.set(o, o1);
        log.debug("redis存入数据");
    }

    @Override
    public Object getObject(Object o) {
        log.debug("redis获取数据");
        RedisTemplate redisTemplate = getTemplate();
        ValueOperations valueOperations = redisTemplate.opsForValue();
        return valueOperations.get(o);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Object removeObject(Object o) {
        RedisTemplate redisTemplate = getTemplate();
        Boolean flag = redisTemplate.delete(o);
        log.debug("redis删除数据：状态: {}", flag);
        return flag;
    }

    @Override
    public void clear() {
        RedisTemplate redisTemplate = getTemplate();
        redisTemplate.execute((RedisCallback) redisConnection -> {
            redisConnection.flushDb();
            return null;
        });
        log.debug("从redis中清除所有缓存查询");
    }

    @Override
    public int getSize() {
        return 0;
    }

    @Override
    public ReadWriteLock getReadWriteLock() {
        return readWriteLock;
    }

    private RedisTemplate getTemplate() {
        if (redisTemplate == null) {
            redisTemplate = (RedisTemplate) ApplicationContextUtil.getBean("redisTemplate");
        }

        return redisTemplate;
    }

}
