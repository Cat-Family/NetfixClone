package com.yill.controller;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yill.common.dto.LoginDto;
import com.yill.entity.User;
import com.yill.service.MailService;
import com.yill.service.UserService;
import com.yill.utils.JwtUtils;
import com.yill.utils.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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
    JwtUtils jwtUtils;
    @Autowired
    UserService userService;
    @Autowired
    private MailService mailService;

    /**
     * 默认账号密码：
     */
    @CrossOrigin
    @ResponseBody
    @ApiOperation(value = "登录功能", tags = {"登录"})
    @RequestMapping(value = "/login", produces = {"application/json"}, method = RequestMethod.POST)
    @Valid
    public Result login( @RequestBody LoginDto loginDto, HttpServletResponse response) {
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
                .map()
        );
    }
    @ApiOperation(value = "郵箱登录功能", tags = {"登录"})
    @RequestMapping(value = "getCheckCode", produces = {"application/json"}, method = RequestMethod.POST)
    @ResponseBody
    public String getCheckCode(String email){
        String checkCode = String.valueOf(new Random().nextInt(899999) + 100000);
        String message = "您的注册验证码为："+checkCode;
        try {
            mailService.sendSimpleMail(email, "注册验证码", message);
        }catch (Exception e){
            return "";
        }
        return checkCode;
    }
}
