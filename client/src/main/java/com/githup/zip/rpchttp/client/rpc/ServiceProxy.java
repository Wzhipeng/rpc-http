package com.githup.zip.rpchttp.client.rpc;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class ServiceProxy<T> implements InvocationHandler {

    private T target;

    private static ObjectMapper om = new ObjectMapper();

    public ServiceProxy(T target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Exception {
        try {
            RemoteClass remoteClass = method.getDeclaringClass().getAnnotation(RemoteClass.class);
            if (remoteClass == null) {
                throw new Exception("远程调用出错");
            }

            List<String> argsTypes = new ArrayList<>();
            if (args != null) {
                for(int i = 0 ; i < args.length; i++){
                    argsTypes.add(args[i].getClass().getName());
                    args[i] = om.writeValueAsString(args[i]);
                }
            }
            ApiResponse result = HttpUtils.callRemote(remoteClass.value(), method.getName(), om.writeValueAsString(args), om.writeValueAsString(argsTypes));
            System.out.println(result.toString());
            if (result.isSuccess()) {
                return om.readValue(result.getData().toString(), Class.forName(result.getResultType()));
            } else {
                throw new Exception("远程调用返回结果失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("远程调用失败 " + e.getMessage());
        }
    }
}
