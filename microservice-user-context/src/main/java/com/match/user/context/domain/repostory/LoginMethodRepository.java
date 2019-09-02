package com.match.user.context.domain.repostory;

import com.match.user.client.bean.LoginType;
import com.match.user.context.domain.entity.LoginMethod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @Author zhangchao
 * @Date 2019/5/22 9:32
 * @Version v1.0
 */
@Repository
public interface LoginMethodRepository extends JpaRepository<LoginMethod,String> {

    Optional<LoginMethod> findByTypeAndMark(LoginType loginType, String mark);
    Optional<LoginMethod> findByTypeAndPeopleId(LoginType loginType, String peopleId);

}
