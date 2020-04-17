package com.githup.zip.rpchttp.client.service;

import com.githup.zip.rpchttp.client.rpc.RemoteClass;

import java.util.Map;

@RemoteClass("com.githup.zip.rpchttp.server.service.OneService")
public interface OneService {

    String query1(Integer id);

    Integer query2(Map<String, Object> params);
}
