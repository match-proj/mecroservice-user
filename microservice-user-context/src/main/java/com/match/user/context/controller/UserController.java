package com.match.user.context.controller;

import com.match.common.exception.BusinessException;
import com.match.user.client.UserClient;
import com.match.user.client.bean.PeopleDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

/**
 * @Author zhangchao
 * @Date 2019/7/31 17:37
 * @Version v1.0
 */
@Slf4j
@RestController
@RequestMapping("/user")
public class UserController implements UserClient {


    @Override
    public String findNameByUserId(@RequestParam("userId") String userId) {
        log.info("findNameByUserId({})",userId);
        return "Juck";
    }

    @Override
    public Optional<String> getPeopleIdByAccessToken(@RequestParam("token")String token) {
        return Optional.empty();
    }

    @Override
    public PeopleDTO findPeipleById(String peopleId) {
        throw new BusinessException("主动抛出的异常信息");
//        return new PeopleDTO();
    }
}
