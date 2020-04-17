package controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.githup.zip.rpchttp.server.rpc.ApiResponse;
import com.githup.zip.rpchttp.server.rpc.ServiceGetter;
import org.junit.Test;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class CommonTest {

    private static ObjectMapper om = new ObjectMapper();
    private TypeReference mapRef = new TypeReference<Map<String, Object>>() {
    };
    private TypeReference listRef = new TypeReference<List<Object>>() {
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


    @Test
    public void test2() {
        String className = "service.TestService";
        String methodName = "query2";
        String argsValues = "[\"{\\\"haha\\\":\\\"heihei\\\"}\"]";
        String argsTypes = "[\"java.util.HashMap\"]";
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
                    valueList.add(om.readValue(String.valueOf(valueJson.get(i)), one));
                }
            }
            Class[] parameterTypes = new Class[typeClassList.size()];
            parameterTypes = typeClassList.toArray(parameterTypes);
            Method method = tClass.getMethod(methodName, parameterTypes);

            Object obj = ServiceGetter.getServiceByClass(tClass);
            Object[] args = new Object[valueList.size()];
            args = valueList.toArray(args);
            Object result = method.invoke(obj, args);
            Map<String, String> params = new HashMap<>();
            params.put("haha", "heihei");
            assertEquals(params, result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test3() throws Exception {
        Map<String, String> params = new HashMap<>();
        params.put("haha", "hehe");
        Object[] objs = new Object[]{params};
        for(int i = 0 ; i < objs.length; i++){
            objs[i] = om.writeValueAsString(objs[i]);
        }
        for (Object obj : objs) {
            obj = om.writeValueAsString(obj);
        }
        String j = om.writeValueAsString(objs);
        List<String> list = om.readValue(j, listRef);
        String c = params.getClass().getName();
        System.out.println(om.readValue(list.get(0), Class.forName(c)));
        assertEquals("1", "1");
    }
}
