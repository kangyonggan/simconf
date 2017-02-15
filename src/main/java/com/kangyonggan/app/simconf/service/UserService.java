package com.kangyonggan.app.simconf.service;

import com.kangyonggan.app.simconf.model.ShiroUser;
import com.kangyonggan.app.simconf.model.User;

import java.util.List;

/**
 * @author kangyonggan
 * @since 2017/1/19
 */
public interface UserService {

    /**
     * 根据用户名查找用户
     *
     * @param username
     * @return
     */
    User findUserByUsername(String username);

    /**
     * 获取当前登录的用户
     *
     * @return
     */
    ShiroUser getShiroUser();

    /**
     * 根据ID查找用户，不会查出密码
     *
     * @param id
     * @return
     */
    User findUserById(Long id);

    /**
     * 校验用户名是否存在
     *
     * @param username
     * @return
     */
    boolean existsUsername(String username);

    /**
     * 保存用户
     *
     * @param user
     */
    void saveUserWithDefaultRole(User user);

    /**
     * 更新用户的密码
     *
     * @param user
     */
    void updateUserPassword(User user);

    /**
     * 搜索用户
     *
     * @param pageNum
     * @param fullname
     * @return
     */
    List<User> searchUsers(int pageNum, String fullname);

    /**
     * 更新用户
     *
     * @param user
     */
    void updateUser(User user);

    /**
     * 修改用户角色
     *
     * @param username
     * @param roleCodes
     */
    void updateUserRoles(String username, String roleCodes);

}
