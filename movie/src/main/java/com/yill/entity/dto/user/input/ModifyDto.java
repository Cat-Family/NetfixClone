package com.yill.entity.dto.user.input;

import cn.hutool.core.date.DateTime;
import io.swagger.annotations.ApiParam;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
public class ModifyDto implements Serializable {
    @ApiParam(value = "头像")
    private String avatar;

    @ApiParam(value = "用户名")
    private String name;

    @ApiParam(value = "密码")
    private String password;

    @NotBlank(message = "电话不能为空")
    private String phone;

    @ApiParam(value = "邮箱")
    private String email;

    @ApiParam(value = "出生年月日")
    private DateTime birthday;

    @ApiParam(value = "真实姓名")
    private String realName;
}
