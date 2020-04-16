package com.githup.zip.rpchttp.server.rpc;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.protobuf.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Method;
import java.util.Map;

@RestController("/common")
public class CommonController {

    private static ObjectMapper om = new ObjectMapper();
    private static TypeReference mapRef = new TypeReference<Map<String, Object>>() {
    };

    @RequestMapping("/")
    public ApiResponse rpcServiceGetter(String className, String methodName, String argsValues, String argsTypes) {
        try {
            //先反射出调用的service名
            Class tClass = Class.forName(className);
            if (tClass == null)
                return ApiResponse.FAIL("服务器解析失败，不存在次方法调用");

            //通过argsValues和argsTypes 解析出 方法调用的参数和类型
            JsonNode valueJson = om.readTree(argsValues);
            JsonNode typeJson = om.readTree(argsTypes);


            //再通过反射类获取即将调用的方法
            Method method = tClass.getMethod(methodName, parameterTypes);
            if (method == null) {
                return ApiResponse.FAIL("服务器解析失败，调用方法名不存在");
            }

            Object obj = ServiceGetter.getServiceByClass(tClass);
            if (obj == null) {
                return ApiResponse.FAIL("服务器解析失败，无法生成" + className + "实例");
            }

            //调用次方法，传参，返回结果
            Object result = method.invoke(obj, );
            return ApiResponse.OK(result);
        } catch (Exception e) {
            e.printStackTrace();
            return ApiResponse.FAIL("服务器解析异常，请校验客户端调用是否正确 " + e.getMessage());
        }
    }
}
