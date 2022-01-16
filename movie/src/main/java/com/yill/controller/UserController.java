package com.yill.controller;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yill.entity.dto.user.input.LoginDto;
import com.yill.entity.dto.user.input.ModifyDto;
import com.yill.entity.dto.user.input.RegionDto;
import com.yill.entity.User;
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
                .put("token",jwt)
                .map()
        );
    }
    @ApiOperation(value = "郵箱登录功能", tags = {"用户"})
    @RequestMapping(value = "getCheckCode", produces = {"application/json"}, method = RequestMethod.POST)
    @ResponseBody
    public String getCheckCode(String email,String code){
        String checkCode = String.valueOf(new Random().nextInt(899999) + 100000);
        redisUtils.setWithTime(email,checkCode,60);
        String message = "欢迎使用无花果影音，您的注册验证码为："+checkCode;
        try {
            mailService.sendSimpleMail(email, "注册验证码", message);
            User user = new User();
            user.setEmail(email);
            user.setName(email);
            if (!code.equals(checkCode)){
                Result.fail("验证码错误");
            }
            userMapper.insert(user);
        }catch (Exception e){
            return "";
        }
        return checkCode;
    }



    @ApiOperation(value = "注册功能", tags = {"用户"})
    @RequestMapping(value = "region", produces = {"application/json"}, method = RequestMethod.POST)
    @ResponseBody
    public void region(RegionDto regionDto){
        userService.region(regionDto);
    }



    @ApiOperation(value = "校验功能", tags = {"用户"})
    @RequestMapping(value = "check", produces = {"application/json"}, method = RequestMethod.POST)
    @ResponseBody
    public Result check(String info){
       return userService.check(info);
    }

    @ApiOperation(value = "修改完善功能", tags = {"用户"})
    @RequestMapping(value = "update", produces = {"application/json"}, method = RequestMethod.POST)
    @ResponseBody
    public void modify(ModifyDto modifyDto){
        userService.modify(modifyDto);
    }


}
