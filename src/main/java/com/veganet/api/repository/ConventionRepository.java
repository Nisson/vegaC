package com.veganet.api.repository;

import com.veganet.api.domain.Convention;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Convention entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ConventionRepository extends JpaRepository<Convention, Long> {

}
