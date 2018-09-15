package xin.stxkfzx.noshy.service;

import xin.stxkfzx.noshy.domain.User;
import xin.stxkfzx.noshy.exception.RegisterException;

/**
 * 用户服务
 *
 * @author fmy
 * @date 2018-07-21 22:12
 */
public interface UserService {

    /**
     * 登录
     *
     * @param phone
     * @param password
     * @return
     * @author fmy
     * @date 2018-07-21 22:14
     */
    User login(String phone, String password);

    /**
     * 注册
     *
     * @param user
     * @return
     * @throws RegisterException
     * @author fmy
     * @date 2018-07-21 22:14
     */
    void register(User user) throws RegisterException;

    /**
     *
     * 获取指定用户
     *
     * @param userId
     * @return
     * @author fmy
     * @date 2018-09-15 15:58
     */
    User getUser(Long userId);
}
