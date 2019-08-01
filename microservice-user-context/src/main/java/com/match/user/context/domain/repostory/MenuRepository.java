package com.match.user.context.domain.repostory;

import com.match.user.context.domain.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author zhangchao
 * @Date 2019/4/25 15:33
 * @Version v1.0
 */
@Repository
public interface MenuRepository extends JpaRepository<Menu,String>, JpaSpecificationExecutor {

    List<Menu> findByParentId(String parentId);

}
