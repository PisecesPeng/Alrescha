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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/user/account")
@Api(value="user-account-controller", description="user account controller")
public class UserAccountController extends AbstractController {

    @Autowired
    UserAccountService userAccountService;
    @Autowired
    SysTokenService sysTokenService;

    @SysLog("根据id/username查找用户, 返回是否存在此用户")
    @GetMapping("/isExistUser")
    @ApiOperation("is exist user by id||username")
    public R isExistUser(@RequestParam(value="id", required=false) @ApiParam("user id") Long userId,
                             @RequestParam(value="username", required=false) @ApiParam("user name") String username) {
        Boolean isExist;
        if (userId == null) isExist = userAccountService.isExistUserByName(username);
        else if (username == null) isExist = userAccountService.isExistUserById(userId);
        else isExist = userAccountService.isExistUser(userId, username);
        return R.ok().put("isExistUser", isExist.toString());
    }

    @SysLog("根据id/name查找用户, 返回该用户信息(精确查找)")
    @GetMapping("/queryUser")
    @ApiOperation("query user by id||username")
    public R queryUser(@RequestParam(value="id", required=false) @ApiParam("user id") Long userId,
                           @RequestParam(value="username", required=false) @ApiParam("user name") String username) {
        UserEntity userEntity = new UserEntity();
        Optional<UserEntity> opt;

        if (userId == null) opt = Optional.ofNullable(userAccountService.queryUserByName(username));
        else if (username == null) opt = Optional.ofNullable(userAccountService.queryUserById(userId));
        else opt = Optional.ofNullable(userAccountService.queryUser(userId, username));

        if (opt.isPresent()) {
            userEntity = opt.get();
            userEntity.setPassword(null);
            userEntity.setSalt(null);
        }

        return R.ok().put("user", new Gson().toJson(userEntity));
    }

    @SysLog("根据name查找用户, 返回该用户信息(模糊查找)")
    @GetMapping("/fuzzyQueryUser")
    @ApiOperation("fuzzy query user by username")
    public R fuzzyQueryUser(@RequestParam(value="username") @ApiParam("user name") String username) {
        List<UserEntity> userEntity = new ArrayList<>();

        Optional<List<UserEntity>> opt = Optional.ofNullable(userAccountService.fuzzyQueryUser(username));

        if (opt.isPresent())
            userEntity = opt.get().stream()
                    .peek(u -> u.setPassword(null))
                    .peek(u -> u.setSalt(null))
                    .collect(Collectors.toList());

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
    public R login(@RequestBody @ApiParam("login form") LoginForm form, HttpServletRequest req) {
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
            String token = sysTokenService.generateToken(form.getId());
            req.getSession().setAttribute("token", token);
            // TODO put expire_time.
            return R.ok().put("token", token);
        }
        return R.error("this account is not exist");
    }

    @SysLog("用户登出")
    @PostMapping("/logout")
    @ApiOperation("user account logout")
    public R logout(HttpServletRequest req) {
        // 防止创建session
        HttpSession session = req.getSession(false);
        if(session == null){
            return R.ok();
        }
        String token = session.getAttribute("token").toString();
        session.removeAttribute("token");
        if (sysTokenService.isExistToken(token)) {
            sysTokenService.deleteToken(token);
        }
        return R.ok();
    }

}
