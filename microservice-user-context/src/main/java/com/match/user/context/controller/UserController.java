package com.match.user.context.controller;

import com.match.common.PageResult;
import com.match.common.context.UserContext;
import com.match.common.exception.BusinessException;
import com.match.user.client.UserClient;
import com.match.user.client.bean.UserInfoDTO;
import com.match.user.client.bean.SettingPasswordDTO;
import com.match.user.client.bean.SimpleUserInfoDTO;
import com.match.user.context.domain.entity.User;
import com.match.user.context.domain.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

/**
 * @Author zhangchao
 * @Date 2019/5/27 17:11
 * @Version v1.0
 */
@RestController
@RequestMapping
@Slf4j
public class UserController implements UserClient {

    @Autowired
    UserService userService;

    @Override
    public String hello() {
        return "UserController.hello()";
    }

    @Override
    public UserInfoDTO info(@RequestParam("userId") String userId){
        return  userService.info(userId);
    }

    @Override
    public void setPassword(@RequestParam("userId") String userId,@RequestBody @Valid SettingPasswordDTO settingPassword){
        userService.setPassword(userId,settingPassword);
    }

    @Override
    public void updateUserInfo(@RequestParam("userId") String userId,@RequestBody UserInfoDTO UserInfoDto){
        userService.updateUserInfo(userId,UserInfoDto);
    }

    @Override
    public void editUserIntroduction(@RequestParam("userId") String userId,@RequestParam("introduction") String introduction){
        userService.editUserIntroduction(userId,introduction);
    }

    @Override
    public void assistUser(@RequestParam("userId") String userId,@RequestParam("assistUserId") String assistUserId){
        userService.assistUser(userId,assistUserId);
    }

    @Override
    public UserInfoDTO getUser(@PathVariable("userId") String userId){
        Optional<User> User = userService.findUserById(userId);
        if(User.isPresent()){
            UserInfoDTO dto = new UserInfoDTO();
            BeanUtils.copyProperties(User.get(),dto);
            return dto;
        }
        throw new BusinessException("用户不存在");
    }

    @Override
    public PageResult<SimpleUserInfoDTO> listSearch(@RequestParam("userId") String userId,
                                                    @RequestParam(required = false,name = "page",defaultValue = "1") Integer page,
                                                    @RequestParam(required = false,name = "size",defaultValue = "10") Integer size,
                                                    @RequestParam(required = false,name = "word") String word) {
        return userService.listSearch(page, size, userId,word);
    }

    @Override
    public String getUserIdByAccessToken(@RequestParam("token")String token) {
        return userService.getUserIdByAccessToken(token);
    }
}
