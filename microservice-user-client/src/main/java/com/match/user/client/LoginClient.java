package com.match.user.client;

import com.match.common.ResponseData;
import com.match.user.client.bean.LoginDTO;
import com.match.user.client.configuration.FeignSupportConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

/**
 * @Author zhangchao
 * @Date 2019/8/1 16:58
 * @Version v1.0
 */
@FeignClient(name = "microservice-user",configuration = FeignSupportConfig.class)
public interface LoginClient {

    @PostMapping("/login")
    ResponseData<Object> login(@Valid @RequestBody LoginDTO loginReq);

    @PostMapping("/logout")
    ResponseData<Object> logout();
}
