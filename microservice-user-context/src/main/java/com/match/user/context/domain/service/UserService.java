package com.match.user.context.domain.service;

import com.match.common.PageResult;
import com.match.user.client.bean.UserInfoDTO;
import com.match.user.client.bean.SettingPasswordDTO;
import com.match.user.client.bean.SimpleUserInfoDTO;
import com.match.user.context.domain.entity.User;

import java.util.Optional;

/**
 * @Author zhangchao
 * @Date 2019/4/26 14:08
 * @Version v1.0
 */
public interface UserService {
    Optional<User> findUserById(String userId);

    Optional<String> loginByCode(String phone,String mark);

    Optional<String> loginByPassword(String phone,String mark);

    Optional<User> findUserByPassword(String phone, String password);

    PageResult<User> findList(Integer page, Integer size, String searchBy, String keyWord);

    void createUser(String phone, String nickName);

    void logout(String userId);

    UserInfoDTO info(String userId);

    void editUserIntroduction(String userId, String introduction);

    void assistUser(String userId, String assistUserId);

    void updateUserInfo(String userId, UserInfoDTO UserInfoDto);

    void setPassword(String userId, SettingPasswordDTO settingPassword);

    PageResult<SimpleUserInfoDTO> listSearch(Integer page, Integer size, String UserId, String word);

    String getUserIdByAccessToken(String token);
}
