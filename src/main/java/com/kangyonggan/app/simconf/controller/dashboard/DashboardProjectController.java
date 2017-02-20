package com.kangyonggan.app.simconf.controller.dashboard;

import com.github.pagehelper.PageInfo;
import com.kangyonggan.app.simconf.constants.Env;
import com.kangyonggan.app.simconf.controller.BaseController;
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
@RequestMapping("dashboard/project")
public class DashboardProjectController extends BaseController {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private ConfService confService;

    /**
     * 项目配置列表
     *
     * @param pageNum
     * @param name
     * @param model
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    @RequiresPermissions("PROJECT")
    public String list(@RequestParam(value = "p", required = false, defaultValue = "1") int pageNum,
                       @RequestParam(value = "name", required = false, defaultValue = "") String name,
                       Model model) {
        List<Project> projects = projectService.searchProjects(pageNum, name);
        PageInfo<Project> page = new PageInfo(projects);

        model.addAttribute("page", page);
        return getPathList();
    }

    /**
     * 添加项目
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "create", method = RequestMethod.GET)
    @RequiresPermissions("PROJECT")
    public String create(Model model) {
        model.addAttribute("project", new Project());
        return getPathFormModal();
    }

    /**
     * 保存项目
     *
     * @param project
     * @param result
     * @return
     */
    @RequestMapping(value = "save", method = RequestMethod.POST)
    @ResponseBody
    @RequiresPermissions("PROJECT")
    public Map<String, Object> save(@ModelAttribute("project") @Valid Project project, BindingResult result) {
        Map<String, Object> resultMap = getResultMap();
        if (!result.hasErrors()) {
            projectService.saveProject(project);
        } else {
            setResultMapFailure(resultMap);
        }

        return resultMap;
    }

    /**
     * 编辑项目
     *
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = "{id:[\\d]+}/edit", method = RequestMethod.GET)
    @RequiresPermissions("PROJECT")
    public String create(@PathVariable("id") Long id, Model model) {
        model.addAttribute("project", projectService.findProjectById(id));
        return getPathFormModal();
    }

    /**
     * 更新项目
     *
     * @param project
     * @param result
     * @return
     */
    @RequestMapping(value = "update", method = RequestMethod.POST)
    @ResponseBody
    @RequiresPermissions("PROJECT")
    public Map<String, Object> update(@ModelAttribute("project") @Valid Project project, BindingResult result) {
        Map<String, Object> resultMap = getResultMap();
        if (!result.hasErrors()) {
            projectService.updateProject(project);
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
     * @param model
     * @return
     */
    @RequestMapping(value = "{id:[\\d]+}/{isDeleted:\\bundelete\\b|\\bdelete\\b}", method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
    @RequiresPermissions("PROJECT")
    public String delete(@PathVariable("id") Long id, @PathVariable("isDeleted") String isDeleted, Model model) {
        Project project = projectService.findProjectById(id);
        project.setIsDeleted((byte) (isDeleted.equals("delete") ? 1 : 0));
        projectService.updateProject(project);

        model.addAttribute("project", project);
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
        projectService.deleteProject(id);

        return getResultMap();
    }

    /**
     * 推送界面
     *
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = "{id:[\\d]+}/push", method = RequestMethod.GET)
    @RequiresPermissions("PROJECT")
    public String push(@PathVariable("id") Long id, Model model) {
        model.addAttribute("project", projectService.findProjectById(id));
        model.addAttribute("envs", Env.values());
        return getPathRoot() + "/push-modal";
    }

    /**
     * 推送配置
     *
     * @param id
     * @param env
     * @param model
     * @return
     */
    @RequestMapping(value = "{id:[\\d]+}/push", method = RequestMethod.POST)
    @RequiresPermissions("PROJECT")
    @ResponseBody
    public Map<String, Object> push(@PathVariable("id") Long id, @RequestParam("env") String env, Model model) {
        return getResultMap();
    }

}
