package com.match.user.context.domain.service.impl;

import com.github.javafaker.Faker;
import com.github.middleware.EventStream;
import com.match.common.PageResult;
import com.match.common.exception.BusinessException;
import com.match.user.client.bean.UserInfoDTO;
import com.match.user.client.bean.SettingPasswordDTO;
import com.match.user.client.bean.SimpleUserInfoDTO;
import com.match.user.context.configuration.Constents;
import com.match.user.context.domain.Language;
import com.match.user.context.domain.PeopleSex;
import com.match.user.context.domain.entity.*;
import com.match.user.context.domain.repostory.*;
import com.match.user.context.domain.service.OssObjectServie;
import com.match.user.context.domain.service.UserService;
import com.match.user.context.domain.service.VerificationService;
import com.match.user.event.EventUserCreateDTO;
import com.match.user.event.EventUserModifyDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author zhangchao
 * @Date 2019/4/26 14:08
 * @Version v1.0
 */
@Service
@Slf4j
@Transactional
public class UserServiceImpl implements UserService {

    Faker faker = new Faker(Locale.SIMPLIFIED_CHINESE);
    @Autowired
    private OssObjectServie ossObjectServie;

    @Autowired
    private UserRepository peopleRepository;

    @Autowired
    private LoginMethodRepository loginMethodRepository;

    @Autowired
    private TokenRepository tokenRepository;
//
//    @Autowired
//    private MessageUserRepository messageUserRepository;

    @Autowired
    private UserIntroductionRepository peopleIntroductionRepository;

    @Autowired
    private UserAssistRepository peopleAssistRepository;

    @Autowired
    private VerificationService verificationService;


    @Override
    public Optional<User> findUserById(String peopleId) {
        return peopleRepository.findById(peopleId);
    }

    @Override
    public Optional<String> loginByCode(String phone,String mark) {
        verificationService.check(phone,mark);
        Optional<LoginMethod> loginMethod = loginMethodRepository.findByTypeAndMark(LoginMethod.LoginType.PHONE,phone);
        if (!loginMethod.isPresent()) {
            User user = createUser(phone, faker.name().name());
            loginMethod = user.getLoginMethodList().stream().filter(item ->item.getType() == LoginMethod.LoginType.PHONE).findFirst();
//            throw new BusinessException("手机号不存在");
        }
        return getLoginToken(loginMethod);
    }

    @Override
    public Optional<String> loginByPassword(String phone,String mark) {
        Optional<LoginMethod> loginMethod = loginMethodRepository.findByTypeAndMark(LoginMethod.LoginType.PASSWORD, LoginMethod.buildPasswordMark(phone, mark));
        if (!loginMethod.isPresent()) {
            throw new BusinessException("用户名或者密码错误");
        }
        return getLoginToken(loginMethod);
    }

    @Override
    public void logout(String peopleId) {
        Optional<Token> tokenOptional = tokenRepository.findByPeopleId(peopleId);
        if (tokenOptional.isPresent()) {
            tokenRepository.deleteById(tokenOptional.get().getId());
        }
    }

    private Optional<String> getLoginToken(Optional<LoginMethod> loginMethod) {
        String peopleId = loginMethod.get().getPeopleId();
        Token token = new Token();
        token.setCreateTime(new Date());
        token.setExpire(3000);
        token.setAccessToken(UUID.randomUUID().toString());
        token.setRefreshToken(UUID.randomUUID().toString());
        token.setPeopleId(peopleId);
        tokenRepository.save(token);
        return Optional.ofNullable(token.getAccessToken());
    }

    @Override
    public Optional<User> findUserByPassword(String phone, String password) {
        Optional<LoginMethod> loginMethod = loginMethodRepository.findByTypeAndMark(LoginMethod.LoginType.PASSWORD, LoginMethod.buildPasswordMark(phone, password));
        if (!loginMethod.isPresent()) {
            throw new BusinessException("用户名或者密码错误");
        }
        return peopleRepository.findById(loginMethod.get().getPeopleId());
    }


    @Override
    public PageResult<User> findList(Integer page, Integer size, String searchBy, String keyWord) {
        PageRequest of = PageRequest.of(page - 1, size);
        Specification specification = JpaSpecification.getSpecification(searchBy, keyWord);
        Page<User> all = peopleRepository.findAll(specification, of);

        return new PageResult<User>(all.getTotalElements(), all.getContent());
    }

    @Override
    public User createUser(String phone, String nickName) {

        if (peopleRepository.findByPhone(phone).isPresent()) {
            throw new BusinessException("手机号已存在");
        }

        User people = new User();
        people.setCreateTime(new Date());
        people.setUpdateTime(new Date());
        people.setEncodedPrincipal("");
        people.setNickName(nickName);
        people.setPhone(phone);
        people.setSex(PeopleSex.UNKNOWN);
        people.setLanguage(Language.zh_CN);
        people.setCountry("");
        people.setProvince("");
        people.setCity("");
        people = peopleRepository.saveAndFlush(people);


        List<LoginMethod> loginMethodSet = new ArrayList<>();

        LoginMethod phoneLogin = new LoginMethod();
        phoneLogin.setType(LoginMethod.LoginType.PHONE);
        phoneLogin.setMark(people.getPhone());
        phoneLogin.setPeopleId(people.getId());
        phoneLogin.setStatus(LoginMethod.LoginMethodStatus.ACTIVE);

        loginMethodSet.add(phoneLogin);

        LoginMethod pwdLogin = new LoginMethod();
        pwdLogin.setType(LoginMethod.LoginType.PASSWORD);
        pwdLogin.setMark(LoginMethod.buildPasswordMark(people.getPhone(), Constents.DEFAUTL_PASSWORD));
        pwdLogin.setPeopleId(people.getId());
        pwdLogin.setStatus(LoginMethod.LoginMethodStatus.ACTIVE);
        loginMethodSet.add(pwdLogin);

        loginMethodRepository.save(phoneLogin);
        loginMethodRepository.save(pwdLogin);

        String path = ossObjectServie.generateUserIcon("headimg", nickName);
        people.setEncodedPrincipal(path);
        peopleRepository.saveAndFlush(people);
        people.setLoginMethodList(loginMethodSet);



        // todo 添加用户事件
        //保存消息用户
//        MessageUser messageUser = new MessageUser();
//        messageUser.setPeopleId(people.getId());
//        messageUser.setNickName(people.getNickName());
//        messageUser.setEncodedPrincipal(people.getEncodedPrincipal());
//        messageUserRepository.save(messageUser);

        EventUserCreateDTO eventUserCreateDTO = new EventUserCreateDTO();
        eventUserCreateDTO.setUserId(people.getId());
        eventUserCreateDTO.setUsername(people.getNickName());
        eventUserCreateDTO.setIcon(people.getEncodedPrincipal());
        EventStream.publish(EventUserCreateDTO.EVENT_NAME,eventUserCreateDTO);

        return people;
    }


    @Override
    public UserInfoDTO info(String peopleId) {
        UserInfoDTO peopleInfo = new UserInfoDTO();


        Optional<User> optionalPeople = peopleRepository.findById(peopleId);
        if (!optionalPeople.isPresent()) {
            throw new BusinessException("用户名不存在");
        }
        User people = optionalPeople.get();

        peopleInfo.setId(people.getId());
        peopleInfo.setPeopleNo(people.getPeopleNo());
        peopleInfo.setCreateTime(people.getCreateTime());
        peopleInfo.setEncodedPrincipal(people.getEncodedPrincipal());
        peopleInfo.setNickName(people.getNickName());
        peopleInfo.setPhone(people.getPhone());
        peopleInfo.setSex(people.getSex().name());
        peopleInfo.setLanguage(people.getLanguage().name());
        peopleInfo.setCountry(people.getCountry());
        peopleInfo.setProvince(people.getProvince());
        peopleInfo.setCity(people.getCity());
        peopleInfo.setIntroduction("");

        Optional<UserIntroduction> peopleIntroduction = peopleIntroductionRepository.findByPeopleIdAndStatus(peopleId, 1);
        if(peopleIntroduction.isPresent()){
            peopleInfo.setIntroduction(peopleIntroduction.get().getIntroduction());
        }

        int peopleAssistCount = peopleAssistRepository.countByPeopleId(peopleId);
        peopleInfo.setPeopleAssistCount(peopleAssistCount);

//        MessageUser messageUser = messageUserRepository.findByPeopleId(peopleId);
//        peopleInfo.setMessageUserId(messageUser.getId());
        return peopleInfo;
    }


    @Override
    public void editUserIntroduction(String peopleId, String introduction) {
        if(StringUtils.isEmpty(introduction)){
            return;
        }
        if(peopleId == null){
            throw new BusinessException("用户不存在");
        }

        Optional<UserIntroduction> repositoryByPeopleIdAndStatus = peopleIntroductionRepository.findByPeopleIdAndStatus(peopleId, 1);
        if(repositoryByPeopleIdAndStatus.isPresent()){
            UserIntroduction peopleIntroduction = repositoryByPeopleIdAndStatus.get();
            peopleIntroduction.setStatus(0);
            peopleIntroductionRepository.saveAndFlush(peopleIntroduction);
        }

        UserIntroduction peopleIntroduction = new UserIntroduction();
        peopleIntroduction.setCreateTime(new Date());
        peopleIntroduction.setPeopleId(peopleId);
        peopleIntroduction.setIntroduction(introduction);
        peopleIntroduction.setStatus(1);
        peopleIntroductionRepository.save(peopleIntroduction);
    }

    @Override
    public void assistUser(String peopleId, String assistPeopleId) {
        if (peopleAssistRepository.countByPeopleIdAndInitiativePeopleId(assistPeopleId,peopleId) >0) {
            return;
        }
        UserAssist peopleAssist = new UserAssist();
        peopleAssist.setInitiativePeopleId(peopleId);
        peopleAssist.setPeopleId(assistPeopleId);
        peopleAssistRepository.saveAndFlush(peopleAssist);
    }

    @Override
    public void setPassword(String peopleId, SettingPasswordDTO settingPassword) {
        User people = peopleRepository.findById(peopleId).get();
        verificationService.check(people.getPhone(),settingPassword.getSmsCode());

        Optional<LoginMethod> loginMethodOptional = loginMethodRepository.findByTypeAndPeopleId(LoginMethod.LoginType.PASSWORD, people.getId());

        if(loginMethodOptional.isPresent()){
            LoginMethod loginMethod = loginMethodOptional.get();
            loginMethod.setMark(LoginMethod.buildPasswordMark(people.getPhone(),settingPassword.getPassword()));
            loginMethodRepository.saveAndFlush(loginMethod);
        }else{
            LoginMethod loginMethod = new LoginMethod();
            loginMethod.setType(LoginMethod.LoginType.PASSWORD);
            loginMethod.setMark(LoginMethod.buildPasswordMark(people.getPhone(),settingPassword.getPassword()));
            loginMethod.setPeopleId(people.getId());
            loginMethod.setStatus(LoginMethod.LoginMethodStatus.ACTIVE);
            loginMethodRepository.saveAndFlush(loginMethod);
        }
    }

    @Override
    public void updateUserInfo(String peopleId, UserInfoDTO peopleInfoDto) {
        Optional<User> optionalPeople = peopleRepository.findById(peopleId);
        if (!optionalPeople.isPresent()) {
            throw new BusinessException("用户名不存在");
        }
        User people = optionalPeople.get();

        if(StringUtils.isNotEmpty(peopleInfoDto.getPeopleNo())){
            people.setPeopleNo(peopleInfoDto.getPeopleNo());
        }

        if(StringUtils.isNotEmpty(peopleInfoDto.getEncodedPrincipal())){
            people.setEncodedPrincipal(peopleInfoDto.getEncodedPrincipal());

            // todo 修改用户事件
//            MessageUser messageUser = messageUserRepository.findByPeopleId(people.getId());
//            messageUser.setEncodedPrincipal(people.getEncodedPrincipal());
//            messageUserRepository.saveAndFlush(messageUser);
            EventUserModifyDTO eventUserModifyDTO = new EventUserModifyDTO();
            eventUserModifyDTO.setUserId(people.getId());
            eventUserModifyDTO.setUsername(people.getNickName());
            eventUserModifyDTO.setIcon(people.getEncodedPrincipal());
            EventStream.publish(EventUserModifyDTO.EVENT_NAME,eventUserModifyDTO);
        }

        if(StringUtils.isNotEmpty(peopleInfoDto.getNickName())) {
            people.setNickName(peopleInfoDto.getNickName());
            // todo 修改用户事件
//            MessageUser messageUser = messageUserRepository.findByPeopleId(people.getId());
//            messageUser.setNickName(people.getNickName());
//            messageUserRepository.saveAndFlush(messageUser);
            EventUserModifyDTO eventUserModifyDTO = new EventUserModifyDTO();
            eventUserModifyDTO.setUserId(people.getId());
            eventUserModifyDTO.setUsername(people.getNickName());
            eventUserModifyDTO.setIcon(people.getEncodedPrincipal());
            EventStream.publish(EventUserModifyDTO.EVENT_NAME,eventUserModifyDTO);
        }

        if(StringUtils.isNotEmpty(peopleInfoDto.getPhone())){
            people.setPhone(peopleInfoDto.getPhone());
        }
        if(StringUtils.isNotEmpty(peopleInfoDto.getCity())) {
            people.setCity(peopleInfoDto.getCity());
        }
        if(StringUtils.isNotEmpty(peopleInfoDto.getCountry())) {
            people.setCountry(peopleInfoDto.getCountry());
        }
        if(StringUtils.isNotEmpty(peopleInfoDto.getProvince())) {
            people.setProvince(peopleInfoDto.getProvince());
        }
        if(peopleInfoDto.getLanguage() != null) {
            people.setLanguage(Language.valueOf(peopleInfoDto.getLanguage()));
        }

        if( peopleInfoDto.getSex() != null) {
            people.setSex(PeopleSex.valueOf(peopleInfoDto.getSex()));
        }
        peopleRepository.saveAndFlush(people);
    }


    @Override
    public PageResult<SimpleUserInfoDTO> listSearch(Integer page, Integer size, String peopleId, String word) {
        PageRequest of = PageRequest.of(page - 1, size);
        Specification specification = JpaSpecification.getSpecification(Arrays.asList("peopleNo","nickName","phone"), word);
        Page<User> all = peopleRepository.findAll(specification, of);
        List<SimpleUserInfoDTO> collect = all.getContent().stream().map(item -> {
            SimpleUserInfoDTO simplePeopleInfoDto = new SimpleUserInfoDTO();
            simplePeopleInfoDto.setId(item.getId());
            simplePeopleInfoDto.setPeopleNo(item.getPeopleNo());
            simplePeopleInfoDto.setEncodedPrincipal(item.getEncodedPrincipal());
            simplePeopleInfoDto.setNickName(item.getNickName());
            simplePeopleInfoDto.setLocation(String.format("%s %s %s", item.getCountry(), item.getProvince(), item.getCity()));
            return simplePeopleInfoDto;
        }).collect(Collectors.toList());
        return new PageResult<SimpleUserInfoDTO>(all.getTotalElements(),collect);
    }

    @Override
    public String getUserIdByAccessToken(String token) {
        Optional<Token> optionalToken = tokenRepository.findByAccessToken(token);
        if(!optionalToken.isPresent()){
            return null;
        }
        return optionalToken.get().getPeopleId();
    }
}
