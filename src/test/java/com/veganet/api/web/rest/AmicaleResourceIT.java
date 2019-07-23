package com.veganet.api.web.rest;

import com.veganet.api.VegaCApp;
import com.veganet.api.domain.Amicale;
import com.veganet.api.repository.AmicaleRepository;
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

/**
 * Integration tests for the {@Link AmicaleResource} REST controller.
 */
@SpringBootTest(classes = VegaCApp.class)
public class AmicaleResourceIT {

    private static final String DEFAULT_ADRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADRESS = "BBBBBBBBBB";

    private static final String DEFAULT_COMPANYNAME = "AAAAAAAAAA";
    private static final String UPDATED_COMPANYNAME = "BBBBBBBBBB";

    private static final String DEFAULT_LOGO = "AAAAAAAAAA";
    private static final String UPDATED_LOGO = "BBBBBBBBBB";

    @Autowired
    private AmicaleRepository amicaleRepository;

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

    private MockMvc restAmicaleMockMvc;

    private Amicale amicale;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AmicaleResource amicaleResource = new AmicaleResource(amicaleRepository);
        this.restAmicaleMockMvc = MockMvcBuilders.standaloneSetup(amicaleResource)
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
    public static Amicale createEntity(EntityManager em) {
        Amicale amicale = new Amicale()
            .adress(DEFAULT_ADRESS)
            .companyname(DEFAULT_COMPANYNAME)
            .logo(DEFAULT_LOGO);
        return amicale;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Amicale createUpdatedEntity(EntityManager em) {
        Amicale amicale = new Amicale()
            .adress(UPDATED_ADRESS)
            .companyname(UPDATED_COMPANYNAME)
            .logo(UPDATED_LOGO);
        return amicale;
    }

    @BeforeEach
    public void initTest() {
        amicale = createEntity(em);
    }

    @Test
    @Transactional
    public void createAmicale() throws Exception {
        int databaseSizeBeforeCreate = amicaleRepository.findAll().size();

        // Create the Amicale
        restAmicaleMockMvc.perform(post("/api/amicales")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(amicale)))
            .andExpect(status().isCreated());

        // Validate the Amicale in the database
        List<Amicale> amicaleList = amicaleRepository.findAll();
        assertThat(amicaleList).hasSize(databaseSizeBeforeCreate + 1);
        Amicale testAmicale = amicaleList.get(amicaleList.size() - 1);
        assertThat(testAmicale.getAdress()).isEqualTo(DEFAULT_ADRESS);
        assertThat(testAmicale.getCompanyname()).isEqualTo(DEFAULT_COMPANYNAME);
        assertThat(testAmicale.getLogo()).isEqualTo(DEFAULT_LOGO);
    }

    @Test
    @Transactional
    public void createAmicaleWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = amicaleRepository.findAll().size();

        // Create the Amicale with an existing ID
        amicale.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAmicaleMockMvc.perform(post("/api/amicales")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(amicale)))
            .andExpect(status().isBadRequest());

        // Validate the Amicale in the database
        List<Amicale> amicaleList = amicaleRepository.findAll();
        assertThat(amicaleList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllAmicales() throws Exception {
        // Initialize the database
        amicaleRepository.saveAndFlush(amicale);

        // Get all the amicaleList
        restAmicaleMockMvc.perform(get("/api/amicales?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(amicale.getId().intValue())))
            .andExpect(jsonPath("$.[*].adress").value(hasItem(DEFAULT_ADRESS.toString())))
            .andExpect(jsonPath("$.[*].companyname").value(hasItem(DEFAULT_COMPANYNAME.toString())))
            .andExpect(jsonPath("$.[*].logo").value(hasItem(DEFAULT_LOGO.toString())));
    }
    
    @Test
    @Transactional
    public void getAmicale() throws Exception {
        // Initialize the database
        amicaleRepository.saveAndFlush(amicale);

        // Get the amicale
        restAmicaleMockMvc.perform(get("/api/amicales/{id}", amicale.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(amicale.getId().intValue()))
            .andExpect(jsonPath("$.adress").value(DEFAULT_ADRESS.toString()))
            .andExpect(jsonPath("$.companyname").value(DEFAULT_COMPANYNAME.toString()))
            .andExpect(jsonPath("$.logo").value(DEFAULT_LOGO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAmicale() throws Exception {
        // Get the amicale
        restAmicaleMockMvc.perform(get("/api/amicales/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAmicale() throws Exception {
        // Initialize the database
        amicaleRepository.saveAndFlush(amicale);

        int databaseSizeBeforeUpdate = amicaleRepository.findAll().size();

        // Update the amicale
        Amicale updatedAmicale = amicaleRepository.findById(amicale.getId()).get();
        // Disconnect from session so that the updates on updatedAmicale are not directly saved in db
        em.detach(updatedAmicale);
        updatedAmicale
            .adress(UPDATED_ADRESS)
            .companyname(UPDATED_COMPANYNAME)
            .logo(UPDATED_LOGO);

        restAmicaleMockMvc.perform(put("/api/amicales")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAmicale)))
            .andExpect(status().isOk());

        // Validate the Amicale in the database
        List<Amicale> amicaleList = amicaleRepository.findAll();
        assertThat(amicaleList).hasSize(databaseSizeBeforeUpdate);
        Amicale testAmicale = amicaleList.get(amicaleList.size() - 1);
        assertThat(testAmicale.getAdress()).isEqualTo(UPDATED_ADRESS);
        assertThat(testAmicale.getCompanyname()).isEqualTo(UPDATED_COMPANYNAME);
        assertThat(testAmicale.getLogo()).isEqualTo(UPDATED_LOGO);
    }

    @Test
    @Transactional
    public void updateNonExistingAmicale() throws Exception {
        int databaseSizeBeforeUpdate = amicaleRepository.findAll().size();

        // Create the Amicale

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAmicaleMockMvc.perform(put("/api/amicales")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(amicale)))
            .andExpect(status().isBadRequest());

        // Validate the Amicale in the database
        List<Amicale> amicaleList = amicaleRepository.findAll();
        assertThat(amicaleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAmicale() throws Exception {
        // Initialize the database
        amicaleRepository.saveAndFlush(amicale);

        int databaseSizeBeforeDelete = amicaleRepository.findAll().size();

        // Delete the amicale
        restAmicaleMockMvc.perform(delete("/api/amicales/{id}", amicale.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Amicale> amicaleList = amicaleRepository.findAll();
        assertThat(amicaleList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Amicale.class);
        Amicale amicale1 = new Amicale();
        amicale1.setId(1L);
        Amicale amicale2 = new Amicale();
        amicale2.setId(amicale1.getId());
        assertThat(amicale1).isEqualTo(amicale2);
        amicale2.setId(2L);
        assertThat(amicale1).isNotEqualTo(amicale2);
        amicale1.setId(null);
        assertThat(amicale1).isNotEqualTo(amicale2);
    }
}
