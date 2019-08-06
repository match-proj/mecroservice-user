package com.match.user.event;


import com.github.middleware.annotation.SubscribeType;
import com.github.middleware.message.MessageType;

import java.io.Serializable;

/**
 * @Author zhangchao
 * @Date 2019/8/5 16:14
 * @Version v1.0
 */
@SubscribeType(type = MessageType.P2M)
public class EventUserModifyDTO  implements Serializable {
    public final static String EVENT_NAME = "USER_MODIFY";

    private String userId;
    private String username;
    private String icon;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
