package org.nrna.repository;

import org.nrna.models.dto.News;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MiscRepository extends JpaRepository<News, Long> {

}
