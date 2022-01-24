package com.yill.entity.dto.user.input;

import io.swagger.annotations.ApiParam;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
public class RegisterDto implements Serializable {

    @NotBlank(message = "昵称不能为空")
    private String name;

    @NotBlank(message = "密码不能为空")
    private String password;

    @ApiParam(value = "电话")
    private String phone;

    @ApiParam(value = "邮箱")
    private String email;
}
