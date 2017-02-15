package com.kangyonggan.app.simconf.controller.dashboard;

import com.github.pagehelper.PageInfo;
import com.kangyonggan.app.simconf.constants.Env;
import com.kangyonggan.app.simconf.controller.BaseController;
import com.kangyonggan.app.simconf.model.Conf;
import com.kangyonggan.app.simconf.model.Project;
import com.kangyonggan.app.simconf.service.ConfService;
import com.kangyonggan.app.simconf.service.ProjectService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * @author kangyonggan
 * @since 2017/2/15
 */
@Controller
@RequestMapping("dashboard/conf")
public class DashboardConfController extends BaseController {

    @Autowired
    private ConfService confService;

    @Autowired
    private ProjectService projectService;

    /**
     * 配置列表
     *
     * @param pageNum
     * @param projCode
     * @param env
     * @param name
     * @param model
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    @RequiresPermissions("PROJECT")
    public String list(@RequestParam(value = "p", required = false, defaultValue = "1") int pageNum,
                       @RequestParam(value = "projCode") String projCode,
                       @RequestParam(value = "env", required = false, defaultValue = "common") String env,
                       @RequestParam(value = "name", required = false, defaultValue = "") String name,
                       Model model) {
        Project project = projectService.findProjectByCode(projCode);
        List<Conf> confs = confService.searchConfs(pageNum, projCode, env, name);
        PageInfo<Conf> page = new PageInfo(confs);

        model.addAttribute("project", project);
        model.addAttribute("page", page);
        model.addAttribute("envs", Env.values());
        return getPathList();
    }

    /**
     * 添加配置
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "create", method = RequestMethod.GET)
    @RequiresPermissions("PROJECT")
    public String create(Model model) {
        model.addAttribute("conf", new Conf());
        model.addAttribute("envs", Env.values());
        return getPathFormModal();
    }

    /**
     * 保存配置
     *
     * @param conf
     * @param result
     * @return
     */
    @RequestMapping(value = "save", method = RequestMethod.POST)
    @ResponseBody
    @RequiresPermissions("PROJECT")
    public Map<String, Object> save(@ModelAttribute("conf") @Valid Conf conf, BindingResult result) {
        Map<String, Object> resultMap = getResultMap();
        if (!result.hasErrors()) {
            confService.saveConf(conf);
        } else {
            setResultMapFailure(resultMap);
        }

        return resultMap;
    }

    /**
     * 编辑配置
     *
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = "{id:[\\d]+}/edit", method = RequestMethod.GET)
    @RequiresPermissions("PROJECT")
    public String create(@PathVariable("id") Long id, Model model) {
        model.addAttribute("conf", confService.findConfById(id));
        model.addAttribute("envs", Env.values());
        return getPathFormModal();
    }

    /**
     * 更新配置
     *
     * @param conf
     * @param result
     * @return
     */
    @RequestMapping(value = "update", method = RequestMethod.POST)
    @ResponseBody
    @RequiresPermissions("PROJECT")
    public Map<String, Object> update(@ModelAttribute("conf") @Valid Conf conf, BindingResult result) {
        Map<String, Object> resultMap = getResultMap();
        if (!result.hasErrors()) {
            confService.updateConf(conf);
        } else {
            setResultMapFailure(resultMap);
        }

        return resultMap;
    }

    /**
     * 删除/恢复
     *
     * @param id
     * @param isDeleted
     * @param projCode
     * @param model
     * @return
     */
    @RequestMapping(value = "{id:[\\d]+}/{isDeleted:\\bundelete\\b|\\bdelete\\b}", method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
    @RequiresPermissions("PROJECT")
    public String delete(@PathVariable("id") Long id, @PathVariable("isDeleted") String isDeleted,
                         @RequestParam("projCode") String projCode, Model model) {
        Project project = projectService.findProjectByCode(projCode);
        Conf conf = confService.findConfById(id);
        conf.setIsDeleted((byte) (isDeleted.equals("delete") ? 1 : 0));
        confService.updateConf(conf);

        model.addAttribute("project", project);
        model.addAttribute("conf", conf);
        model.addAttribute("envs", Env.values());
        return getPathTableTr();
    }

    /**
     * 物理删除
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "{id:[\\d]+}/remove", method = RequestMethod.GET)
    @RequiresPermissions("PROJECT")
    @ResponseBody
    public Map<String, Object> remove(@PathVariable("id") Long id) {
        confService.deleteConf(id);

        return getResultMap();
    }


}
