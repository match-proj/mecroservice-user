package com.match.user.client;

import com.match.common.PageResult;
import com.match.common.feign.fallback.HystrixClientFallbackFactory;
import com.match.user.client.bean.SettingPasswordDTO;
import com.match.user.client.bean.SimpleUserInfoDTO;
import com.match.user.client.bean.UserInfoDTO;
import com.match.user.client.configuration.FeignSupportConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @Author zhangchao
 * @Date 2019/7/31 17:28
 * @Version v1.0
 */
@FeignClient(name = "microservice-user",configuration = FeignSupportConfig.class, fallbackFactory = HystrixClientFallbackFactory.class)
public interface UserClient {

    @GetMapping("/user/info")
    public UserInfoDTO info(@RequestParam("userId") String userId);

    @PutMapping("/user/setPassword")
    public void setPassword(@RequestBody @Valid SettingPasswordDTO settingPassword);

    @PutMapping("/user/updateUserInfo")
    public void updateUserInfo(@RequestBody UserInfoDTO userInfoDto);

    @PutMapping("/user/editUserIntroduction")
    public void editUserIntroduction(String introduction);

    @GetMapping("/user/assistUser")
    public void assistUser(String assistUserId);

    @GetMapping("/user/{userId}/info")
    public UserInfoDTO getUser(@PathVariable("userId") String userId);

    @GetMapping("/user/list/search")
    public PageResult<SimpleUserInfoDTO> listSearch(@RequestParam(required = false,name = "page",defaultValue = "1") Integer page,
                                                    @RequestParam(required = false,name = "size",defaultValue = "10") Integer size,
                                                    @RequestParam(required = false,name = "word") String word);
    @GetMapping("/user/getUserIdByAccessToken")
    String getUserIdByAccessToken(@RequestParam("token")String token);
}
