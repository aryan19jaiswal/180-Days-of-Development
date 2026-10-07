package com._daysOfBatman.SpringBootPractice.Config;

import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

@Component
public class TimingBeanPostProcessor implements BeanPostProcessor {

    private static final String OWN_PACKAGE = "com._daysOfBatman";

    private final ConcurrentHashMap<String, Long> startTimes = new ConcurrentHashMap<>();

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) {
        if (isOwnBean(bean)) {
            startTimes.put(beanName, System.nanoTime());
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) {
        Long start = startTimes.remove(beanName);
        if (start != null) {
            long micros = TimeUnit.NANOSECONDS.toMicros(System.nanoTime() - start);
            System.out.printf("[TIMING] %s (%s) init took %.2f ms%n",
                    beanName, bean.getClass().getSimpleName(), micros / 1000.0);
        }
        return bean;
    }

    private boolean isOwnBean(Object bean) {
        return bean.getClass().getName().startsWith(OWN_PACKAGE);
    }
}
