package com.kangyonggan.app.simconf.controller.dashboard;

import com.kangyonggan.app.simconf.controller.BaseController;
import com.kangyonggan.app.simconf.model.User;
import com.kangyonggan.app.simconf.service.UserService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * @author kangyonggan
 * @since 2017/2/15
 */
@Controller
@RequestMapping("dashboard/password")
public class DashboardPasswordController extends BaseController {

    @Autowired
    private UserService userService;

    @RequestMapping(method = RequestMethod.GET)
    @RequiresPermissions("PASSWORD")
    public String password() {
        return getPathRoot();
    }

    @RequestMapping(method = RequestMethod.POST)
    @RequiresPermissions("PASSWORD")
    @ResponseBody
    public Map<String, Object> password(@RequestParam("password") String password) {
        User user = new User();
        user.setId(userService.getShiroUser().getId());
        user.setPassword(password);

        userService.updateUserPassword(user);
        return getResultMap();
    }

}
