package com.caller.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.caller.model.RegisterUser;

@Repository
public interface CallManagementDao extends CrudRepository<RegisterUser, Integer>{

	RegisterUser findUserByPhonenumber(String phonenumber);

	List<RegisterUser> findByName(String name);

	@Transactional
    @Modifying
    @Query(value = "UPDATE true_caller.user_info u set spam =:spam where u.phone_number = :phonenumber",
            nativeQuery = true)
	void update(String phonenumber, boolean spam);

}
