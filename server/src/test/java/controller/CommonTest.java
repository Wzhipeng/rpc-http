package controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.githup.zip.rpchttp.server.rpc.ApiResponse;
import com.githup.zip.rpchttp.server.rpc.ServiceGetter;
import org.junit.Test;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class CommonTest {

    private static ObjectMapper om = new ObjectMapper();
    private  TypeReference mapRef = new TypeReference<Map<String, Object>>() {
    };
    private  TypeReference listRef = new TypeReference<List<String>>() {
    };

    @Test
    public void rpcServiceGetterTest() {
        String className = "service.TestService";
        String methodName = "query1";
        String argsValues = "[1]";
        String argsTypes = "[\"java.lang.Integer\"]";
        try {
            Class tClass = Class.forName(className);
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
            Class[] parameterTypes = new Class[typeClassList.size()];
            parameterTypes = typeClassList.toArray(parameterTypes);
            Method method = tClass.getMethod(methodName, parameterTypes);

            Object obj = ServiceGetter.getServiceByClass(tClass);
            Object[] args = new Object[valueList.size()];
            args = valueList.toArray(args);
            Object result = method.invoke(obj, args);
            assertEquals("query1 return 1", result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
