package com.veganet.api.repository;

import com.veganet.api.domain.Amicale;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Amicale entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AmicaleRepository extends JpaRepository<Amicale, Long> {

}
