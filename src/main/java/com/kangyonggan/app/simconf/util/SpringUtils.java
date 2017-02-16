package com.kangyonggan.app.simconf.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * Spring辅助工具类型
 *
 * @author kangyonggan
 * @since 2017/1/3
 */
@Component
public class SpringUtils implements ApplicationContextAware {

    private static SpringUtils instance;
    private ApplicationContext context;

    public SpringUtils() {
        instance = this;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
        context = applicationContext;
    }

    public static void autowire(Object bean) {
        instance.context.getAutowireCapableBeanFactory().autowireBean(bean);
    }

    public static Object getBean(String beanName) {
        return instance.context.getBean(beanName);
    }

    public static <T> T getBean(Class<T> clzz) {
        return instance.context.getBean(clzz);
    }
}
