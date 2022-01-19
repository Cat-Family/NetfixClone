package com.yill.service.impl;

import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.yill.entity.User;
import com.yill.entity.dto.user.input.ModifyDto;
import com.yill.entity.dto.user.input.RegisterDto;
import com.yill.mapper.UserMapper;
import com.yill.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yill.utils.JwtUtils;
import com.yill.utils.RedisUtils;
import com.yill.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletResponse;
import java.util.Objects;


/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author 关注公众号：huluwa
 * @since 2021-12-06
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {


    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private JwtUtils jwtUtils;


    @Override
    public Result register(RegisterDto registerDto) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(User::getName,registerDto.getName()).select(User::getName);
        if (!Objects.isNull(userMapper.selectOne(queryWrapper))) {
            return Result.fail("用户名已存在");
        }
        queryWrapper.or().eq(User::getPhone,registerDto.getPhone()).select(User::getPhone);
        if (!Objects.isNull(userMapper.selectOne(queryWrapper))){
            return Result.fail("该号码已被注册");
        }
        queryWrapper.or().eq(User::getEmail,registerDto.getEmail()).select(User::getEmail);
        if (!Objects.isNull(userMapper.selectOne(queryWrapper))){
            return Result.fail("该邮箱已被注册");
        }
        User user = new User();
        user.setName(registerDto.getName());
        user.setPassword(registerDto.getPassword());
        user.setPhone(registerDto.getPhone());
        user.setEmail(registerDto.getEmail());
        userMapper.insert(user);
        return Result.succ("注册成功");
    }


/*    @Override
    public Result check(String info) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<User>();
        queryWrapper.and(wrapper -> wrapper.eq("name",info).or()
        .eq("phone",info).or()
        .eq("email",info));
        User user = userMapper.selectOne(queryWrapper);
        if(user!=null){
           return Result.fail("该信息已存在");
        }else {
          return   Result.succ("允许使用");
        }
    }*/

    @Override
    public void modify(ModifyDto modifyDto) {
        User user = new User();
        BeanUtils.copyProperties(modifyDto, user);
        userMapper.insert(user);
    }

    @Override
    public Result loginByEmail(String email, String code,HttpServletResponse response) {
        if (null == email || code == null) {
            return Result.fail("邮箱或验证码出错");
        } else {
            User user = userMapper.queryUserByEmail(email);
            if (null != user) {
                boolean hasKey = redisUtils.hasKey(email);
                if (!hasKey) {
                    return Result.fail("验证码已过期，请重新获取");
                } else {
                    String realCode = (String) redisUtils.get(email);
                    if (StringUtils.pathEquals(realCode,code)) {
                        String jwt = jwtUtils.generateToken(user.getId());
                        response.setHeader("Authorization", jwt);
                        response.setHeader("Access-Control-Expose-Headers", "Authorization");
                        return Result.succ(MapUtil.builder()
                                .put("id", user.getId())
                                .put("username", user.getName())
                                .put("email", user.getEmail())
                                .put("token", jwt)
                                .map()
                        );
                    } else {
                        return Result.fail("验证码错误");
                    }
                }
            } else {
                return Result.fail("邮箱错误，请检查");
            }
        }
    }


}

