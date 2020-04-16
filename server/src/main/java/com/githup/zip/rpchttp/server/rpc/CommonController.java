package com.githup.zip.rpchttp.server.rpc;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.protobuf.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController("/common")
public class CommonController {

    private static ObjectMapper om = new ObjectMapper();
    private static TypeReference mapRef = new TypeReference<Map<String, Object>>() {
    };
    private static TypeReference listRef = new TypeReference<List<String>>() {
    };

    @RequestMapping("/")
    public ApiResponse rpcServiceGetter(String className, String methodName, String argsValues, String argsTypes) {
        try {
            //先反射出调用的service名
            Class tClass = Class.forName(className);
            if (tClass == null)
                return ApiResponse.FAIL("服务器解析失败，不存在次方法调用");

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
                return ApiResponse.FAIL("服务器解析失败，调用方法名不存在");
            }

            Object obj = ServiceGetter.getServiceByClass(tClass);
            if (obj == null) {
                return ApiResponse.FAIL("服务器解析失败，无法生成" + className + "实例");
            }

            //调用次方法，传参，返回结果
            Object[] args = new Object[valueList.size()];
            args = valueList.toArray(args);
            Object result = method.invoke(obj, args);
            return ApiResponse.OK(result);
        } catch (Exception e) {
            e.printStackTrace();
            return ApiResponse.FAIL("服务器解析异常，请校验客户端调用是否正确 " + e.getMessage());
        }
    }
}
