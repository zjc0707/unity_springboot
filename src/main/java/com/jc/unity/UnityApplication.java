package com.jc.unity;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.jc.unity.mapper")
public class UnityApplication {

    public static void main(String[] args) {
        SpringApplication.run(UnityApplication.class, args);
    }

}
