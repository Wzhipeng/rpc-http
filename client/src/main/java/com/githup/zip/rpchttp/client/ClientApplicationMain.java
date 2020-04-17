package com.githup.zip.rpchttp.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class ClientApplicationMain {
    private static final Logger log = LoggerFactory.getLogger(ClientApplicationMain.class);

    public static void main(String[] args) {
        SpringApplication.run(ClientApplicationMain.class, args);
    }

}
