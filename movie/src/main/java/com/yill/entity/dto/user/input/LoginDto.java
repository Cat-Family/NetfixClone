package com.yill.entity.dto.user.input;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
public class LoginDto implements Serializable {

    @NotBlank(message = "昵称不能为空")
    private String name;


    @NotBlank(message = "密码不能为空")
    private String password;
}
