package com.yill.controller;


import com.yill.config.Result;
import com.yill.entity.dto.user.input.UserAddInput;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

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

    @ApiOperation(value = "注册功能", tags = { "登录" })
    @RequestMapping(value = "/add", produces = { "application/json" }, method = RequestMethod.POST)
    @Valid
    public Result add(@RequestBody UserAddInput input) throws Exception {

        return  null;
    }

}
