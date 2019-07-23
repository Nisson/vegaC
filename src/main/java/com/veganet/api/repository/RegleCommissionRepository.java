package com.veganet.api.repository;

import com.veganet.api.domain.RegleCommission;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the RegleCommission entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RegleCommissionRepository extends JpaRepository<RegleCommission, Long> {

}
