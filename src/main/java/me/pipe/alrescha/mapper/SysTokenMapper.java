package me.pipe.alrescha.mapper;

import me.pipe.alrescha.entity.TokenEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface SysTokenMapper {

    /**
     * 根据用户id, 查询Token
     */
    TokenEntity queryTokenById(@Param("user_id") Long userId);

    /**
     * 更新Token
     * 插入Token
     */
    void updateToken(TokenEntity tokenEntity);
    void insertToken(TokenEntity tokenEntity);

}
