package com.match.user.client.bean;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @Author zhangchao
 * @Date 2019/5/27 17:14
 * @Version v1.0
 */
@Getter
@Setter
public class UserInfoDTO {

    private String id;
    private String peopleNo;
    private Date createTime;
    private String encodedPrincipal;
    private String nickName;
    private String phone;
    private String sex;
    private String language ;
    private String country;
    private String province;
    private String city;
    private String introduction;
    private Integer peopleAssistCount;
//    private String messageUserId;

}
