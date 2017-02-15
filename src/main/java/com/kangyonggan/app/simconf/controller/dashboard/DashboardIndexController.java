package com.kangyonggan.app.simconf.controller.dashboard;

import com.kangyonggan.app.simconf.controller.BaseController;
import com.kangyonggan.app.simconf.model.Menu;
import com.kangyonggan.app.simconf.model.ShiroUser;
import com.kangyonggan.app.simconf.model.User;
import com.kangyonggan.app.simconf.service.MenuService;
import com.kangyonggan.app.simconf.service.UserService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * @author kangyonggan
 * @since 2016/12/22
 */
@Controller
@RequestMapping("dashboard")
public class DashboardIndexController extends BaseController {

    @Autowired
    private UserService userService;

    @Autowired
    private MenuService menuService;

    /**
     * 工作台
     *
     * @param model
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    @RequiresPermissions("DASHBOARD")
    public String dashboard(Model model) {
        ShiroUser shiroUser = userService.getShiroUser();
        User user = userService.findUserById(shiroUser.getId());
        List<Menu> menus = menuService.findMenusByUsername(shiroUser.getUsername());

        model.addAttribute("user", user);
        model.addAttribute("menus", menus);
        return "dashboard/dashboard";
    }

    @RequestMapping(value = "index", method = RequestMethod.GET)
    @RequiresPermissions("DASHBOARD")
    public String index() {
        return getPathRoot();
    }

}
