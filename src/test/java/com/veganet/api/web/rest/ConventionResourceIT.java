package com.veganet.api.web.rest;

import com.veganet.api.PropApp;
import com.veganet.api.domain.Convention;
import com.veganet.api.repository.ConventionRepository;
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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static com.veganet.api.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@Link ConventionResource} REST controller.
 */
@SpringBootTest(classes = PropApp.class)
public class ConventionResourceIT {

    private static final String DEFAULT_TITLECONVENTION = "AAAAAAAAAA";
    private static final String UPDATED_TITLECONVENTION = "BBBBBBBBBB";

    private static final Double DEFAULT_AMOUNTCONVENTION = 1D;
    private static final Double UPDATED_AMOUNTCONVENTION = 2D;

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_ENDDATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_ENDDATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_STARTDATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_STARTDATE = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_DURATION = 1;
    private static final Integer UPDATED_DURATION = 2;

    private static final Double DEFAULT_TOTALPRICE = 1D;
    private static final Double UPDATED_TOTALPRICE = 2D;

    @Autowired
    private ConventionRepository conventionRepository;

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

    private MockMvc restConventionMockMvc;

    private Convention convention;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ConventionResource conventionResource = new ConventionResource(conventionRepository);
        this.restConventionMockMvc = MockMvcBuilders.standaloneSetup(conventionResource)
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
    public static Convention createEntity(EntityManager em) {
        Convention convention = new Convention()
            .titleconvention(DEFAULT_TITLECONVENTION)
            .amountconvention(DEFAULT_AMOUNTCONVENTION)
            .description(DEFAULT_DESCRIPTION)
            .enddate(DEFAULT_ENDDATE)
            .startdate(DEFAULT_STARTDATE)
            .duration(DEFAULT_DURATION)
            .totalprice(DEFAULT_TOTALPRICE);
        return convention;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Convention createUpdatedEntity(EntityManager em) {
        Convention convention = new Convention()
            .titleconvention(UPDATED_TITLECONVENTION)
            .amountconvention(UPDATED_AMOUNTCONVENTION)
            .description(UPDATED_DESCRIPTION)
            .enddate(UPDATED_ENDDATE)
            .startdate(UPDATED_STARTDATE)
            .duration(UPDATED_DURATION)
            .totalprice(UPDATED_TOTALPRICE);
        return convention;
    }

    @BeforeEach
    public void initTest() {
        convention = createEntity(em);
    }

    @Test
    @Transactional
    public void createConvention() throws Exception {
        int databaseSizeBeforeCreate = conventionRepository.findAll().size();

        // Create the Convention
        restConventionMockMvc.perform(post("/api/conventions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(convention)))
            .andExpect(status().isCreated());

        // Validate the Convention in the database
        List<Convention> conventionList = conventionRepository.findAll();
        assertThat(conventionList).hasSize(databaseSizeBeforeCreate + 1);
        Convention testConvention = conventionList.get(conventionList.size() - 1);
        assertThat(testConvention.getTitleconvention()).isEqualTo(DEFAULT_TITLECONVENTION);
        assertThat(testConvention.getAmountconvention()).isEqualTo(DEFAULT_AMOUNTCONVENTION);
        assertThat(testConvention.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testConvention.getEnddate()).isEqualTo(DEFAULT_ENDDATE);
        assertThat(testConvention.getStartdate()).isEqualTo(DEFAULT_STARTDATE);
        assertThat(testConvention.getDuration()).isEqualTo(DEFAULT_DURATION);
        assertThat(testConvention.getTotalprice()).isEqualTo(DEFAULT_TOTALPRICE);
    }

    @Test
    @Transactional
    public void createConventionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = conventionRepository.findAll().size();

        // Create the Convention with an existing ID
        convention.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restConventionMockMvc.perform(post("/api/conventions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(convention)))
            .andExpect(status().isBadRequest());

        // Validate the Convention in the database
        List<Convention> conventionList = conventionRepository.findAll();
        assertThat(conventionList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllConventions() throws Exception {
        // Initialize the database
        conventionRepository.saveAndFlush(convention);

        // Get all the conventionList
        restConventionMockMvc.perform(get("/api/conventions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(convention.getId().intValue())))
            .andExpect(jsonPath("$.[*].titleconvention").value(hasItem(DEFAULT_TITLECONVENTION.toString())))
            .andExpect(jsonPath("$.[*].amountconvention").value(hasItem(DEFAULT_AMOUNTCONVENTION.doubleValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].enddate").value(hasItem(DEFAULT_ENDDATE.toString())))
            .andExpect(jsonPath("$.[*].startdate").value(hasItem(DEFAULT_STARTDATE.toString())))
            .andExpect(jsonPath("$.[*].duration").value(hasItem(DEFAULT_DURATION)))
            .andExpect(jsonPath("$.[*].totalprice").value(hasItem(DEFAULT_TOTALPRICE.doubleValue())));
    }
    
    @Test
    @Transactional
    public void getConvention() throws Exception {
        // Initialize the database
        conventionRepository.saveAndFlush(convention);

        // Get the convention
        restConventionMockMvc.perform(get("/api/conventions/{id}", convention.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(convention.getId().intValue()))
            .andExpect(jsonPath("$.titleconvention").value(DEFAULT_TITLECONVENTION.toString()))
            .andExpect(jsonPath("$.amountconvention").value(DEFAULT_AMOUNTCONVENTION.doubleValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.enddate").value(DEFAULT_ENDDATE.toString()))
            .andExpect(jsonPath("$.startdate").value(DEFAULT_STARTDATE.toString()))
            .andExpect(jsonPath("$.duration").value(DEFAULT_DURATION))
            .andExpect(jsonPath("$.totalprice").value(DEFAULT_TOTALPRICE.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingConvention() throws Exception {
        // Get the convention
        restConventionMockMvc.perform(get("/api/conventions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateConvention() throws Exception {
        // Initialize the database
        conventionRepository.saveAndFlush(convention);

        int databaseSizeBeforeUpdate = conventionRepository.findAll().size();

        // Update the convention
        Convention updatedConvention = conventionRepository.findById(convention.getId()).get();
        // Disconnect from session so that the updates on updatedConvention are not directly saved in db
        em.detach(updatedConvention);
        updatedConvention
            .titleconvention(UPDATED_TITLECONVENTION)
            .amountconvention(UPDATED_AMOUNTCONVENTION)
            .description(UPDATED_DESCRIPTION)
            .enddate(UPDATED_ENDDATE)
            .startdate(UPDATED_STARTDATE)
            .duration(UPDATED_DURATION)
            .totalprice(UPDATED_TOTALPRICE);

        restConventionMockMvc.perform(put("/api/conventions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedConvention)))
            .andExpect(status().isOk());

        // Validate the Convention in the database
        List<Convention> conventionList = conventionRepository.findAll();
        assertThat(conventionList).hasSize(databaseSizeBeforeUpdate);
        Convention testConvention = conventionList.get(conventionList.size() - 1);
        assertThat(testConvention.getTitleconvention()).isEqualTo(UPDATED_TITLECONVENTION);
        assertThat(testConvention.getAmountconvention()).isEqualTo(UPDATED_AMOUNTCONVENTION);
        assertThat(testConvention.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testConvention.getEnddate()).isEqualTo(UPDATED_ENDDATE);
        assertThat(testConvention.getStartdate()).isEqualTo(UPDATED_STARTDATE);
        assertThat(testConvention.getDuration()).isEqualTo(UPDATED_DURATION);
        assertThat(testConvention.getTotalprice()).isEqualTo(UPDATED_TOTALPRICE);
    }

    @Test
    @Transactional
    public void updateNonExistingConvention() throws Exception {
        int databaseSizeBeforeUpdate = conventionRepository.findAll().size();

        // Create the Convention

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restConventionMockMvc.perform(put("/api/conventions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(convention)))
            .andExpect(status().isBadRequest());

        // Validate the Convention in the database
        List<Convention> conventionList = conventionRepository.findAll();
        assertThat(conventionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteConvention() throws Exception {
        // Initialize the database
        conventionRepository.saveAndFlush(convention);

        int databaseSizeBeforeDelete = conventionRepository.findAll().size();

        // Delete the convention
        restConventionMockMvc.perform(delete("/api/conventions/{id}", convention.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Convention> conventionList = conventionRepository.findAll();
        assertThat(conventionList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Convention.class);
        Convention convention1 = new Convention();
        convention1.setId(1L);
        Convention convention2 = new Convention();
        convention2.setId(convention1.getId());
        assertThat(convention1).isEqualTo(convention2);
        convention2.setId(2L);
        assertThat(convention1).isNotEqualTo(convention2);
        convention1.setId(null);
        assertThat(convention1).isNotEqualTo(convention2);
    }
}
