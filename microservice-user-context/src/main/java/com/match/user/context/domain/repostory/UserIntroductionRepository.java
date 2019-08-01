package com.match.user.context.domain.repostory;

import com.match.user.context.domain.entity.UserIntroduction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @Author zhangchao
 * @Date 2019/4/25 15:33
 * @Version v1.0
 */
@Repository
public interface UserIntroductionRepository extends JpaRepository<UserIntroduction,String> {

    Optional<UserIntroduction> findByPeopleIdAndStatus(String peopleId, Integer status);
}
