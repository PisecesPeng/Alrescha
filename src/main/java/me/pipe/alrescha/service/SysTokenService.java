package me.pipe.alrescha.service;

import me.pipe.alrescha.entity.TokenEntity;

public interface SysTokenService {

    /**
     * 根据用户id, 查询Token
     */
    TokenEntity queryTokenById(Long userId);

    /**
     * 根据用户id, 查询Token
     */
    TokenEntity queryTokenByToken(String token);

    /**
     * 生成Token
     */
    String generateToken(Long userId);

    /**
     * 是否存在token
     */
    Boolean isExistToken(String token);

    /**
     * 删除token
     */
    void deleteToken(String token);

}
