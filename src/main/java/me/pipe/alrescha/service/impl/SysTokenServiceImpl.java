package me.pipe.alrescha.service.impl;

import me.pipe.alrescha.entity.TokenEntity;
import me.pipe.alrescha.mapper.SysTokenMapper;
import me.pipe.alrescha.service.SysTokenService;
import me.pipe.alrescha.util.TokenGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service("SysTokenService")
public class SysTokenServiceImpl implements SysTokenService {

    @Autowired
    private SysTokenMapper sysTokenMapper;

    @Override
    public TokenEntity queryTokenById(Long userId) {
        return sysTokenMapper.queryTokenById(userId);
    }

    @Override
    public TokenEntity queryTokenByToken(String token) {
        return sysTokenMapper.queryTokenByToken(token);
    }

    @Override
    public String generateToken(Long userId) {
        // 获得token
        String token = TokenGenerator.generateToken();

        // 当前时间
        Date updateTime = new Date();
        // 过期时间
        Date expireTime = new Date(updateTime.getTime() + 3600 * 12 * 1000);

        // 判断该用户是否已有token
        TokenEntity tokenEntity = new TokenEntity();
        Optional<TokenEntity> opt = Optional.ofNullable(this.queryTokenById(userId));
        if (opt.isPresent()) tokenEntity = opt.get();

        tokenEntity.setUpdateTime(updateTime);
        tokenEntity.setExpireTime(expireTime);

        // 存在token则更新token, 否则则插入新的token
        if (tokenEntity.getToken() != null) {
            tokenEntity.setToken(token);
            sysTokenMapper.updateToken(tokenEntity);
        } else {
            tokenEntity.setUserId(userId);
            tokenEntity.setToken(token);
            sysTokenMapper.insertToken(tokenEntity);
        }
        return token;
    }

    @Override
    public Boolean isExistToken(String token) {
        Optional<TokenEntity> opt = Optional.ofNullable(this.queryTokenByToken(token));
        return opt.isPresent();
    }

    @Override
    public void deleteToken(String token) {
        sysTokenMapper.deleteToken(token);
    }

}
