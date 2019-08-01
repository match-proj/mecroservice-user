package com.match.user.context.controller;

import com.match.common.context.UserContext;
import com.match.user.client.UserConfigClient;
import com.match.user.client.bean.UserConfigDTO;
import com.match.user.context.domain.service.UserConfigService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public UserConfigDTO getPeopleConfig() {
        String peopleId = UserContext.getUser().getId();
        return peopleConfigService.findFirstByPeopleId(peopleId);
    }

    @Override
    public void updatePeopleConfig(@RequestBody UserConfigDTO peopleConfig) {
        String peopleId = UserContext.getUser().getId();
        peopleConfigService.updatePeopleConfig(peopleId, peopleConfig);
    }

}
