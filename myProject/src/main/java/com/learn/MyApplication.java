package com.learn;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
@MapperScan("com.learn.*.mapper")

public class MyApplication {
    public static void main(String[] args) {
        SpringApplication.run(MyApplication.class,args);
        log.info("启动成功");
    }
}
