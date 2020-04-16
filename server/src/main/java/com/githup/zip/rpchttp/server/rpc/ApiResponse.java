package com.githup.zip.rpchttp.server.rpc;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class ApiResponse<T> {

    public static final ApiResponse<Object> OK = new ApiResponse<>();
    public static final ApiResponse<Object> FAIL = new ApiResponse<>();

    private int status = HttpStatus.OK.value();

    private String message = "ok";

    private long timestamp = System.currentTimeMillis();

    private T data = null;

    public static <T> ApiResponse OK(T data) {
        ApiResponse<T> resp = new ApiResponse<>();
        resp.setData(data);
        return resp;
    }

    public static <T> ApiResponse FAIL(T data) {
        ApiResponse<T> resp = new ApiResponse<>();
        resp.setData(data);
        resp.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        resp.setMessage("failed");
        return resp;
    }

}
