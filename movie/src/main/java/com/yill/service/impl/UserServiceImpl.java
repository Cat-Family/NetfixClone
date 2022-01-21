package com.yill.service.impl;

import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.yill.constant.EmaliConstant;
import com.yill.entity.User;
import com.yill.entity.dto.user.input.FindPassword;
import com.yill.entity.dto.user.input.ModifyDto;
import com.yill.entity.dto.user.input.RegisterDto;
import com.yill.mapper.UserMapper;
import com.yill.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yill.utils.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    @Autowired
    private EmailUtils emailUtils;

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public Result register(RegisterDto registerDto, HttpServletResponse response) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(User::getName, registerDto.getName()).select(User::getName);
        if (!Objects.isNull(userMapper.selectOne(queryWrapper))) {
            return Result.fail("用户名已存在");
        }
        queryWrapper.or().eq(User::getPhone, registerDto.getPhone()).select(User::getPhone);
        if (!Objects.isNull(userMapper.selectOne(queryWrapper))) {
            return Result.fail("该号码已被注册");
        }
        queryWrapper.or().eq(User::getEmail, registerDto.getEmail()).select(User::getEmail);
        if (!Objects.isNull(userMapper.selectOne(queryWrapper))) {
            return Result.fail("该邮箱已被注册");
        }
        User user = new User();
        user.setName(registerDto.getName());
        user.setPassword(registerDto.getPassword());
        user.setPhone(registerDto.getPhone());
        user.setEmail(registerDto.getEmail());
        userMapper.insert(user);
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
    public Result loginByEmail(String email, String validateCode, HttpServletResponse response) {
        if (null == email || validateCode == null) {
            logger.info("邮箱或验证码为空");
            return Result.fail("邮箱或验证码为空");
        } else {
            User user = userMapper.queryUserByEmail(email);
            boolean hasKey = redisUtils.hasKey(email);
            if (null != user) {
                if (!hasKey) {
                    logger.info("验证码已过期，请重新获取");
                    return Result.fail("验证码已过期，请重新获取");
                } else {
                    String realCode = (String) redisUtils.get(email);
                    if (StringUtils.pathEquals(realCode, validateCode)) {
                        String jwt = jwtUtils.generateToken(user.getId());
                        response.setHeader("Authorization", jwt);
                        response.setHeader("Access-Control-Expose-Headers", "Authorization");
                        return Result.succ(MapUtil.builder()
                                .put("email", user.getEmail())
                                .put("token", jwt)
                                .map()
                        );
                    } else {
                        logger.info("验证码错误");
                        return Result.fail("验证码错误");
                    }
                }
            } else {
                if (!hasKey) {
                    logger.info("验证码已过期，请重新获取");
                    return Result.fail("验证码已过期，请重新获取");
                } else {
                    String realCode = (String) redisUtils.get(email);
                    if (StringUtils.pathEquals(realCode, validateCode)) {
                        User users = new User();
                        users.setEmail(email);
                        users.setName(email);
                        userMapper.insert(users);
                        String jwt = jwtUtils.generateTokens(users.getEmail());
                        response.setHeader("Authorization", jwt);
                        response.setHeader("Access-Control-Expose-Headers", "Authorization");
                        return Result.succ(MapUtil.builder()
                                .put("email", users.getEmail())
                                .put("token", jwt)
                                .map()
                        );
                    } else {
                        logger.info("验证码错误");
                        return Result.fail("验证码错误");
                    }
                }
            }
        }
    }

    @Override
    public Result findPassword(FindPassword findPassword) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.and(warpper -> warpper.eq(User::getEmail, findPassword.getUserName()).or()
                .eq(User::getName, findPassword.getUserName()).or()
                .eq(User::getPhone, findPassword.getUserName())).select(User::getPassword, User::getId);
        User users = userMapper.selectOne(queryWrapper);
        if (users != null) {
            if (!findPassword.getPassword().equals(findPassword.getConfirmPassword())) {
                logger.info("密码不一致");
                return Result.fail("密码不一致");
            }
            if (findPassword.getPassword().equals("")) {
                return Result.fail("密码不能为空");
            }
            User user = new User();
            user.setPassword(findPassword.getPassword());
            user.setId(users.getId());
            userMapper.updateById(user);
            logger.info("修改成功");
            return Result.succ("修改成功");
        } else {
            logger.info("用户不存在");
            return Result.fail("用户不存在");
        }
    }

    @Override
    public Result sendEmailForFindPassword(String email) {
        if (null == email) {
            return Result.fail("输入邮箱不能为空");
        } else {
            String code = CommonUtils.randomCode();
            User user = userMapper.queryUserByEmail(email);
            if (null != user) {
                //发送邮件
                Boolean sendEmail = emailUtils.sendEmail(email, EmaliConstant.EMALI_TITLE, "您的修改密码地址为：http://localhost:3000/RecoverPasswordForm?passwordKey=" + code);
                if (sendEmail) {
                    return Result.succ(email);
                } else {
                    return Result.fail("邮件发送失败，请重试");
                }
            } else {
                return Result.fail("邮箱未注册，请重新输入");
            }
        }
    }

}

