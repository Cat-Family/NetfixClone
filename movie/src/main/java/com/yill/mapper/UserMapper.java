package com.yill.mapper;

import com.yill.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 关注公众号：huluwa
 * @since 2021-12-06
 */
@Repository
public interface UserMapper extends BaseMapper<User> {

    @Select("SELECT t.* FROM t_user t WHERE email = #{email}")
    public User queryUserByEmail(String email);
}
