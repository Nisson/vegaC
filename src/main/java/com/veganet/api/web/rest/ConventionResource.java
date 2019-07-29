package com.veganet.api.web.rest;

import com.veganet.api.domain.Convention;
import com.veganet.api.repository.ConventionRepository;
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
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * REST controller for managing {@link com.veganet.api.domain.Convention}.
 */
@RestController
@RequestMapping("/api")
public class ConventionResource {

    private final Logger log = LoggerFactory.getLogger(ConventionResource.class);

    private static final String ENTITY_NAME = "convention";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ConventionRepository conventionRepository;

    public ConventionResource(ConventionRepository conventionRepository) {
        this.conventionRepository = conventionRepository;
    }

    /**
     * {@code POST  /conventions} : Create a new convention.
     *
     * @param convention the convention to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new convention, or with status {@code 400 (Bad Request)} if the convention has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/conventions")
    public ResponseEntity<Convention> createConvention(@RequestBody Convention convention) throws URISyntaxException {
        log.debug("REST request to save Convention : {}", convention);
        if (convention.getId() != null) {
            throw new BadRequestAlertException("A new convention cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Convention result = conventionRepository.save(convention);
        return ResponseEntity.created(new URI("/api/conventions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /conventions} : Updates an existing convention.
     *
     * @param convention the convention to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated convention,
     * or with status {@code 400 (Bad Request)} if the convention is not valid,
     * or with status {@code 500 (Internal Server Error)} if the convention couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/conventions")
    public ResponseEntity<Convention> updateConvention(@RequestBody Convention convention) throws URISyntaxException {
        log.debug("REST request to update Convention : {}", convention);
        if (convention.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Convention result = conventionRepository.save(convention);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, convention.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /conventions} : get all the conventions.
     *
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of conventions in body.
     */
    @GetMapping("/conventions")
    public List<Convention> getAllConventions(@RequestParam(required = false) String filter) {
        if ("contrat-is-null".equals(filter)) {
            log.debug("REST request to get all Conventions where contrat is null");
            return StreamSupport
                .stream(conventionRepository.findAll().spliterator(), false)
                .filter(convention -> convention.getContrat() == null)
                .collect(Collectors.toList());
        }
        log.debug("REST request to get all Conventions");
        return conventionRepository.findAll();
    }

    /**
     * {@code GET  /conventions/:id} : get the "id" convention.
     *
     * @param id the id of the convention to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the convention, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/conventions/{id}")
    public ResponseEntity<Convention> getConvention(@PathVariable Long id) {
        log.debug("REST request to get Convention : {}", id);
        Optional<Convention> convention = conventionRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(convention);
    }

    /**
     * {@code DELETE  /conventions/:id} : delete the "id" convention.
     *
     * @param id the id of the convention to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/conventions/{id}")
    public ResponseEntity<Void> deleteConvention(@PathVariable Long id) {
        log.debug("REST request to delete Convention : {}", id);
        conventionRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
