package xin.stxkfzx.noshy.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import xin.stxkfzx.noshy.domain.User;

@Repository
public interface UserMapper {
    int deleteByPrimaryKey(Integer userId);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer userId);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    /**
     * 通过电话号码和密码查询用户
     *
     * @param userPhone
     * @param userPassword
     * @return
     * @author fmy
     * @date 2018-07-21 23:21
     */
    User queryUserByPhoneAndPassword(@Param("userPhone") String userPhone, @Param("userPassword") String userPassword);

    /**
     * 通过电话号码来查询用户
     *
     * @param userPhone
     * @return
     * @author fmy
     * @date 2018-07-21 16:17
     */
    User queryByPhone(String userPhone);
}