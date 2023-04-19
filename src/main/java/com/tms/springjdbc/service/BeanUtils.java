package com.tms.springjdbc.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class BeanUtils implements ApplicationContextAware {
    private static ApplicationContext context;

    public BeanUtils() {
        // do not call
    }

    public static ApplicationContext getContext() {
        return context;
    }

    public static void setContext(ApplicationContext context) {
        BeanUtils.context = context;
    }

    public void setApplicationContext(ApplicationContext context) {
        setContext(context);
    }

    public static <T> T getBean(Class<T> beanClass) {
        if (getContext() == null) {
            throw new RuntimeException(ApplicationContext.class.getName());
        } else {
            return getContext().getBean(beanClass);
        }
    }

    public static <T> T getBean(Class<T> beanClass, T clazz) {
        if (clazz != null) {
            return clazz;
        } else {
            log.info("getBean by class: [{}]", beanClass.getCanonicalName());
            return getBean(beanClass);
        }
    }

    public static <T> T getBean(String beanClass) {
        if (getContext() == null) {
            throw new RuntimeException(ApplicationContext.class.getName());
        } else {
            return (T) getContext().getBean(beanClass);
        }
    }

    public static <T> T getBean(String beanClass, T clazz) {
        if (clazz != null) {
            return clazz;
        } else {
            log.info("getBean by name: [{}]", beanClass);
            return getBean(beanClass);
        }
    }
}
