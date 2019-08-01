package com.match.user.context.domain.repostory;

import com.match.user.context.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @Author zhangchao
 * @Date 2019/4/25 15:33
 * @Version v1.0
 */
@Repository
public interface UserRepository extends JpaRepository<User,String>, JpaSpecificationExecutor {

    Optional<User> findByPhone(String phone);
}
