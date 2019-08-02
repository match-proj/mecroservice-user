package com.match.user.client;

import com.match.user.client.bean.UserConfigDTO;
import com.match.user.client.configuration.FeignSupportConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Author zhangchao
 * @Date 2019/8/1 17:18
 * @Version v1.0
 */
@FeignClient(name = "microservice-user",configuration = FeignSupportConfig.class)
public interface UserConfigClient {

    @GetMapping("/people/config")
    UserConfigDTO getPeopleConfig(@RequestParam("userId") String userId);

    @PutMapping("/people/config")
    void updatePeopleConfig(@RequestParam("userId") String userId,@RequestBody UserConfigDTO peopleConfig);
}
