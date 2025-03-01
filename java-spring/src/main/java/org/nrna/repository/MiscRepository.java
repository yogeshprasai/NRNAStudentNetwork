package org.nrna.repository;

import org.nrna.dao.News;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MiscRepository extends JpaRepository<News, Long> {

}
