package com.match.user.context.controller;

import com.match.user.client.UserConfigClient;
import com.match.user.client.bean.UserConfigDTO;
import com.match.user.context.domain.service.UserConfigService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author zhangchao
 * @Date 2019/6/6 11:40
 * @Version v1.0
 */
@RestController
@RequestMapping
@Slf4j
public class UserConfigController implements UserConfigClient {

    @Autowired
    UserConfigService peopleConfigService;

    @Override
    public UserConfigDTO getPeopleConfig(@RequestParam("userId") String userId) {
        return peopleConfigService.findFirstByPeopleId(userId);
    }

    @Override
    public void updatePeopleConfig(@RequestParam("userId") String userId,@RequestBody UserConfigDTO peopleConfig) {
        peopleConfigService.updatePeopleConfig(userId, peopleConfig);
    }

}
