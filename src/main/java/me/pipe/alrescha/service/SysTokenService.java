package me.pipe.alrescha.service;

import me.pipe.alrescha.entity.TokenEntity;

public interface SysTokenService {

    /**
     * 根据用户id, 查询Token
     */
    TokenEntity queryTokenById(Long userId);

    /**
     * 生成Token
     */
    String generateToken(Long userId);

}
