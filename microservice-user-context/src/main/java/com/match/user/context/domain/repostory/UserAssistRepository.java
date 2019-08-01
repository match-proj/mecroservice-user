package com.match.user.context.domain.repostory;

import com.match.user.context.domain.entity.UserAssist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @Author zhangchao
 * @Date 2019/4/25 15:33
 * @Version v1.0
 */
@Repository
public interface UserAssistRepository extends JpaRepository<UserAssist,String> {


    int countByPeopleId(String peopleId);


    int countByPeopleIdAndInitiativePeopleId(String peopleId, String initiativePeopleId);

}
