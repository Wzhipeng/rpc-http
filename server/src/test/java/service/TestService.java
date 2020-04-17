package service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class TestService {

    private static final Logger log = LoggerFactory.getLogger(TestService.class);

    public String query1(Integer id) {
        return "query1 return " + id;
    }

    public Map<String, Object> query2(HashMap<String, Object> params) {
        log.info("query2: param=" + params);
        return params;
    }
}
