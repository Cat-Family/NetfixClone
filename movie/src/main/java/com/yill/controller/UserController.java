package com.yill.controller;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yill.entity.dto.user.input.LoginDto;
import com.yill.entity.dto.user.input.ModifyDto;
import com.yill.entity.User;
import com.yill.entity.dto.user.input.RegisterDto;
import com.yill.mapper.UserMapper;
import com.yill.service.MailService;
import com.yill.service.UserService;
import com.yill.utils.JwtUtils;
import com.yill.utils.RedisUtils;
import com.yill.utils.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Random;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author 关注公众号：huluwa
 * @since 2021-12-06
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    UserService userService;

    @Autowired
    private MailService mailService;

    @Autowired
    UserMapper userMapper;

    /**
     * 默认账号密码：
     */
    @CrossOrigin
    @ResponseBody
    @ApiOperation(value = "登录功能", tags = {"用户"})
    @RequestMapping(value = "/login", produces = {"application/json"}, method = RequestMethod.POST)
    @Valid
    public Result login(@RequestBody LoginDto loginDto, HttpServletResponse response) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<User>();
        queryWrapper.and(wrapper -> wrapper
                .eq("name", loginDto.getName()).or()
                .eq("phone", loginDto.getName()).or()
                .eq("email", loginDto.getName()));
        User user = userService.getOne(queryWrapper);
        Assert.notNull(user, "用户不存在");
        if (!user.getPassword().equals(loginDto.getPassword())) {
            return Result.fail("密码错误！");
        }
        String jwt = jwtUtils.generateToken(user.getId());
        response.setHeader("Authorization", jwt);
        response.setHeader("Access-Control-Expose-Headers", "Authorization");
        // 用户可以另一个接口
        return Result.succ(MapUtil.builder()
                .put("id", user.getId())
                .put("username", user.getName())
                .put("email", user.getEmail())
                .put("token", jwt)
                .map()
        );
    }

    @ApiOperation(value = "获取验证码", tags = {"用户"})
    @RequestMapping(value = "getCheckCode", produces = {"application/json"}, method = RequestMethod.POST)
    @ResponseBody
    public String getCheckCode(String email) {
        String checkCode = String.valueOf(new Random().nextInt(899999) + 100000);
        redisUtils.setWithTime(email, checkCode, 60);
        String message = "欢迎使用无花果影音，您的注册验证码为：" + checkCode;
        try {
            mailService.sendSimpleMail(email, "注册验证码", message);
            redisUtils.set("code", checkCode);
        } catch (Exception e) {
            return "";
        }
        return checkCode;
    }

    @ApiOperation(value = "邮箱登录", tags = {"用户"})
    @RequestMapping(value = "email-login", produces = {"application/json"}, method = RequestMethod.POST)
    @ResponseBody
    public Result emailLogin(String validateCode) {
        if (validateCode == null) {
            return Result.fail("错误获取");
        }
        //redis中的验证码
        Object checkCode = redisUtils.get("code");
        //校验
        System.out.println(checkCode);
        if (validateCode != null && validateCode.equals(checkCode)) {
            return Result.succ("验证码正确");
        }
        return Result.fail("验证码错误");
    }


    @ApiOperation(value = "注册功能", tags = {"用户"})
    @RequestMapping(value = "register", produces = {"application/json"}, method = RequestMethod.POST)
    @ResponseBody
    public Result region(@RequestBody RegisterDto registerDto) {
        return userService.register(registerDto);
    }

    @ApiOperation(value = "修改完善功能", tags = {"用户"})
    @RequestMapping(value = "update", produces = {"application/json"}, method = RequestMethod.POST)
    @ResponseBody
    public void modify(ModifyDto modifyDto) {
        userService.modify(modifyDto);
    }


}
