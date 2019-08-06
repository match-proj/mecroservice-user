package com.match.user.client.fallback;

import com.match.common.ResponseData;
import com.match.common.utils.ResponseDataUtils;
import com.match.user.client.LoginClient;
import com.match.user.client.bean.LoginDTO;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

/**
 * @Author zhangchao
 * @Date 2019/8/1 16:59
 * @Version v1.0
 */
@Component
public class LoginClientFallbackImpl implements LoginClient {
    @Override
    public ResponseData<Object> login(@Valid LoginDTO loginReq) {
        return ResponseDataUtils.buildError("系统暂时无法使用");
    }

    @Override
    public ResponseData<Object> logout(@RequestParam("userId") String userId) {
        return ResponseDataUtils.buildError("系统暂时无法使用");
    }
}
