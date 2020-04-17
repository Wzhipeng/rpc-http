package com.githup.zip.rpchttp.client.rpc;


import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class ApiResponse<T> {

    public static final ApiResponse<Object> OK = new ApiResponse<>();
    public static final ApiResponse<Object> FAIL = new ApiResponse<>();

    private int status = HttpStatus.OK.value();

    private String message = "ok";

    private long timestamp = System.currentTimeMillis();

    private String resultType;

    private T data = null;

    public static <T> ApiResponse OK(T data, String resultType) {
        ApiResponse<T> resp = new ApiResponse<>();
        resp.setData(data);
        resp.setResultType(resultType);
        return resp;
    }

    public static <T> ApiResponse FAIL(T data, String resultType) {
        ApiResponse<T> resp = new ApiResponse<>();
        resp.setData(data);
        resp.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        resp.setResultType(resultType);
        resp.setMessage("failed");
        return resp;
    }

    public boolean isSuccess() {
        return "ok".equals(message);
    }

}
