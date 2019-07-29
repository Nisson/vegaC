package com.veganet.api.web.rest;

import com.veganet.api.domain.CAEasylink;
import com.veganet.api.repository.CAEasylinkRepository;
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
 * REST controller for managing {@link com.veganet.api.domain.CAEasylink}.
 */
@RestController
@RequestMapping("/api")
public class CAEasylinkResource {

    private final Logger log = LoggerFactory.getLogger(CAEasylinkResource.class);

    private static final String ENTITY_NAME = "cAEasylink";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CAEasylinkRepository cAEasylinkRepository;

    public CAEasylinkResource(CAEasylinkRepository cAEasylinkRepository) {
        this.cAEasylinkRepository = cAEasylinkRepository;
    }

    /**
     * {@code POST  /ca-easylinks} : Create a new cAEasylink.
     *
     * @param cAEasylink the cAEasylink to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cAEasylink, or with status {@code 400 (Bad Request)} if the cAEasylink has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/ca-easylinks")
    public ResponseEntity<CAEasylink> createCAEasylink(@RequestBody CAEasylink cAEasylink) throws URISyntaxException {
        log.debug("REST request to save CAEasylink : {}", cAEasylink);
        if (cAEasylink.getId() != null) {
            throw new BadRequestAlertException("A new cAEasylink cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CAEasylink result = cAEasylinkRepository.save(cAEasylink);
        return ResponseEntity.created(new URI("/api/ca-easylinks/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /ca-easylinks} : Updates an existing cAEasylink.
     *
     * @param cAEasylink the cAEasylink to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cAEasylink,
     * or with status {@code 400 (Bad Request)} if the cAEasylink is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cAEasylink couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/ca-easylinks")
    public ResponseEntity<CAEasylink> updateCAEasylink(@RequestBody CAEasylink cAEasylink) throws URISyntaxException {
        log.debug("REST request to update CAEasylink : {}", cAEasylink);
        if (cAEasylink.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CAEasylink result = cAEasylinkRepository.save(cAEasylink);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cAEasylink.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /ca-easylinks} : get all the cAEasylinks.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cAEasylinks in body.
     */
    @GetMapping("/ca-easylinks")
    public List<CAEasylink> getAllCAEasylinks() {
        log.debug("REST request to get all CAEasylinks");
        return cAEasylinkRepository.findAll();
    }

    /**
     * {@code GET  /ca-easylinks/:id} : get the "id" cAEasylink.
     *
     * @param id the id of the cAEasylink to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cAEasylink, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/ca-easylinks/{id}")
    public ResponseEntity<CAEasylink> getCAEasylink(@PathVariable Long id) {
        log.debug("REST request to get CAEasylink : {}", id);
        Optional<CAEasylink> cAEasylink = cAEasylinkRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(cAEasylink);
    }

    /**
     * {@code DELETE  /ca-easylinks/:id} : delete the "id" cAEasylink.
     *
     * @param id the id of the cAEasylink to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/ca-easylinks/{id}")
    public ResponseEntity<Void> deleteCAEasylink(@PathVariable Long id) {
        log.debug("REST request to delete CAEasylink : {}", id);
        cAEasylinkRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
