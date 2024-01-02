package com.wl2o2o.smartoj;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 主类（项目启动入口）
 *
 * @author <a href="https://github.com/wl2o2o">程序员CSGUIDER</a>
 * @from <a href="https://wl2o2o.github.io">CSGUIDER博客</a>
 */
// TODO 如需开启 Redis，须移除 exclude 中的内容
// @SpringBootApplication(exclude = {RedisAutoConfiguration.class})
@SpringBootApplication
@MapperScan("com.wl2o2o.smartoj.mapper")
@EnableScheduling
@EnableAspectJAutoProxy(proxyTargetClass = true, exposeProxy = true)
public class MainApplication {

    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class, args);
    }

}
