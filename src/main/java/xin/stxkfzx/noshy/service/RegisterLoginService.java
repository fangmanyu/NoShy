package xin.stxkfzx.noshy.service;

import xin.stxkfzx.noshy.domain.User;
import xin.stxkfzx.noshy.exception.RegisterException;

/**
 * 注册登录服务
 *
 * @author fmy
 * @date 2018-07-21 22:12
 */
public interface RegisterLoginService {

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
     * @author fmy
     * @date 2018-07-21 22:14
     */
    void register(User user) throws RegisterException;
}
