package com.githup.zip.rpchttp.client.service;

import com.githup.zip.rpchttp.client.rpc.RemoteClass;

import java.util.Map;

@RemoteClass("com.githup.zip.rpchttp.server.service.TwoService")
public interface TwoService {
    String test1();

    Map<String, String> test2(Map<String, String> params);
}
