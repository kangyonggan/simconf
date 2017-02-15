package com.kangyonggan.app.simconf.service.impl;

import com.github.pagehelper.PageHelper;
import com.kangyonggan.app.simconf.annotation.LogTime;
import com.kangyonggan.app.simconf.constants.AppConstants;
import com.kangyonggan.app.simconf.mapper.ProjectMapper;
import com.kangyonggan.app.simconf.model.Project;
import com.kangyonggan.app.simconf.service.ProjectService;
import com.kangyonggan.app.simconf.service.UserService;
import com.kangyonggan.app.simconf.util.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @author kangyonggan
 * @since 2017/2/15
 */
@Service
public class ProjectServiceImpl extends BaseService<Project> implements ProjectService {

    @Autowired
    private ProjectMapper projectMapper;

    @Autowired
    private UserService userService;

    @Override
    @LogTime
    public List<Project> searchProjects(int pageNum, String name) {
        Example example = new Example(Project.class);

        Example.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotEmpty(name)) {
            criteria.andLike("name", StringUtil.toLikeString(name));
        }
        criteria.andEqualTo("createUsername", userService.getShiroUser().getUsername());

        example.setOrderByClause("id desc");

        PageHelper.startPage(pageNum, AppConstants.PAGE_SIZE);
        return super.selectByExample(example);
    }

    @Override
    @LogTime
    public void saveProject(Project project) {
        project.setCreateUsername(userService.getShiroUser().getUsername());

        super.insertSelective(project);
    }

    @Override
    @LogTime
    public Project findProjectById(Long id) {
        return super.selectByPrimaryKey(id);
    }

    @Override
    @LogTime
    public void updateProject(Project project) {
        super.updateByPrimaryKeySelective(project);
    }

    @Override
    @LogTime
    public void deleteProject(Long id) {
        super.deleteByPrimaryKey(id);
    }

    @Override
    @LogTime
    public boolean existsProjectCode(String code) {
        Project project = new Project();
        project.setCode(code);

        return projectMapper.selectCount(project) == 1;
    }

    @Override
    @LogTime
    public Project findProjectByCode(String projCode) {
        Project project = new Project();
        project.setCode(projCode);

        return super.selectOne(project);
    }
}
