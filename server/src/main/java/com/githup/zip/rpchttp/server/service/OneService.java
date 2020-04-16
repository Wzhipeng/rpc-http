package com.githup.zip.rpchttp.server.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class OneService {

    private static final Logger log = LoggerFactory.getLogger(OneService.class);

    public String query1(int id) {
        return "query1 return " + id;
    }

    public int query2(Map<String, Object> params) {
        log.info("query2: param=" + params);
        return 1;
    }
}
