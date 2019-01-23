package xin.stxkfzx.noshy;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import xin.stxkfzx.noshy.domain.School;

import java.util.Date;

import static org.junit.Assert.assertEquals;

/**
 * redis 测试
 *
 * @author fmy
 * @date 2019-01-23 13:40
 */
public class RedisTemplateTest extends BaseTest{
    @Autowired
    private RedisTemplate<String, School> redisTemplate;

    @Test
    public void add() {
        ValueOperations<String, School> value = redisTemplate.opsForValue();


        School bean = new School();
        bean.setSchoolId(123);
        bean.setCreateTime(new Date());
        bean.setSchoolAddr("111111111");
        bean.setSchoolName("测试用例");

        value.set("Java", bean);
        School beanStr = value.get("Java");
        assertEquals(bean, beanStr);

    }
}
