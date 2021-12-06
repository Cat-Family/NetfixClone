package com.yill.entity.dto.user.input;

import io.swagger.annotations.ApiParam;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class UserAddInput implements Serializable {



    /**
     * 用户名称
     */
    @ApiParam(value = "用户名称", required = true)
    @NotBlank(message = "用户名称不能为空")
    private String name;

    /**
     * 用户电话
     */
    @ApiParam(value = "用户电话", required = true)
    @NotBlank(message = "用户电话不能为空")
    private String phone;


    /**
     * 密码
     */
    @ApiParam(value = "密码", required = true)
    @NotBlank(message = "密码不能为空")
    private String password;

}
