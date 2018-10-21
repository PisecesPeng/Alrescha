package me.pipe.alrescha.service;

import me.pipe.alrescha.entity.UserEntity;

public interface UserAccountService {

    /**
     * 根据用户id, 查询用户是否存在
     */
    Boolean isExistUserById(Long userId);

    /**
     * 根据用户id，查询用户
     */
    UserEntity queryUserById(Long userId);

    /**
     * 保存用户帐号信息
     */
    void saveUserAccount(UserEntity userEntity);

}
