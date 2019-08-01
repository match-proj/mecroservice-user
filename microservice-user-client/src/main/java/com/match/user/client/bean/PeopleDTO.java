package com.match.user.client.bean;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @Author zhangchao
 * @Date 2019/8/1 15:01
 * @Version v1.0
 */
@Getter
@Setter
public class PeopleDTO {
    private String id;
    private String peopleNo;
    private Date createTime;
    private Date updateTime;
    private String encodedPrincipal;
    private String nickName;
    private String phone;
    private String sex;
    private String province;
    private String city;
    private String country;
    private String street;
}
