package com.match.user.context.domain.repostory;

import com.match.user.context.domain.entity.DefaultUserConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @Author zhangchao
 * @Date 2019/6/6 11:39
 * @Version v1.0
 */
@Repository
public interface DefaultUserConfigRepository extends JpaRepository<DefaultUserConfig,String> {
}
