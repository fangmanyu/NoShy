package xin.stxkfzx.noshy.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xin.stxkfzx.noshy.domain.User;
import xin.stxkfzx.noshy.domain.UserInformation;
import xin.stxkfzx.noshy.exception.RegisterException;
import xin.stxkfzx.noshy.mapper.UserInformationMapper;
import xin.stxkfzx.noshy.mapper.UserMapper;
import xin.stxkfzx.noshy.service.RegisterLoginService;

/**
 * @author fmy
 * @date 2018-07-21 22:15
 */
@Service
public class RegisterLoginServiceImpl implements RegisterLoginService {
    private static final Logger log = LogManager.getLogger(RegisterLoginServiceImpl.class);

    private final UserMapper userMapper;
    private final UserInformationMapper userInformationMapper;


    @Override
    public User login(String phone, String password) {

        User user = userMapper.queryUserByPhoneAndPassword(phone, password);
        return user;
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

            log.debug("注册用户信息: {}", user);
            int effectedNum = userMapper.insertSelective(user);

            if (effectedNum <= 0) {
                throw new RegisterException("注册失败, 服务器内部错误");
            }

            // 创建用户信息
            UserInformation information = new UserInformation();
            information.setUserId(user.getUserId());
            information.setExperience(0);
            information.setRank(0);
            userInformationMapper.insert(information);

        } catch (Exception e) {
            throw new RegisterException("RegisterLoginService error: " + e.getMessage());
        }

    }


    @Autowired
    public RegisterLoginServiceImpl(UserMapper userMapper, UserInformationMapper userInformationMapper) {
        this.userMapper = userMapper;
        this.userInformationMapper = userInformationMapper;
    }
}
