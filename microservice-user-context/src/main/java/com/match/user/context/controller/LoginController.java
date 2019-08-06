package com.match.user.context.controller;

import com.match.common.ResponseData;
import com.match.common.context.User;
import com.match.common.context.UserContext;
import com.match.common.exception.BusinessException;
import com.match.common.utils.ResponseDataUtils;
import com.match.user.client.LoginClient;
import com.match.user.client.bean.LoginDTO;
import com.match.user.client.bean.LoginType;
import com.match.user.context.domain.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Optional;

/**
 * @Author zhangchao
 * @Date 2019/4/26 13:46
 * @Version v1.0
 */
@RestController
@RequestMapping
@Slf4j
public class LoginController implements LoginClient {


    @Autowired
    UserService peopleService;

    @Override
    public ResponseData<Object> login(@Valid @RequestBody LoginDTO loginReq) {
        log.info("login =>method:{} phone:{},mark:{} ", loginReq.getMethod(), loginReq.getPhone(),loginReq.getMark());
        if(loginReq.getMethod() == LoginType.PHONE){
            Optional<String> token = peopleService.loginByCode(loginReq.getPhone(),loginReq.getMark());
            return ResponseDataUtils.buildSuccess().addAttrs("token",token.get());
        }else if(loginReq.getMethod() == LoginType.PASSWORD){
            Optional<String> token = peopleService.loginByPassword(loginReq.getPhone(),loginReq.getMark());
            return ResponseDataUtils.buildSuccess().addAttrs("token",token.get());
        }
        throw new BusinessException("不支持的登录方式");
    }

    @Override
    public ResponseData<Object> logout(@RequestParam("userId") String userId) {
        log.info("logout:{}", userId);
        peopleService.logout(userId);
        return ResponseDataUtils.buildSuccess();
    }

}
