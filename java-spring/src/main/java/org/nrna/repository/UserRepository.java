package org.nrna.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import org.nrna.dao.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	
	Optional<User> findByEmail(String email);

	Boolean existsByEmail(String email);
	
	@Transactional
	@Modifying
	@Query("delete from Address u where u.id = ?1")
	void deleteByUserId(long id);

	@Query(nativeQuery = true, value = "SELECT * FROM users")
	public List<User> findAllUsers();
	
}
