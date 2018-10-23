package me.pipe.alrescha.mapper;

import me.pipe.alrescha.entity.UserEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserAccountMapper {

    /**
     * 保存用户帐号信息
     */
    void saveUserAccount(UserEntity userEntity);

    /**
     * 根据用户id, 查询用户信息
     */
    UserEntity queryUserById(@Param("user_id") Long userId);

    /**
     * 根据用户username, 查询用户信息
     */
    UserEntity queryUserByName(@Param("username") String username);

    /**
     * 根据用户id&username, 查询用户信息
     */
    UserEntity queryUser(@Param("user_id") Long userId, @Param("username") String username);

    /**
     * 根据用户username, 查询用户信息(模糊查询)
     */
    List<UserEntity> fuzzyQueryUser(@Param("username") String username);

}
