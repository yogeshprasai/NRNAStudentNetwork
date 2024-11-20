package org.nrna.repository;

import org.nrna.models.dto.PasswordResetToken;
import org.nrna.models.dto.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PasswordResetRepository extends CrudRepository<PasswordResetToken, Long> {
    @Query(nativeQuery = true, value = "SELECT * FROM tokens where user_id = ?1")
    public List<PasswordResetToken> findByUserId(Long userId);
}
