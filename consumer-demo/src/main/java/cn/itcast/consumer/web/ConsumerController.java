package cn.itcast.consumer.web;


import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.ribbon.RibbonLoadBalancerClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @author ：hkxia
 * @description：TODO
 * @date ：2021/2/8 01:21
 */

@RestController
@RequestMapping("consumer")
@DefaultProperties(defaultFallback = "queryByIdFallBack")
public class ConsumerController {

    @Autowired
    private RestTemplate restTemplatere;

    //动态获取eureka中服务
    @Autowired
    //private DiscoveryClient discoveryClient;
    private RibbonLoadBalancerClient ribbonLoadBalancerClient; //添加 Ribbon 实现负载均衡

    /*
     * 掉用的另外的一个 tomcat 也就是 user-service 对应的 tomcat
     * */
    @GetMapping("{id}")
    // 开启线程隔离、服务容错注解
    //@HystrixCommand(fallbackMethod = "queryByIdFallBack") //成功和失败的方法，两个方法的返回值、参数列表必须一致
    @HystrixCommand(commandProperties = {
            // @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "2000")
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10"), //失败次数
            @HystrixProperty(name = "circuitBreaker.sleepWiondowInMilliseconds", value = "10000"),//休眠时间窗次数
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "60") //错误百分比
    })
    public String queryById(@PathVariable("id") Long id) {
        /*
         * eureka中已经注册了user-service，动态获取
         * 1 注入DiscoveryClient，使用discoveryClient获取实例
         * 返回的List 就是 服务的多个实例
         * */

        // 根据服务id获取实例
        // List<ServiceInstance> instances = discoveryClient.getInstances("user-service");//参数是服务id
        // 从实例中取出ip和端口
        // ServiceInstance serviceInstance = instances.get(0);//0号,将来需要负载均衡算法，随机选Instances
        // 取出端口
        // String url = "http://" + serviceInstance.getHost() + ":" + serviceInstance.getPort() + "/user/" + id;

        /*
         * 使用 Ribbon 做负载均衡
         * 找到启动类,添加 LoadBalanced注解，添加到 restTemplate 身上，loadBalanced内置拦截器拦截 restTemplate请求
         * */
        //ServiceInstance choose = ribbonLoadBalancerClient.choose("user-service"); //得到一个实例，默认轮询

        if (id % 2 == 0) {
            throw new RuntimeException("手动控制熔断");
        }
        String url = "http://user-service/user/" + id;
        String user = restTemplatere.getForObject(url, String.class);
        return user;
    }

    public String queryByIdFallBack(@PathVariable("id") Long id) {
        return "抱歉，服务器拥挤，请稍后再试！";
    }

    public String defaultFallBack() {
        return "抱歉，服务器拥挤，请稍后再试！";
    }
}
