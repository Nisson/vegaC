package com.veganet.api.web.rest;

import com.veganet.api.PropApp;
import com.veganet.api.domain.CAEasylink;
import com.veganet.api.repository.CAEasylinkRepository;
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

import com.veganet.api.domain.enumeration.TypePeriode;
/**
 * Integration tests for the {@Link CAEasylinkResource} REST controller.
 */
@SpringBootTest(classes = PropApp.class)
public class CAEasylinkResourceIT {

    private static final Double DEFAULT_GAIN = 1D;
    private static final Double UPDATED_GAIN = 2D;

    private static final TypePeriode DEFAULT_TYPE_PERIODE = TypePeriode.JOURNALIERE;
    private static final TypePeriode UPDATED_TYPE_PERIODE = TypePeriode.HEBDOMADAIRE;

    @Autowired
    private CAEasylinkRepository cAEasylinkRepository;

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

    private MockMvc restCAEasylinkMockMvc;

    private CAEasylink cAEasylink;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CAEasylinkResource cAEasylinkResource = new CAEasylinkResource(cAEasylinkRepository);
        this.restCAEasylinkMockMvc = MockMvcBuilders.standaloneSetup(cAEasylinkResource)
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
    public static CAEasylink createEntity(EntityManager em) {
        CAEasylink cAEasylink = new CAEasylink()
            .gain(DEFAULT_GAIN)
            .typePeriode(DEFAULT_TYPE_PERIODE);
        return cAEasylink;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CAEasylink createUpdatedEntity(EntityManager em) {
        CAEasylink cAEasylink = new CAEasylink()
            .gain(UPDATED_GAIN)
            .typePeriode(UPDATED_TYPE_PERIODE);
        return cAEasylink;
    }

    @BeforeEach
    public void initTest() {
        cAEasylink = createEntity(em);
    }

    @Test
    @Transactional
    public void createCAEasylink() throws Exception {
        int databaseSizeBeforeCreate = cAEasylinkRepository.findAll().size();

        // Create the CAEasylink
        restCAEasylinkMockMvc.perform(post("/api/ca-easylinks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cAEasylink)))
            .andExpect(status().isCreated());

        // Validate the CAEasylink in the database
        List<CAEasylink> cAEasylinkList = cAEasylinkRepository.findAll();
        assertThat(cAEasylinkList).hasSize(databaseSizeBeforeCreate + 1);
        CAEasylink testCAEasylink = cAEasylinkList.get(cAEasylinkList.size() - 1);
        assertThat(testCAEasylink.getGain()).isEqualTo(DEFAULT_GAIN);
        assertThat(testCAEasylink.getTypePeriode()).isEqualTo(DEFAULT_TYPE_PERIODE);
    }

    @Test
    @Transactional
    public void createCAEasylinkWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = cAEasylinkRepository.findAll().size();

        // Create the CAEasylink with an existing ID
        cAEasylink.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCAEasylinkMockMvc.perform(post("/api/ca-easylinks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cAEasylink)))
            .andExpect(status().isBadRequest());

        // Validate the CAEasylink in the database
        List<CAEasylink> cAEasylinkList = cAEasylinkRepository.findAll();
        assertThat(cAEasylinkList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllCAEasylinks() throws Exception {
        // Initialize the database
        cAEasylinkRepository.saveAndFlush(cAEasylink);

        // Get all the cAEasylinkList
        restCAEasylinkMockMvc.perform(get("/api/ca-easylinks?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cAEasylink.getId().intValue())))
            .andExpect(jsonPath("$.[*].gain").value(hasItem(DEFAULT_GAIN.doubleValue())))
            .andExpect(jsonPath("$.[*].typePeriode").value(hasItem(DEFAULT_TYPE_PERIODE.toString())));
    }
    
    @Test
    @Transactional
    public void getCAEasylink() throws Exception {
        // Initialize the database
        cAEasylinkRepository.saveAndFlush(cAEasylink);

        // Get the cAEasylink
        restCAEasylinkMockMvc.perform(get("/api/ca-easylinks/{id}", cAEasylink.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(cAEasylink.getId().intValue()))
            .andExpect(jsonPath("$.gain").value(DEFAULT_GAIN.doubleValue()))
            .andExpect(jsonPath("$.typePeriode").value(DEFAULT_TYPE_PERIODE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCAEasylink() throws Exception {
        // Get the cAEasylink
        restCAEasylinkMockMvc.perform(get("/api/ca-easylinks/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCAEasylink() throws Exception {
        // Initialize the database
        cAEasylinkRepository.saveAndFlush(cAEasylink);

        int databaseSizeBeforeUpdate = cAEasylinkRepository.findAll().size();

        // Update the cAEasylink
        CAEasylink updatedCAEasylink = cAEasylinkRepository.findById(cAEasylink.getId()).get();
        // Disconnect from session so that the updates on updatedCAEasylink are not directly saved in db
        em.detach(updatedCAEasylink);
        updatedCAEasylink
            .gain(UPDATED_GAIN)
            .typePeriode(UPDATED_TYPE_PERIODE);

        restCAEasylinkMockMvc.perform(put("/api/ca-easylinks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCAEasylink)))
            .andExpect(status().isOk());

        // Validate the CAEasylink in the database
        List<CAEasylink> cAEasylinkList = cAEasylinkRepository.findAll();
        assertThat(cAEasylinkList).hasSize(databaseSizeBeforeUpdate);
        CAEasylink testCAEasylink = cAEasylinkList.get(cAEasylinkList.size() - 1);
        assertThat(testCAEasylink.getGain()).isEqualTo(UPDATED_GAIN);
        assertThat(testCAEasylink.getTypePeriode()).isEqualTo(UPDATED_TYPE_PERIODE);
    }

    @Test
    @Transactional
    public void updateNonExistingCAEasylink() throws Exception {
        int databaseSizeBeforeUpdate = cAEasylinkRepository.findAll().size();

        // Create the CAEasylink

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCAEasylinkMockMvc.perform(put("/api/ca-easylinks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cAEasylink)))
            .andExpect(status().isBadRequest());

        // Validate the CAEasylink in the database
        List<CAEasylink> cAEasylinkList = cAEasylinkRepository.findAll();
        assertThat(cAEasylinkList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCAEasylink() throws Exception {
        // Initialize the database
        cAEasylinkRepository.saveAndFlush(cAEasylink);

        int databaseSizeBeforeDelete = cAEasylinkRepository.findAll().size();

        // Delete the cAEasylink
        restCAEasylinkMockMvc.perform(delete("/api/ca-easylinks/{id}", cAEasylink.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CAEasylink> cAEasylinkList = cAEasylinkRepository.findAll();
        assertThat(cAEasylinkList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CAEasylink.class);
        CAEasylink cAEasylink1 = new CAEasylink();
        cAEasylink1.setId(1L);
        CAEasylink cAEasylink2 = new CAEasylink();
        cAEasylink2.setId(cAEasylink1.getId());
        assertThat(cAEasylink1).isEqualTo(cAEasylink2);
        cAEasylink2.setId(2L);
        assertThat(cAEasylink1).isNotEqualTo(cAEasylink2);
        cAEasylink1.setId(null);
        assertThat(cAEasylink1).isNotEqualTo(cAEasylink2);
    }
}
