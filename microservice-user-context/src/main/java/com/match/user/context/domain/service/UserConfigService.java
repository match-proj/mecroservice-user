package com.match.user.context.domain.service;

import com.match.user.client.bean.UserConfigDTO;

/**
 * @Author zhangchao
 * @Date 2019/6/6 12:40
 * @Version v1.0
 */
public interface UserConfigService {

    UserConfigDTO findFirstByPeopleId(String peopleId);

    void updatePeopleConfig(String peopleId, UserConfigDTO peopleConfig);
}
