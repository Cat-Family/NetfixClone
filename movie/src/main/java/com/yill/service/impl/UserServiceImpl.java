package com.yill.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yill.entity.dto.user.input.ModifyDto;
import com.yill.entity.User;
import com.yill.entity.dto.user.input.RegisterDto;
import com.yill.mapper.UserMapper;
import com.yill.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yill.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.beans.BeanUtils;


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


    @Override
    public Result register(RegisterDto registerDto) {
        User user = new User();
        user.setName(registerDto.getName());
        user.setPassword(registerDto.getPassword());
        user.setPhone(registerDto.getPhone());
        user.setEmail(registerDto.getEmail());
        userMapper.insert(user);
        return Result.succ("注册成功");
    }

    @Override
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
    }

    @Override
    public void modify(ModifyDto modifyDto) {
        User user = new User();
        BeanUtils.copyProperties(modifyDto,user);
        userMapper.insert(user);
    }
}

