package com.example.search.criteria;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author sagar.kalra
 */

public interface UserDetailRepository extends JpaRepository<UserDetail, Integer>, JpaSpecificationExecutor<UserDetail> {

}
