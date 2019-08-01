package com.match.user.client;

import com.match.user.client.fallback.UserClientFallbackImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Author zhangchao
 * @Date 2019/7/31 17:28
 * @Version v1.0
 */
@FeignClient(name = "microservice-user", fallback = UserClientFallbackImpl.class)
@RequestMapping("/user")
public interface UserClient {

    @GetMapping("/findNameByUserId")
    String findNameByUserId(@RequestParam("userId") String userId);
}
