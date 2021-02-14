package cn.itcast;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * @author ：hkxia
 * @description：TODO
 * @date ：2021/2/8 01:17
 */

//@EnableCircuitBreaker 服务熔断
//@EnableEurekaClient
//@SpringBootApplication
/*
 * 上面的三个注解,被包含在了 SpringCloudApplication注解中
 * */
@SpringCloudApplication
public class ConsumerApplication {
    //注册一个restTemplate
    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    public static void main(String[] args) {
        SpringApplication.run(ConsumerApplication.class);
    }
}
