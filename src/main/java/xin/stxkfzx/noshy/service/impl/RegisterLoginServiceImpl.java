package xin.stxkfzx.noshy.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xin.stxkfzx.noshy.domain.User;
import xin.stxkfzx.noshy.exception.RegisterException;
import xin.stxkfzx.noshy.mapper.UserMapper;
import xin.stxkfzx.noshy.service.RegisterLoginService;

/**
 * @author fmy
 * @date 2018-07-21 22:15
 */
@Service
public class RegisterLoginServiceImpl implements RegisterLoginService {

    private final UserMapper userMapper;


    @Override
    public User login(String phone, String password) {
        return userMapper.queryUserByPhoneAndPassword(phone, password);
    }

    @Override
    @Transactional(rollbackFor = RegisterException.class)
    public void register(User user) throws RegisterException {
        User user1 = userMapper.queryByPhone(user.getUserPhone());

        if (user1 != null) {
            throw new RegisterException("该号码已被注册");
        }

        try {
            // TODO 用户密码加密

            int effectedNum = userMapper.insert(user);

            if (effectedNum <= 0) {
                throw new RegisterException("注册失败, 服务器内部错误");
            }
        } catch (Exception e) {
            throw new RegisterException("RegisterLoginService error: " + e.getMessage());
        }

    }


    @Autowired
    public RegisterLoginServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }
}
