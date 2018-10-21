package me.pipe.alrescha.mapper;

import me.pipe.alrescha.entity.UserEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

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

}
