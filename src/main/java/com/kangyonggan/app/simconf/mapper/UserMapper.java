package com.kangyonggan.app.simconf.mapper;

import com.kangyonggan.app.simconf.model.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserMapper extends MyMapper<User> {

    /**
     * 保存用户角色
     *
     * @param username
     * @param roleCodes
     */
    void insertUserRoles(@Param("username") String username, @Param("roleCodes") List<String> roleCodes);

}