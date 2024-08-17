package com.yogesh.springjwt.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.yogesh.springjwt.models.UserAddress;

@Repository
public interface AddressRepository extends JpaRepository<UserAddress, Long> {

	UserAddress getOne(Long id);
	
	@Modifying
	@Query("delete from UserAddress u where u.id = ?1")
	void deleteByUserId(long id);
	
}
