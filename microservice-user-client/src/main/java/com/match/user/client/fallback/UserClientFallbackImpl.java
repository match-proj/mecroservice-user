package com.match.user.client.fallback;

import com.match.user.client.UserClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
        log.info("findNameByUserId({})",userId);
        return null;
    }
}
