package com.veganet.api.web.rest;

import com.veganet.api.VegaCApp;
import com.veganet.api.domain.RegleCommission;
import com.veganet.api.repository.RegleCommissionRepository;
import com.veganet.api.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;

import static com.veganet.api.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.veganet.api.domain.enumeration.TypeMontant;
/**
 * Integration tests for the {@Link RegleCommissionResource} REST controller.
 */
@SpringBootTest(classes = VegaCApp.class)
public class RegleCommissionResourceIT {

    private static final Double DEFAULT_MIN_CA = 1D;
    private static final Double UPDATED_MIN_CA = 2D;

    private static final Double DEFAULT_MAX_CA = 1D;
    private static final Double UPDATED_MAX_CA = 2D;

    private static final TypeMontant DEFAULT_TYPE_MONTANT = TypeMontant.POURCENTAGE;
    private static final TypeMontant UPDATED_TYPE_MONTANT = TypeMontant.DT;

    private static final Double DEFAULT_MONTANTREGLE = 1D;
    private static final Double UPDATED_MONTANTREGLE = 2D;

    @Autowired
    private RegleCommissionRepository regleCommissionRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restRegleCommissionMockMvc;

    private RegleCommission regleCommission;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final RegleCommissionResource regleCommissionResource = new RegleCommissionResource(regleCommissionRepository);
        this.restRegleCommissionMockMvc = MockMvcBuilders.standaloneSetup(regleCommissionResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RegleCommission createEntity(EntityManager em) {
        RegleCommission regleCommission = new RegleCommission()
            .minCA(DEFAULT_MIN_CA)
            .maxCa(DEFAULT_MAX_CA)
            .typeMontant(DEFAULT_TYPE_MONTANT)
            .montantregle(DEFAULT_MONTANTREGLE);
        return regleCommission;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RegleCommission createUpdatedEntity(EntityManager em) {
        RegleCommission regleCommission = new RegleCommission()
            .minCA(UPDATED_MIN_CA)
            .maxCa(UPDATED_MAX_CA)
            .typeMontant(UPDATED_TYPE_MONTANT)
            .montantregle(UPDATED_MONTANTREGLE);
        return regleCommission;
    }

    @BeforeEach
    public void initTest() {
        regleCommission = createEntity(em);
    }

    @Test
    @Transactional
    public void createRegleCommission() throws Exception {
        int databaseSizeBeforeCreate = regleCommissionRepository.findAll().size();

        // Create the RegleCommission
        restRegleCommissionMockMvc.perform(post("/api/regle-commissions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(regleCommission)))
            .andExpect(status().isCreated());

        // Validate the RegleCommission in the database
        List<RegleCommission> regleCommissionList = regleCommissionRepository.findAll();
        assertThat(regleCommissionList).hasSize(databaseSizeBeforeCreate + 1);
        RegleCommission testRegleCommission = regleCommissionList.get(regleCommissionList.size() - 1);
        assertThat(testRegleCommission.getMinCA()).isEqualTo(DEFAULT_MIN_CA);
        assertThat(testRegleCommission.getMaxCa()).isEqualTo(DEFAULT_MAX_CA);
        assertThat(testRegleCommission.getTypeMontant()).isEqualTo(DEFAULT_TYPE_MONTANT);
        assertThat(testRegleCommission.getMontantregle()).isEqualTo(DEFAULT_MONTANTREGLE);
    }

    @Test
    @Transactional
    public void createRegleCommissionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = regleCommissionRepository.findAll().size();

        // Create the RegleCommission with an existing ID
        regleCommission.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRegleCommissionMockMvc.perform(post("/api/regle-commissions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(regleCommission)))
            .andExpect(status().isBadRequest());

        // Validate the RegleCommission in the database
        List<RegleCommission> regleCommissionList = regleCommissionRepository.findAll();
        assertThat(regleCommissionList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllRegleCommissions() throws Exception {
        // Initialize the database
        regleCommissionRepository.saveAndFlush(regleCommission);

        // Get all the regleCommissionList
        restRegleCommissionMockMvc.perform(get("/api/regle-commissions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(regleCommission.getId().intValue())))
            .andExpect(jsonPath("$.[*].minCA").value(hasItem(DEFAULT_MIN_CA.doubleValue())))
            .andExpect(jsonPath("$.[*].maxCa").value(hasItem(DEFAULT_MAX_CA.doubleValue())))
            .andExpect(jsonPath("$.[*].typeMontant").value(hasItem(DEFAULT_TYPE_MONTANT.toString())))
            .andExpect(jsonPath("$.[*].montantregle").value(hasItem(DEFAULT_MONTANTREGLE.doubleValue())));
    }
    
    @Test
    @Transactional
    public void getRegleCommission() throws Exception {
        // Initialize the database
        regleCommissionRepository.saveAndFlush(regleCommission);

        // Get the regleCommission
        restRegleCommissionMockMvc.perform(get("/api/regle-commissions/{id}", regleCommission.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(regleCommission.getId().intValue()))
            .andExpect(jsonPath("$.minCA").value(DEFAULT_MIN_CA.doubleValue()))
            .andExpect(jsonPath("$.maxCa").value(DEFAULT_MAX_CA.doubleValue()))
            .andExpect(jsonPath("$.typeMontant").value(DEFAULT_TYPE_MONTANT.toString()))
            .andExpect(jsonPath("$.montantregle").value(DEFAULT_MONTANTREGLE.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingRegleCommission() throws Exception {
        // Get the regleCommission
        restRegleCommissionMockMvc.perform(get("/api/regle-commissions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRegleCommission() throws Exception {
        // Initialize the database
        regleCommissionRepository.saveAndFlush(regleCommission);

        int databaseSizeBeforeUpdate = regleCommissionRepository.findAll().size();

        // Update the regleCommission
        RegleCommission updatedRegleCommission = regleCommissionRepository.findById(regleCommission.getId()).get();
        // Disconnect from session so that the updates on updatedRegleCommission are not directly saved in db
        em.detach(updatedRegleCommission);
        updatedRegleCommission
            .minCA(UPDATED_MIN_CA)
            .maxCa(UPDATED_MAX_CA)
            .typeMontant(UPDATED_TYPE_MONTANT)
            .montantregle(UPDATED_MONTANTREGLE);

        restRegleCommissionMockMvc.perform(put("/api/regle-commissions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedRegleCommission)))
            .andExpect(status().isOk());

        // Validate the RegleCommission in the database
        List<RegleCommission> regleCommissionList = regleCommissionRepository.findAll();
        assertThat(regleCommissionList).hasSize(databaseSizeBeforeUpdate);
        RegleCommission testRegleCommission = regleCommissionList.get(regleCommissionList.size() - 1);
        assertThat(testRegleCommission.getMinCA()).isEqualTo(UPDATED_MIN_CA);
        assertThat(testRegleCommission.getMaxCa()).isEqualTo(UPDATED_MAX_CA);
        assertThat(testRegleCommission.getTypeMontant()).isEqualTo(UPDATED_TYPE_MONTANT);
        assertThat(testRegleCommission.getMontantregle()).isEqualTo(UPDATED_MONTANTREGLE);
    }

    @Test
    @Transactional
    public void updateNonExistingRegleCommission() throws Exception {
        int databaseSizeBeforeUpdate = regleCommissionRepository.findAll().size();

        // Create the RegleCommission

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRegleCommissionMockMvc.perform(put("/api/regle-commissions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(regleCommission)))
            .andExpect(status().isBadRequest());

        // Validate the RegleCommission in the database
        List<RegleCommission> regleCommissionList = regleCommissionRepository.findAll();
        assertThat(regleCommissionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteRegleCommission() throws Exception {
        // Initialize the database
        regleCommissionRepository.saveAndFlush(regleCommission);

        int databaseSizeBeforeDelete = regleCommissionRepository.findAll().size();

        // Delete the regleCommission
        restRegleCommissionMockMvc.perform(delete("/api/regle-commissions/{id}", regleCommission.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<RegleCommission> regleCommissionList = regleCommissionRepository.findAll();
        assertThat(regleCommissionList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RegleCommission.class);
        RegleCommission regleCommission1 = new RegleCommission();
        regleCommission1.setId(1L);
        RegleCommission regleCommission2 = new RegleCommission();
        regleCommission2.setId(regleCommission1.getId());
        assertThat(regleCommission1).isEqualTo(regleCommission2);
        regleCommission2.setId(2L);
        assertThat(regleCommission1).isNotEqualTo(regleCommission2);
        regleCommission1.setId(null);
        assertThat(regleCommission1).isNotEqualTo(regleCommission2);
    }
}
