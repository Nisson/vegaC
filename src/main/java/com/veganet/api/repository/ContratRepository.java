package com.veganet.api.repository;

import com.veganet.api.domain.Contrat;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Contrat entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ContratRepository extends JpaRepository<Contrat, Long> {
    @Query("select c from Contrat c where c.convention.provider.id= :idp")
    List<Contrat> findByProvider(@Param("idp") Long id);
    @Query("select c from Contrat c where c.datedeb between :debut and :fin and c.convention.provider.id= :idp")
    List<Contrat> findByDateDebDateFinByProvider(@Param("debut") LocalDate dateDeb, @Param("fin") LocalDate dateFin , @Param("idp") Long id );
    @Query("select c from Contrat c where c.datedeb between :debut and :fin ")
    List<Contrat> findByDateDebDateFin(@Param("debut") LocalDate dateDeb, @Param("fin") LocalDate dateFin);
}
