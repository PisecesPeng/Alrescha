package me.pipe.alrescha.service.impl;

import me.pipe.alrescha.entity.UserEntity;
import me.pipe.alrescha.exception.ASException;
import me.pipe.alrescha.mapper.SysTokenMapper;
import me.pipe.alrescha.mapper.UserAccountMapper;
import me.pipe.alrescha.service.UserAccountService;
import me.pipe.alrescha.util.Constant;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service("UserAccountService")
public class UserAccountServiceImpl implements UserAccountService {

    @Autowired
    private UserAccountMapper userAccountMapper;

    @Override
    public Boolean isExistUserById(Long userId) {
        Boolean bool = Boolean.FALSE;
        UserEntity userEntity = userAccountMapper.queryUserById(userId);
        try {
            if (userEntity.getUsername() != null) bool = Boolean.TRUE;
        } finally {
            return bool;
        }
    }

    @Override
    public UserEntity queryUserById(Long userId) {
        return userAccountMapper.queryUserById(userId);
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
