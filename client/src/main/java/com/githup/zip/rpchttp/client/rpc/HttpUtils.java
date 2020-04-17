package com.githup.zip.rpchttp.client.rpc;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.util.HashMap;
import java.util.Map;

public class HttpUtils {

    public static RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(30000)
            .setConnectTimeout(30000).setConnectionRequestTimeout(30000).build();

    private static ObjectMapper om = new ObjectMapper();

    public static String execute(String method, String url, String body) {
        if ("get".equals(method)) {
            return simpleGet(url);
        } else {
            return simpleJsonPost(url, body);
        }
    }

    public static String simpleGet(String url, Map<String, String> header) {
        try {
            CloseableHttpClient httpclient = HttpClients.createDefault();
            HttpGet httpGet = new HttpGet(url);
            httpGet.setConfig(requestConfig);
            for (Map.Entry<String, String> head : header.entrySet()) {
                httpGet.setHeader(head.getKey(), head.getValue());
            }
            CloseableHttpResponse response = httpclient.execute(httpGet);
            if (response.getStatusLine().getStatusCode() == 200) {
                HttpEntity entity = response.getEntity();
                return EntityUtils.toString(entity, "utf-8");
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    public static String simpleGet(String url) {
        try {
            CloseableHttpClient httpclient = HttpClients.createDefault();
            HttpGet httpGet = new HttpGet(url);
            httpGet.setConfig(requestConfig);
            CloseableHttpResponse response = httpclient.execute(httpGet);
            if (response.getStatusLine().getStatusCode() == 200) {
                HttpEntity entity = response.getEntity();
                return EntityUtils.toString(entity, "utf-8");
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String simpleJsonPost(String url, String data) {
        try {
            JsonNode node = om.readTree(data);
            return simpleJsonPost(url, node);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    static public String simpleJsonPost(String url, JsonNode data) {
        try {
            CloseableHttpClient httpclient = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost(url);
            StringEntity postingString = new StringEntity(om.writeValueAsString(data), "UTF-8");
            httpPost.setEntity(postingString);
            httpPost.setHeader("Content-type", "application/json");
            httpPost.setConfig(requestConfig);


            CloseableHttpResponse response = httpclient.execute(httpPost);
            if (response.getStatusLine().getStatusCode() == 200) {
                HttpEntity entity = response.getEntity();
                return EntityUtils.toString(entity, "utf-8");
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static ApiResponse callRemote(String className, String methodName, String argsValues, String argsTypes) {
        try {
            String url = "http://127.0.0.1:25000/";
            Map<String, String> data = new HashMap<>();
            data.put("className", className);
            data.put("methodName", methodName);
            data.put("argsValues", argsValues);
            data.put("argsTypes", argsTypes);
            String result = simpleJsonPost(url, om.writeValueAsString(data));
            return om.readValue(result, ApiResponse.class);
        } catch (Exception e) {
            e.printStackTrace();
            return ApiResponse.FAIL("http调用失败 ", String.class.getName());
        }
    }

    public static void main(String[] args) throws Exception {
        try {
            String className = "com.githup.zip.rpchttp.server.service.OneService";
            String methodName = "query1";
            String argsValues = "[1]";
            String argsTypes = "[\"java.lang.Integer\"]";
            String url = "http://127.0.0.1:25000/";
            Map<String, String> data = new HashMap<>();
            data.put("className", className);
            data.put("methodName", methodName);
            data.put("argsValues", argsValues);
            data.put("argsTypes", argsTypes);
            String result = simpleJsonPost(url, om.writeValueAsString(data));
            System.out.println(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
