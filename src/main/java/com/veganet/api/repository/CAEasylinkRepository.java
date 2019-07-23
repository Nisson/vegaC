package com.veganet.api.repository;

import com.veganet.api.domain.CAEasylink;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the CAEasylink entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CAEasylinkRepository extends JpaRepository<CAEasylink, Long> {

}
