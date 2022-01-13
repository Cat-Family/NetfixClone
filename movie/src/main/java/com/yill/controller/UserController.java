package com.yill.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.yill.common.dto.LoginDto;
import com.yill.entity.User;
import com.yill.mapper.UserMapper;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Objects;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 关注公众号：huluwa
 * @since 2021-12-06
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    RedisUtil redisUtil;

    @Autowired
    UserMapper userMapper;
    @ResponseBody
    @ApiOperation(value = "注册功能", tags = { "登录" })
    @RequestMapping(value = "/login", produces = { "application/json" }, method = RequestMethod.POST)
    @Valid
    public Result login(@RequestBody LoginDto loginDto, HttpServletResponse response) throws JsonProcessingException {
        User user=new User();
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", loginDto.getName());
        User users = userMapper.selectOne(queryWrapper);
        System.out.println(queryWrapper);
        //去数据库拿密码验证用户名密码，这里直接验证
        if(Objects.isNull(users)){
                return new Result(400,"用户名不存在");
        }else{
            queryWrapper.eq("password",loginDto.getPassword());
            if (Objects.isNull(userMapper.selectOne(queryWrapper))){
                return new Result(400, "密码错误");
            }
        }
        Long currentTimeMillis = System.currentTimeMillis();
        String token= TokenUtil.sign(loginDto.getName(),currentTimeMillis);
        redisUtil.set(loginDto.getName(),currentTimeMillis,TokenUtil.REFRESH_EXPIRE_TIME);
        response.setHeader("Authorization", token);
        response.setHeader("Access-Control-Expose-Headers", "Authorization");

        return new Result().OK();
    };
}
