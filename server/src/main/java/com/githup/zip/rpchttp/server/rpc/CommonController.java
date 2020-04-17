package com.githup.zip.rpchttp.server.rpc;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class CommonController {
    private static final Logger log = LoggerFactory.getLogger(CommonController.class);

    private static ObjectMapper om = new ObjectMapper();
    private static TypeReference<Map<String, Object>> mapRef = new TypeReference<Map<String, Object>>() {
    };
    private static TypeReference<List<String>> listRef = new TypeReference<List<String>>() {
    };

    @RequestMapping("/")
    public ApiResponse rpcServiceGetter(@RequestBody Map<String, String> params) {
        try {
            String className = params.get("className");
            String methodName = params.get("methodName");
            String argsValues = params.get("argsValues");
            String argsTypes = params.get("argsTypes");
            log.info("开始远程调用, className: " + className + ", methodName: " + methodName + ", argsValues: " + argsValues + ", argsTypes: " + argsTypes);
            //先反射出调用的service名
            Class tClass = Class.forName(className);
            if (tClass == null)
                return ApiResponse.FAIL("服务器解析失败，不存在次方法调用", String.class.getName());

            //通过argsValues和argsTypes 解析出 方法调用的参数和类型
            List<String> valueJson = om.readValue(argsValues, listRef);
            List<String> typeJson = om.readValue(argsTypes, listRef);

            List<Class> typeClassList = new ArrayList<>();
            List<Object> valueList = new ArrayList<>();
            for (int i = 0; i < typeJson.size(); i++) {
                Class one = Class.forName(typeJson.get(i));
                typeClassList.add(one);
                if (one.equals(String.class)) {
                    valueList.add(valueJson.get(i));
                } else {
                    valueList.add(om.readValue(valueJson.get(i), one));
                }
            }


            //再通过反射类获取即将调用的方法
            Class[] parameterTypes = new Class[typeClassList.size()];
            parameterTypes = typeClassList.toArray(parameterTypes);
            Method method = tClass.getMethod(methodName, parameterTypes);
            if (method == null) {
                return ApiResponse.FAIL("服务器解析失败，调用方法名不存在", String.class.getName());
            }

            Object obj = ServiceGetter.getServiceByClass(tClass);
            if (obj == null) {
                return ApiResponse.FAIL("服务器解析失败，无法生成" + className + "实例", String.class.getName());
            }

            //调用次方法，传参，返回结果
            Object[] args = new Object[valueList.size()];
            args = valueList.toArray(args);
            Object result = method.invoke(obj, args);
            return ApiResponse.OK(om.writeValueAsString(result), result.getClass().getName());
        } catch (Exception e) {
            e.printStackTrace();
            return ApiResponse.FAIL("服务器解析异常，请校验客户端调用是否正确 " + e.getMessage(), String.class.getName());
        }
    }
}
