package me.pipe.alrescha.service.impl;

import me.pipe.alrescha.entity.UserEntity;
import me.pipe.alrescha.exception.ASException;
import me.pipe.alrescha.mapper.UserAccountMapper;
import me.pipe.alrescha.service.UserAccountService;
import me.pipe.alrescha.util.Constant;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service("UserAccountService")
public class UserAccountServiceImpl implements UserAccountService {

    @Autowired
    private UserAccountMapper userAccountMapper;

    @Override
    public Boolean isExistUserById(Long userId) {
        Optional<UserEntity> opt = Optional.ofNullable(userAccountMapper.queryUserById(userId));
        return opt.isPresent();
    }

    @Override
    public Boolean isExistUserByName(String username) {
        Optional<UserEntity> opt = Optional.ofNullable(userAccountMapper.queryUserByName(username));
        return opt.isPresent();
    }

    @Override
    public Boolean isExistUser(Long userId, String username) {
        Optional<UserEntity> opt = Optional.ofNullable(userAccountMapper.queryUser(userId, username));
        return opt.isPresent();
    }

    @Override
    public UserEntity queryUserById(Long userId) {
        return userAccountMapper.queryUserById(userId);
    }

    @Override
    public UserEntity queryUserByName(String username) {
        return userAccountMapper.queryUserByName(username);
    }

    @Override
    public UserEntity queryUser(Long userId, String username) {
        return userAccountMapper.queryUser(userId, username);
    }

    @Override
    public List<UserEntity> fuzzyQueryUser(String username) {
        return userAccountMapper.fuzzyQueryUser(username);
    }

    @Override
    public void saveUserAccount(UserEntity userEntity) {

        userEntity.setCreateTime(new Date());
		userEntity.setStatus(Constant.AccountStatusType.NORMAL.getValue());
		// sha256加密
		String salt = RandomStringUtils.randomAlphanumeric(20);
		userEntity.setPassword(new Sha256Hash(userEntity.getPassword(), salt).toHex());
		userEntity.setSalt(salt);

        try {
            userAccountMapper.saveUserAccount(userEntity);
        } catch (Exception e) {
            throw new ASException("save user account error", e);
        }

    }

}
