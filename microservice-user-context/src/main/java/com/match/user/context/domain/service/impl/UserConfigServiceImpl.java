package com.match.user.context.domain.service.impl;

import com.match.user.client.bean.UserConfigDTO;
import com.match.user.context.domain.entity.DefaultUserConfig;
import com.match.user.context.domain.entity.UserConfig;
import com.match.user.context.domain.repostory.DefaultUserConfigRepository;
import com.match.user.context.domain.repostory.UserConfigRepository;
import com.match.user.context.domain.service.UserConfigService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @Author zhangchao
 * @Date 2019/6/6 12:40
 * @Version v1.0
 */
@Service
public class UserConfigServiceImpl implements UserConfigService {

    @Autowired
    UserConfigRepository peopleConfigRepository;

    @Autowired
    DefaultUserConfigRepository defaultPeopleConfigRepository;

    @Override
    public UserConfigDTO findFirstByPeopleId(String peopleId) {
        return Optional.ofNullable(peopleConfigRepository.findFirstByPeopleId(peopleId))
                .map(item -> {
                    UserConfig config = new UserConfig();
                    if(item.isPresent()){
                        config = item.get();
                    }else{
                        DefaultUserConfig first = findFirst();
                        BeanUtils.copyProperties(first,config);
                        config.setPeopleId(peopleId);
                        peopleConfigRepository.save(config);
                    }
                    return config;
                }).map(item ->{
                    UserConfigDTO peopleConfigDTO = new UserConfigDTO();
                    peopleConfigDTO.setChatRecordCloudStore(item.getChatRecordCloudStore());
                    peopleConfigDTO.setMessageNotifyVoice(item.getMessageNotifyVoice());
                    peopleConfigDTO.setMessageNotifyShake(item.getMessageNotifyShake());
                    peopleConfigDTO.setUsePhonePlusMe(item.getUsePhonePlusMe());
                    peopleConfigDTO.setUseChatNoPlusMe(item.getUseChatNoPlusMe());
                    peopleConfigDTO.setUseQrCodePlusMe(item.getUseQrCodePlusMe());
                    peopleConfigDTO.setAddMeVerify(item.getAddMeVerify());
                    peopleConfigDTO.setAllowTomeRecommendedGroup(item.getAllowTomeRecommendedGroup());
                    peopleConfigDTO.setDynameicVideoPlayNet(item.getDynameicVideoPlayNet());
                    return peopleConfigDTO;
                }).get();
    }


    @Override
    public void updatePeopleConfig(String peopleId, UserConfigDTO peopleConfig) {

        UserConfig config = peopleConfigRepository.findFirstByPeopleId(peopleId).get();


        if(peopleConfig.getUsePhonePlusMe() != null){
            config.setUsePhonePlusMe(peopleConfig.getUsePhonePlusMe());
        }
        if(peopleConfig.getUseChatNoPlusMe() != null){
            config.setUseChatNoPlusMe(peopleConfig.getUseChatNoPlusMe());
        }
        if(peopleConfig.getUseQrCodePlusMe() != null){
            config.setUseQrCodePlusMe(peopleConfig.getUseQrCodePlusMe());
        }

        if(peopleConfig.getAddMeVerify() != null){
            config.setAddMeVerify(peopleConfig.getAddMeVerify());
        }
        if(peopleConfig.getAllowTomeRecommendedGroup() != null){
            config.setAllowTomeRecommendedGroup(peopleConfig.getAllowTomeRecommendedGroup());
        }
        if(peopleConfig.getChatRecordCloudStore() != null){
            config.setChatRecordCloudStore(peopleConfig.getChatRecordCloudStore());
        }
        if(peopleConfig.getDynameicVideoPlayNet() != null){
            config.setDynameicVideoPlayNet(peopleConfig.getDynameicVideoPlayNet());
        }
        if(peopleConfig.getMessageNotifyShake() != null){
            config.setMessageNotifyShake(peopleConfig.getMessageNotifyVoice());
        }
        if(peopleConfig.getMessageNotifyVoice() != null){
            config.setMessageNotifyVoice(peopleConfig.getMessageNotifyVoice());
        }

        peopleConfigRepository.saveAndFlush(config);

    }



    public DefaultUserConfig findFirst(){
       return Optional.ofNullable(defaultPeopleConfigRepository.findAll().stream().findFirst())
               .map(item ->{
                   DefaultUserConfig defaultPeopleConfig = new DefaultUserConfig();
                    if(item.isPresent()){
                        defaultPeopleConfig = item.get();
                    }else{
                        defaultPeopleConfig = new DefaultUserConfig();
                        defaultPeopleConfig.setChatRecordCloudStore(0);
                        defaultPeopleConfig.setMessageNotifyVoice(0);
                        defaultPeopleConfig.setMessageNotifyShake(0);
                        defaultPeopleConfig.setUseChatNoPlusMe(0);
                        defaultPeopleConfig.setUsePhonePlusMe(0);
                        defaultPeopleConfig.setUseQrCodePlusMe(0);
                        defaultPeopleConfig.setAddMeVerify(0);
                        defaultPeopleConfig.setAllowTomeRecommendedGroup(0);
                        defaultPeopleConfig.setDynameicVideoPlayNet(0);
                        defaultPeopleConfigRepository.save(defaultPeopleConfig);
                    }

                   return defaultPeopleConfig;
               }).get();
    }
}
