package com.yill.common.dto;

import io.swagger.annotations.ApiParam;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
public class RegionDto implements Serializable {
    @NotBlank(message = "昵称不能为空")
    private String name;

    @NotBlank(message = "密码不能为空")
    private String password;

    @NotBlank(message = "电话不能为空")
    private String phone;

    @ApiParam(value = "邮箱")
    private String email;
}
