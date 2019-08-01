package com.match.user.client.fallback;

import com.match.user.client.UserClient;
import com.match.user.client.bean.PeopleDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @Author zhangchao
 * @Date 2019/7/31 17:31
 * @Version v1.0
 */
@Slf4j
@Service
public class UserClientFallbackImpl implements UserClient {

    @Override
    public String findNameByUserId(String userId) {
        log.error("findNameByUserId({})",userId);
        return null;
    }

    @Override
    public Optional<String> getPeopleIdByAccessToken(String token) {
        log.error("getPeopleIdByAccessToken({})",token);
        return Optional.empty();
    }

    @Override
    public PeopleDTO findPeipleById(String peopleId) {
        log.error("findPeipleById({})",peopleId);
        return null;
    }
}
