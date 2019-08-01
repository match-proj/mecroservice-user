package com.match.user.context.domain.repostory;

import com.match.user.context.domain.entity.UserConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @Author zhangchao
 * @Date 2019/6/6 11:39
 * @Version v1.0
 */
@Repository
public interface UserConfigRepository extends JpaRepository<UserConfig,String> {

    Optional<UserConfig> findFirstByPeopleId(String peopleId);

}
