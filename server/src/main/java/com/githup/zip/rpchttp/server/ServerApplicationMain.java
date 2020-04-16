package com.githup.zip.rpchttp.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class ServerApplicationMain {

    private static final Logger log = LoggerFactory.getLogger(ServerApplicationMain.class);

    public static void main(String[] args) {
        log.info("start rpc http server.");
        SpringApplication.run(ServerApplicationMain.class, args);
    }

}
