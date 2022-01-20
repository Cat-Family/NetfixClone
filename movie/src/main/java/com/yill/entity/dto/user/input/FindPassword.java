package com.yill.entity.dto.user.input;

import io.swagger.annotations.ApiParam;
import lombok.Data;

import java.io.Serializable;

@Data
public class FindPassword implements Serializable {

    @ApiParam(value = "用户名")
    private String userName;

    @ApiParam(value = "密码")
    private String password;

    @ApiParam(value = "确认密码")
    private String confirmPassword;
}
