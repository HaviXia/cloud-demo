package cn.itcast.consumer.web;

import cn.itcast.consumer.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
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
public class ConsumerController {

    @Autowired
    private RestTemplate restTemplatere;

    /*
    * 掉用的另外的一个 tomcat 也就是 user-service 对应的 tomcat
    * */
    @GetMapping("{id}")
    public User queryById(@PathVariable("id") Long id) {
        String url = "http://localhost:8081/user/" + id;
        User user = restTemplatere.getForObject(url, User.class);
        return user;
    }
}
