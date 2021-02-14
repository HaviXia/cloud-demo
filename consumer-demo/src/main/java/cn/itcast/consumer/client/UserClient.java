package cn.itcast.consumer.client;

import cn.itcast.consumer.pojo.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author ：hkxia
 * @description：Feign的客户端,请求方式、请求路径、请求参数、返回结果
 * @date ：2021/2/15 01:20
 */

@FeignClient(value = "user-service", fallback = UserClientFallBack.class)
// 服务名,feign拿着服务名去拉取eureka中，根据Ribbon实现负载均衡，向user发起请求，传入id
public interface UserClient {
    @GetMapping("user/{id}")
    User queryById(@PathVariable("id") Long id);
}
