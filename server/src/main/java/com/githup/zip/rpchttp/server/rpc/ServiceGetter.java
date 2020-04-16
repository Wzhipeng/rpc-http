package com.githup.zip.rpchttp.server.rpc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@SuppressWarnings("unchecked")
public class ServiceGetter {
    private static final Logger log = LoggerFactory.getLogger(ServiceGetter.class);

    private static Map<Class, Object> serviceMap = new ConcurrentHashMap<>();

    public static <T> T getServiceByClass(Class<T> tClass) {
        try {
            if (serviceMap.containsKey(tClass)) {
                return (T) serviceMap.get(tClass);
            } else {
                T bean = tClass.newInstance();
                serviceMap.put(tClass, bean);
                return bean;
            }
        } catch (Exception e) {
            log.error("getServiceByClass error ", e);
            return null;
        }
    }

}
