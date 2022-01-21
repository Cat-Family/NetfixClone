package com.yill.service;

import com.yill.entity.dto.user.input.FindPassword;
import com.yill.entity.dto.user.input.ModifyDto;
import com.yill.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yill.entity.dto.user.input.RegisterDto;
import com.yill.utils.Result;

import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 关注公众号：huluwa
 * @since 2021-12-06
 */
public interface UserService extends IService<User> {

    Result  register(RegisterDto registerDto,HttpServletResponse response);

    void modify(ModifyDto modifyDto);

    Result loginByEmail(String email, String code, HttpServletResponse response);

    Result findPassword(FindPassword findPassword);
}
