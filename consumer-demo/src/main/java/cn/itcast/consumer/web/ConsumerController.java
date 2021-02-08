package cn.itcast.consumer.web;

import cn.itcast.consumer.pojo.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * @author ：hkxia
 * @description：TODO
 * @date ：2021/2/8 01:21
 */

@RestController
@RequestMapping("consumer")
public class ConsumerController {

    @Autowired
    private RestTemplate restTemplatere;

    //动态获取eureka中服务
    @Autowired
    private DiscoveryClient discoveryClient;

    /*
     * 掉用的另外的一个 tomcat 也就是 user-service 对应的 tomcat
     * */
    @GetMapping("{id}")
    public User queryById(@PathVariable("id") Long id) {
        /*
         * eureka中已经注册了user-service，动态获取
         * 1 注入DiscoveryClient，使用discoveryClient获取实例
         * 返回的List 就是 服务的多个实例
         * */

        // 根据服务id获取实例
        List<ServiceInstance> instances = discoveryClient.getInstances("user-service");//参数是服务id
        //从实例中取出ip和端口
        ServiceInstance serviceInstance = instances.get(0);//0号,将来需要负载均衡算法，随机选Instances
        //取出端口
        String url = "http://" + serviceInstance.getHost() + ":" + serviceInstance.getPort() + "/user/" + id;

        User user = restTemplatere.getForObject(url, User.class);
        return user;
    }
}
