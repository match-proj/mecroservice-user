package com.match.user.client.fallback;

import com.match.common.PageResult;
import com.match.common.exception.BusinessException;
import com.match.user.client.UserClient;
import com.match.user.client.bean.SettingPasswordDTO;
import com.match.user.client.bean.SimpleUserInfoDTO;
import com.match.user.client.bean.UserInfoDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.validation.Valid;

/**
 * @Author zhangchao
 * @Date 2019/8/2 18:30
 * @Version v1.0
 */
@Slf4j
@Component
public class UserClientFallbackImpl implements UserClient {
    @Override
    public UserInfoDTO info(String userId) {
        log.error("info");
        throw new BusinessException("服务不可用");
    }

    @Override
    public void setPassword(@Valid SettingPasswordDTO settingPassword) {
        log.error("setPassword");
        throw new BusinessException("服务不可用");
    }

    @Override
    public void updateUserInfo(UserInfoDTO userInfoDto) {
        log.error("updateUserInfo");
        throw new BusinessException("服务不可用");
    }

    @Override
    public void editUserIntroduction(String introduction) {
        log.error("editUserIntroduction");
        throw new BusinessException("服务不可用");
    }

    @Override
    public void assistUser(String assistUserId) {
        log.error("assistUser");
        throw new BusinessException("服务不可用");
    }

    @Override
    public UserInfoDTO getUser(String userId) {
        log.error("getUser");
        throw new BusinessException("服务不可用");
    }

    @Override
    public PageResult<SimpleUserInfoDTO> listSearch(Integer page, Integer size, String word) {
        log.error("listSearch");
        return new PageResult<>();
    }

    @Override
    public String getUserIdByAccessToken(String token) {
        log.error("listSearch");
        throw new BusinessException("服务不可用");
    }
}
