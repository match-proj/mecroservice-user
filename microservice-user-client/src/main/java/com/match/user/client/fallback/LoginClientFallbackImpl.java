package com.match.user.client.fallback;

import com.match.common.ResponseData;
import com.match.user.client.LoginClient;
import com.match.user.client.bean.LoginDTO;
import org.springframework.stereotype.Service;

import javax.validation.Valid;

/**
 * @Author zhangchao
 * @Date 2019/8/1 16:59
 * @Version v1.0
 */
@Service
public class LoginClientFallbackImpl implements LoginClient {
    @Override
    public ResponseData<Object> login(@Valid LoginDTO loginReq) {
        return null;
    }

    @Override
    public ResponseData<Object> logout() {
        return null;
    }
}
