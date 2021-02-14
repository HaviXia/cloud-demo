package cn.itcast;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;


/**
 * @author ：hkxia
 * @description：TODO 网关应用
 * @date ：2021/2/15 01:49
 */

@EnableZuulProxy
@SpringBootApplication
public class gatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(gatewayApplication.class);
    }
}
