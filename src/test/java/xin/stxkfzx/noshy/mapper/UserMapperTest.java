package xin.stxkfzx.noshy.mapper;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import xin.stxkfzx.noshy.BaseTest;
import xin.stxkfzx.noshy.domain.User;

import java.util.Date;

import static org.junit.Assert.assertEquals;

/**
 * @author fmy
 * @date 2018-07-21 19:21
 */
public class UserMapperTest extends BaseTest{
    @Autowired
    private UserMapper userMapper1;

    @Test
    public void queryByPhone() {
        User user = userMapper1.queryByPhone("15383644375");
        System.out.println(user);
    }


    @Test
    public void insertUser() {
        User user = new User();
        user.setCreateTime(new Date());
        user.setUserName("aaa");
        user.setUserPassword("aaaaaaaa");
        user.setUserPhone("15383644444");
        int effectedNum = userMapper1.insertSelective(user);
        assertEquals(1, effectedNum);
    }

    @Test
    public void updateUser() {
        User user = new User();
        user.setCreateTime(new Date());
        user.setUserName("aaa");
        user.setUserPassword("aaaaaaaa");
        user.setUserPhone("15383644444");
        user.setUserId(1L);
        int effectedNum = userMapper1.updateByPrimaryKeySelective(user);
        assertEquals(1, effectedNum);
    }

    @Test
    public void testQueryUserByPhoneAndPassword() {
        User user = userMapper1.queryUserByPhoneAndPassword("15383644375", "123456");
        System.out.println(user);
    }
}