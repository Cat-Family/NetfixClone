package com.yill.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 *
 * </p>
 *
 * @author 关注公众号：huluwa
 * @since 2021-12-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_user")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    private Long id;

    /**
     * 用户名称
     */
    private String name;

    /**
     * 用户头像
     */
    private String avatar;

    /**
     * 用户真实姓名
     */
    private String realName;

    /**
     * 用户电话
     */
    private String phone;

    /**
     * 用户出生年月日
     */
    private LocalDateTime birthday;

    /**
     * 密码
     */
    private String password;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 最后登录时间
     */
    private LocalDateTime lastLogin;

    private Boolean isDelete;


}
