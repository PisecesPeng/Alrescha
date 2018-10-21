package me.pipe.alrescha.controller;

import com.google.gson.Gson;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import me.pipe.alrescha.annotation.SysLog;
import me.pipe.alrescha.entity.UserEntity;
import me.pipe.alrescha.form.LoginForm;
import me.pipe.alrescha.form.RegisterForm;
import me.pipe.alrescha.service.SysTokenService;
import me.pipe.alrescha.service.UserAccountService;
import me.pipe.alrescha.util.Constant;
import me.pipe.alrescha.util.R;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/user/account")
@Api(value="user-account-controller", description="user account controller")
public class UserAccountController {

    @Autowired
    UserAccountService userAccountService;
    @Autowired
    SysTokenService sysTokenService;

    @SysLog("根据id查找用户, 返回是否存在此用户")
    @GetMapping("/isExistUser")
    @ApiOperation("is exist user by id")
    public R isExistUserById(@RequestParam(value="id", required=true) @ApiParam("user id") Long userId) {
        Boolean isExist = userAccountService.isExistUserById(userId);
        return R.ok().put("isExistUser", isExist.toString());
    }

    @SysLog("根据id查找用户, 返回该用户信息")
    @GetMapping("/queryUser")
    @ApiOperation("query user by id")
    public R queryUserById(@RequestParam(value="id", required=true) @ApiParam("user id") Long userId) {
        UserEntity userEntity = userAccountService.queryUserById(userId);
        userEntity.setPassword(null);
        userEntity.setSalt(null);
        return R.ok().put("user", new Gson().toJson(userEntity));
    }

    @SysLog("用户注册")
    @PostMapping("/register")
    @ApiOperation("user account register")
    public R register(@RequestBody @ApiParam("register form") RegisterForm form) {

        UserEntity userEntity = new UserEntity();
        userEntity.setUserId(form.getId());
        userEntity.setUsername(form.getUsername());
        userEntity.setPassword(form.getPassword());
        userEntity.setEmail(form.getEmail());

        // 保存用户注册信息
        userAccountService.saveUserAccount(userEntity);

        return R.ok();
    }

    @SysLog("用户登录")
    @PostMapping("/login")
    @ApiOperation("user account login")
    public R login(@RequestBody @ApiParam("login form") LoginForm form) {
        if (userAccountService.isExistUserById(form.getId())) {
            UserEntity user = userAccountService.queryUserById(form.getId());
            // 验证密码
            if(!user.getPassword().equals(new Sha256Hash(form.getPassword(), user.getSalt()).toHex())) {
                return R.error("user login fail");
            }
            // 账号状态是否可用
            if(user.getStatus() == Constant.AccountStatusType.Disable.getValue()){
                return R.error("account is disable, please contact admin");
            }
            return R.ok().put("token", sysTokenService.generateToken(form.getId()));
        }
        return R.error("user account is not exist");
    }

}
