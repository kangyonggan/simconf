package com.kangyonggan.app.simconf.freemarker;

import com.kangyonggan.app.simconf.shiro.SuperTag;
import com.kangyonggan.app.simconf.util.PropertiesUtil;
import freemarker.core.Environment;
import freemarker.template.ObjectWrapper;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

/**
 * @author kangyonggan
 * @since 2016/12/2
 */
@Component
public class AppsDirective extends SuperTag {

    @Override
    public void render(Environment env, Map params, TemplateDirectiveBody body) throws IOException, TemplateException {
        env.setVariable("appName", ObjectWrapper.DEFAULT_WRAPPER.wrap(PropertiesUtil.getProperties("app.name")));
        env.setVariable("appAuthor", ObjectWrapper.DEFAULT_WRAPPER.wrap(PropertiesUtil.getProperties("app.author")));
        env.setVariable("appBaNo", ObjectWrapper.DEFAULT_WRAPPER.wrap(PropertiesUtil.getProperties("app.ba.no")));
        renderBody(env, body);
    }
}
