package com.veganet.api.web.rest;

import com.veganet.api.domain.RegleCommission;
import com.veganet.api.repository.RegleCommissionRepository;
import com.veganet.api.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.veganet.api.domain.RegleCommission}.
 */
@RestController
@RequestMapping("/api")
public class RegleCommissionResource {

    private final Logger log = LoggerFactory.getLogger(RegleCommissionResource.class);

    private static final String ENTITY_NAME = "regleCommission";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RegleCommissionRepository regleCommissionRepository;

    public RegleCommissionResource(RegleCommissionRepository regleCommissionRepository) {
        this.regleCommissionRepository = regleCommissionRepository;
    }

    /**
     * {@code POST  /regle-commissions} : Create a new regleCommission.
     *
     * @param regleCommission the regleCommission to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new regleCommission, or with status {@code 400 (Bad Request)} if the regleCommission has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/regle-commissions")
    public ResponseEntity<RegleCommission> createRegleCommission(@RequestBody RegleCommission regleCommission) throws URISyntaxException {
        log.debug("REST request to save RegleCommission : {}", regleCommission);
        if (regleCommission.getId() != null) {
            throw new BadRequestAlertException("A new regleCommission cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RegleCommission result = regleCommissionRepository.save(regleCommission);
        return ResponseEntity.created(new URI("/api/regle-commissions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /regle-commissions} : Updates an existing regleCommission.
     *
     * @param regleCommission the regleCommission to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated regleCommission,
     * or with status {@code 400 (Bad Request)} if the regleCommission is not valid,
     * or with status {@code 500 (Internal Server Error)} if the regleCommission couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/regle-commissions")
    public ResponseEntity<RegleCommission> updateRegleCommission(@RequestBody RegleCommission regleCommission) throws URISyntaxException {
        log.debug("REST request to update RegleCommission : {}", regleCommission);
        if (regleCommission.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        RegleCommission result = regleCommissionRepository.save(regleCommission);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, regleCommission.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /regle-commissions} : get all the regleCommissions.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of regleCommissions in body.
     */
    @GetMapping("/regle-commissions")
    public List<RegleCommission> getAllRegleCommissions() {
        log.debug("REST request to get all RegleCommissions");
        return regleCommissionRepository.findAll();
    }

    /**
     * {@code GET  /regle-commissions/:id} : get the "id" regleCommission.
     *
     * @param id the id of the regleCommission to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the regleCommission, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/regle-commissions/{id}")
    public ResponseEntity<RegleCommission> getRegleCommission(@PathVariable Long id) {
        log.debug("REST request to get RegleCommission : {}", id);
        Optional<RegleCommission> regleCommission = regleCommissionRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(regleCommission);
    }

    /**
     * {@code DELETE  /regle-commissions/:id} : delete the "id" regleCommission.
     *
     * @param id the id of the regleCommission to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/regle-commissions/{id}")
    public ResponseEntity<Void> deleteRegleCommission(@PathVariable Long id) {
        log.debug("REST request to delete RegleCommission : {}", id);
        regleCommissionRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
