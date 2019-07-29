package com.veganet.api.web.rest;

import com.veganet.api.domain.Amicale;
import com.veganet.api.repository.AmicaleRepository;
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
 * REST controller for managing {@link com.veganet.api.domain.Amicale}.
 */
@RestController
@RequestMapping("/api")
public class AmicaleResource {

    private final Logger log = LoggerFactory.getLogger(AmicaleResource.class);

    private static final String ENTITY_NAME = "amicale";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AmicaleRepository amicaleRepository;

    public AmicaleResource(AmicaleRepository amicaleRepository) {
        this.amicaleRepository = amicaleRepository;
    }

    /**
     * {@code POST  /amicales} : Create a new amicale.
     *
     * @param amicale the amicale to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new amicale, or with status {@code 400 (Bad Request)} if the amicale has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/amicales")
    public ResponseEntity<Amicale> createAmicale(@RequestBody Amicale amicale) throws URISyntaxException {
        log.debug("REST request to save Amicale : {}", amicale);
        if (amicale.getId() != null) {
            throw new BadRequestAlertException("A new amicale cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Amicale result = amicaleRepository.save(amicale);
        return ResponseEntity.created(new URI("/api/amicales/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /amicales} : Updates an existing amicale.
     *
     * @param amicale the amicale to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated amicale,
     * or with status {@code 400 (Bad Request)} if the amicale is not valid,
     * or with status {@code 500 (Internal Server Error)} if the amicale couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/amicales")
    public ResponseEntity<Amicale> updateAmicale(@RequestBody Amicale amicale) throws URISyntaxException {
        log.debug("REST request to update Amicale : {}", amicale);
        if (amicale.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Amicale result = amicaleRepository.save(amicale);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, amicale.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /amicales} : get all the amicales.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of amicales in body.
     */
    @GetMapping("/amicales")
    public List<Amicale> getAllAmicales() {
        log.debug("REST request to get all Amicales");
        return amicaleRepository.findAll();
    }

    /**
     * {@code GET  /amicales/:id} : get the "id" amicale.
     *
     * @param id the id of the amicale to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the amicale, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/amicales/{id}")
    public ResponseEntity<Amicale> getAmicale(@PathVariable Long id) {
        log.debug("REST request to get Amicale : {}", id);
        Optional<Amicale> amicale = amicaleRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(amicale);
    }

    /**
     * {@code DELETE  /amicales/:id} : delete the "id" amicale.
     *
     * @param id the id of the amicale to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/amicales/{id}")
    public ResponseEntity<Void> deleteAmicale(@PathVariable Long id) {
        log.debug("REST request to delete Amicale : {}", id);
        amicaleRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
