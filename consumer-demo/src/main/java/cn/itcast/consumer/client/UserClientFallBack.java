package cn.itcast.consumer.client;

import cn.itcast.consumer.pojo.User;
import org.springframework.stereotype.Component;

/**
 * @author ：hkxia
 * @description：熔断接口实现类
 * @date ：2021/2/15 01:33
 */

@Component // 注入到 SpringBoot
public class UserClientFallBack implements UserClient {
    @Override
    public User queryById(Long id) {
        User user = new User();
        user.setName("未知的用户！");
        return user;
    }
}
