package org.nrna.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.nrna.dao.Address;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {

	Address getOne(Long id);
	
	@Modifying
	@Query("delete from Address u where u.id = ?1")
	void deleteByUserId(long id);
	
}
