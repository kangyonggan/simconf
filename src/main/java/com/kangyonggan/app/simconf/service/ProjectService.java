package com.kangyonggan.app.simconf.service;

import com.kangyonggan.app.simconf.model.Project;

import java.util.List;

/**
 * @author kangyonggan
 * @since 2017/2/15
 */
public interface ProjectService {

    /**
     * 搜索我的项目
     *
     * @param pageNum
     * @param name
     * @return
     */
    List<Project> searchProjects(int pageNum, String name);

    /**
     * 保存项目
     *
     * @param project
     */
    void saveProject(Project project);

    /**
     * 查找项目
     *
     * @param id
     * @return
     */
    Project findProjectById(Long id);

    /**
     * 更新项目
     *
     * @param project
     */
    void updateProject(Project project);

    /**
     * 物理删除
     *
     * @param id
     */
    void deleteProject(Long id);

    /**
     * 校验项目代码是否存在
     *
     * @param code
     * @return
     */
    boolean existsProjectCode(String code);

    /**
     * 查找项目
     *
     * @param projCode
     * @return
     */
    Project findProjectByCode(String projCode);
}
