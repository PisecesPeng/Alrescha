package me.pipe.alrescha.service;

import me.pipe.alrescha.entity.UserEntity;

import java.util.List;

public interface UserAccountService {

    /**
     * 根据用户id, 查询用户是否存在
     */
    Boolean isExistUserById(Long userId);

    /**
     * 根据用户username, 查询用户是否存在
     */
    Boolean isExistUserByName(String username);

    /**
     * 根据用户id/username, 查询用户是否存在
     */
    Boolean isExistUser(Long userId, String username);

    /**
     * 根据用户id，查询用户
     */
    UserEntity queryUserById(Long userId);

    /**
     * 根据用户username，查询用户
     */
    UserEntity queryUserByName(String username);

    /**
     * 根据用户id/username，查询用户
     */
    UserEntity queryUser(Long userId, String username);

    /**
     * 根据用户username，查询用户(模糊查询)
     */
    List<UserEntity> fuzzyQueryUser(String username);

    /**
     * 保存用户帐号信息
     */
    void saveUserAccount(UserEntity userEntity);

}
