package com.match.user.client;

import com.match.common.PageResult;
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
@FeignClient(name = "microservice-user",configuration = FeignSupportConfig.class)
public interface UserClient {

    @GetMapping("/user/hello")
    String hello();

    @GetMapping("/user/info")
    UserInfoDTO info(@RequestParam("userId") String userId);

    @PutMapping("/user/setPassword")
    void setPassword(@RequestParam("userId") String userId,@RequestBody @Valid SettingPasswordDTO settingPassword);

    @PutMapping("/user/updateUserInfo")
    void updateUserInfo(@RequestParam("userId") String userId,@RequestBody UserInfoDTO userInfoDto);

    @PutMapping("/user/editUserIntroduction")
    void editUserIntroduction(@RequestParam("userId") String userId,@RequestParam("introduction") String introduction);

    @GetMapping("/user/assistUser")
    void assistUser(@RequestParam("userId") String userId,@RequestParam("assistUserId") String assistUserId);

    @GetMapping("/user/{userId}/info")
    UserInfoDTO getUser(@PathVariable("userId") String userId);

    @GetMapping("/user/list/search")
    PageResult<SimpleUserInfoDTO> listSearch(@RequestParam("userId") String userId,
                                             @RequestParam(required = false,name = "page",defaultValue = "1") Integer page,
                                             @RequestParam(required = false,name = "size",defaultValue = "10") Integer size,
                                             @RequestParam(required = false,name = "word") String word);

    @GetMapping("/user/getUserIdByAccessToken")
    String getUserIdByAccessToken(@RequestParam("token")String token);
}
