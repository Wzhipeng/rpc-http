package com.githup.zip.rpchttp.server.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class TwoService {
    private static final Logger log = LoggerFactory.getLogger(TwoService.class);

    public String test1() {
        return this.getClass().getName() + " test1";
    }

    public Map<String, String> test2(HashMap<String, String> params) {
        log.info(this.getClass().getName() + " test2");
        return params;
    }

}
