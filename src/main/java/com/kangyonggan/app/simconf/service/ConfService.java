package com.kangyonggan.app.simconf.service;

import com.kangyonggan.app.simconf.model.Conf;

import java.util.List;

/**
 * @author kangyonggan
 * @since 2017/2/15
 */
public interface ConfService {

    /**
     * 搜索配置
     *
     * @param pageNum
     * @param projCode
     * @param env
     * @param name
     * @return
     */
    List<Conf> searchConfs(int pageNum, String projCode, String env, String name);

    /**
     * 保存配置
     *
     * @param conf
     */
    void saveConf(Conf conf);

    /**
     * 查找配置
     *
     * @param id
     * @return
     */
    Conf findConfById(Long id);

    /**
     * 更新配置
     *
     * @param conf
     */
    void updateConf(Conf conf);

    /**
     * 物理删除
     *
     * @param id
     */
    void deleteConf(Long id);

    /**
     * 查找项目配置
     *
     * @param projCode
     * @param env
     * @return
     */
    List<Conf> findConfsByProjCodeAndEnv(String projCode, String env);
}
