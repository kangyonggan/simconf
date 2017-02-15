package com.kangyonggan.app.simconf.service.impl;

import com.github.pagehelper.PageHelper;
import com.kangyonggan.app.simconf.annotation.LogTime;
import com.kangyonggan.app.simconf.constants.AppConstants;
import com.kangyonggan.app.simconf.constants.Env;
import com.kangyonggan.app.simconf.mapper.ConfMapper;
import com.kangyonggan.app.simconf.model.Conf;
import com.kangyonggan.app.simconf.service.ConfService;
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
public class ConfServiceImpl extends BaseService<Conf> implements ConfService {

    @Autowired
    private ConfMapper confMapper;

    @Autowired
    private UserService userService;

    @Override
    @LogTime
    public List<Conf> searchConfs(int pageNum, String projCode, String env, String name) {
        Example example = new Example(Conf.class);
        Example.Criteria criteria = example.createCriteria();

        criteria.andEqualTo("projCode", projCode);
        if (StringUtils.isNotEmpty(env)) {
            criteria.andEqualTo("env", env);
        }
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
    public void saveConf(Conf conf) {
        conf.setCreateUsername(userService.getShiroUser().getUsername());

        super.insertSelective(conf);
    }

    @Override
    @LogTime
    public Conf findConfById(Long id) {
        return super.selectByPrimaryKey(id);
    }

    @Override
    @LogTime
    public void updateConf(Conf conf) {
        super.updateByPrimaryKeySelective(conf);
    }

    @Override
    @LogTime
    public void deleteConf(Long id) {
        super.deleteByPrimaryKey(id);
    }

    @Override
    @LogTime
    public List<Conf> findConfsByProjCodeAndEnv(String projCode, String env) {
        Example commonExample = new Example(Conf.class);
        commonExample.selectProperties("name", "value");
        commonExample.createCriteria().andEqualTo("env", Env.COMMON.getEnv())
                .andEqualTo("projCode", projCode).andEqualTo("isDeleted", AppConstants.IS_DELETED_NO);

        // 通用配置
        List<Conf> commonConfs = super.selectByExample(commonExample);

        Example envExample = new Example(Conf.class);
        envExample.selectProperties("name", "value");
        envExample.createCriteria().andEqualTo("env", env)
                .andEqualTo("projCode", projCode).andEqualTo("isDeleted", AppConstants.IS_DELETED_NO);

        // env环境的配置
        List<Conf> envConfs = super.selectByExample(envExample);

        // env覆盖配置common配置
        addAll(commonConfs, envConfs);

        return commonConfs;
    }

    /**
     * 合并2个集合
     *
     * @param commonConfs
     * @param envConfs
     */
    private void addAll(List<Conf> commonConfs, List<Conf> envConfs) {
        for (Conf conf : envConfs) {
            for (int i = 0; i < commonConfs.size(); i++) {
                if (conf.getName().equals(commonConfs.get(i).getName())) {
                    commonConfs.remove(i);
                    break;
                }
            }
            commonConfs.add(conf);
        }
    }
}
