package com.githup.zip.rpchttp.server.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class OneService {

    private static final Logger log = LoggerFactory.getLogger(OneService.class);

    public String query1(Integer id) {
        return "query1 return " + id;
    }

    public int query2(HashMap<String, Object> params) {
        log.info("query2: param=" + params);
        return 1;
    }
}
