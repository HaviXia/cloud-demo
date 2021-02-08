/**
 * @author ：hkxia
 * @description：TODO
 * @date ：2021/2/8 01:04
 */

package cn.itcast;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import tk.mybatis.spring.annotation.MapperScan;

/*
 * @EnableDiscoveryClient基于spring-cloud-commons
 * @EnableEurekaClient基于spring-cloud-netflix
 *
 * EnableEurekaClient 有@EnableDiscoveryClient的功能，
 * 另外上面的注释中提到，其实**@EnableEurekaClient**注解就是一种方便使用eureka的注解而已
 *
 * EnableDiscoveryClient 使用其他的注册中心（Zookeeper、consul、），那么推荐使用@EnableDiscoveryClient
 * */
@EnableEurekaClient
@SpringBootApplication
@MapperScan("cn.itcast.user.mapper")
public class UserApplication {
    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class);
    }
}
