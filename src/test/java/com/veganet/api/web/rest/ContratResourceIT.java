package com.veganet.api.web.rest;

import com.veganet.api.VegaCApp;
import com.veganet.api.domain.Contrat;
import com.veganet.api.repository.ContratRepository;
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

import com.veganet.api.domain.enumeration.TypePeriode;
import com.veganet.api.domain.enumeration.TypeCommission;
import com.veganet.api.domain.enumeration.TypeMontant;
/**
 * Integration tests for the {@Link ContratResource} REST controller.
 */
@SpringBootTest(classes = VegaCApp.class)
public class ContratResourceIT {

    private static final LocalDate DEFAULT_DATEDEB = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATEDEB = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATEFIN = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATEFIN = LocalDate.now(ZoneId.systemDefault());

    private static final TypePeriode DEFAULT_TYPE_PERIODE = TypePeriode.JOURNALIERE;
    private static final TypePeriode UPDATED_TYPE_PERIODE = TypePeriode.HEBDOMADAIRE;

    private static final TypeCommission DEFAULT_TYPE_COMMISSION = TypeCommission.FORFAITAIREPARTRANSACTION;
    private static final TypeCommission UPDATED_TYPE_COMMISSION = TypeCommission.FORFAITAIREPARPERIODE;

    private static final TypeMontant DEFAULT_TYPE_MONTANT = TypeMontant.POURCENTAGE;
    private static final TypeMontant UPDATED_TYPE_MONTANT = TypeMontant.DT;

    private static final Double DEFAULT_MONTANT_COMMISSION = 1D;
    private static final Double UPDATED_MONTANT_COMMISSION = 2D;

    private static final Boolean DEFAULT_PAR_PALIER = false;
    private static final Boolean UPDATED_PAR_PALIER = true;

    @Autowired
    private ContratRepository contratRepository;

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

    private MockMvc restContratMockMvc;

    private Contrat contrat;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ContratResource contratResource = new ContratResource(contratRepository);
        this.restContratMockMvc = MockMvcBuilders.standaloneSetup(contratResource)
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
    public static Contrat createEntity(EntityManager em) {
        Contrat contrat = new Contrat()
            .datedeb(DEFAULT_DATEDEB)
            .datefin(DEFAULT_DATEFIN)
            .typePeriode(DEFAULT_TYPE_PERIODE)
            .typeCommission(DEFAULT_TYPE_COMMISSION)
            .typeMontant(DEFAULT_TYPE_MONTANT)
            .montantCommission(DEFAULT_MONTANT_COMMISSION)
            .parPalier(DEFAULT_PAR_PALIER);
        return contrat;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Contrat createUpdatedEntity(EntityManager em) {
        Contrat contrat = new Contrat()
            .datedeb(UPDATED_DATEDEB)
            .datefin(UPDATED_DATEFIN)
            .typePeriode(UPDATED_TYPE_PERIODE)
            .typeCommission(UPDATED_TYPE_COMMISSION)
            .typeMontant(UPDATED_TYPE_MONTANT)
            .montantCommission(UPDATED_MONTANT_COMMISSION)
            .parPalier(UPDATED_PAR_PALIER);
        return contrat;
    }

    @BeforeEach
    public void initTest() {
        contrat = createEntity(em);
    }

    @Test
    @Transactional
    public void createContrat() throws Exception {
        int databaseSizeBeforeCreate = contratRepository.findAll().size();

        // Create the Contrat
        restContratMockMvc.perform(post("/api/contrats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contrat)))
            .andExpect(status().isCreated());

        // Validate the Contrat in the database
        List<Contrat> contratList = contratRepository.findAll();
        assertThat(contratList).hasSize(databaseSizeBeforeCreate + 1);
        Contrat testContrat = contratList.get(contratList.size() - 1);
        assertThat(testContrat.getDatedeb()).isEqualTo(DEFAULT_DATEDEB);
        assertThat(testContrat.getDatefin()).isEqualTo(DEFAULT_DATEFIN);
        assertThat(testContrat.getTypePeriode()).isEqualTo(DEFAULT_TYPE_PERIODE);
        assertThat(testContrat.getTypeCommission()).isEqualTo(DEFAULT_TYPE_COMMISSION);
        assertThat(testContrat.getTypeMontant()).isEqualTo(DEFAULT_TYPE_MONTANT);
        assertThat(testContrat.getMontantCommission()).isEqualTo(DEFAULT_MONTANT_COMMISSION);
        assertThat(testContrat.isParPalier()).isEqualTo(DEFAULT_PAR_PALIER);
    }

    @Test
    @Transactional
    public void createContratWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = contratRepository.findAll().size();

        // Create the Contrat with an existing ID
        contrat.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restContratMockMvc.perform(post("/api/contrats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contrat)))
            .andExpect(status().isBadRequest());

        // Validate the Contrat in the database
        List<Contrat> contratList = contratRepository.findAll();
        assertThat(contratList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllContrats() throws Exception {
        // Initialize the database
        contratRepository.saveAndFlush(contrat);

        // Get all the contratList
        restContratMockMvc.perform(get("/api/contrats?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(contrat.getId().intValue())))
            .andExpect(jsonPath("$.[*].datedeb").value(hasItem(DEFAULT_DATEDEB.toString())))
            .andExpect(jsonPath("$.[*].datefin").value(hasItem(DEFAULT_DATEFIN.toString())))
            .andExpect(jsonPath("$.[*].typePeriode").value(hasItem(DEFAULT_TYPE_PERIODE.toString())))
            .andExpect(jsonPath("$.[*].typeCommission").value(hasItem(DEFAULT_TYPE_COMMISSION.toString())))
            .andExpect(jsonPath("$.[*].typeMontant").value(hasItem(DEFAULT_TYPE_MONTANT.toString())))
            .andExpect(jsonPath("$.[*].montantCommission").value(hasItem(DEFAULT_MONTANT_COMMISSION.doubleValue())))
            .andExpect(jsonPath("$.[*].parPalier").value(hasItem(DEFAULT_PAR_PALIER.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getContrat() throws Exception {
        // Initialize the database
        contratRepository.saveAndFlush(contrat);

        // Get the contrat
        restContratMockMvc.perform(get("/api/contrats/{id}", contrat.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(contrat.getId().intValue()))
            .andExpect(jsonPath("$.datedeb").value(DEFAULT_DATEDEB.toString()))
            .andExpect(jsonPath("$.datefin").value(DEFAULT_DATEFIN.toString()))
            .andExpect(jsonPath("$.typePeriode").value(DEFAULT_TYPE_PERIODE.toString()))
            .andExpect(jsonPath("$.typeCommission").value(DEFAULT_TYPE_COMMISSION.toString()))
            .andExpect(jsonPath("$.typeMontant").value(DEFAULT_TYPE_MONTANT.toString()))
            .andExpect(jsonPath("$.montantCommission").value(DEFAULT_MONTANT_COMMISSION.doubleValue()))
            .andExpect(jsonPath("$.parPalier").value(DEFAULT_PAR_PALIER.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingContrat() throws Exception {
        // Get the contrat
        restContratMockMvc.perform(get("/api/contrats/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateContrat() throws Exception {
        // Initialize the database
        contratRepository.saveAndFlush(contrat);

        int databaseSizeBeforeUpdate = contratRepository.findAll().size();

        // Update the contrat
        Contrat updatedContrat = contratRepository.findById(contrat.getId()).get();
        // Disconnect from session so that the updates on updatedContrat are not directly saved in db
        em.detach(updatedContrat);
        updatedContrat
            .datedeb(UPDATED_DATEDEB)
            .datefin(UPDATED_DATEFIN)
            .typePeriode(UPDATED_TYPE_PERIODE)
            .typeCommission(UPDATED_TYPE_COMMISSION)
            .typeMontant(UPDATED_TYPE_MONTANT)
            .montantCommission(UPDATED_MONTANT_COMMISSION)
            .parPalier(UPDATED_PAR_PALIER);

        restContratMockMvc.perform(put("/api/contrats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedContrat)))
            .andExpect(status().isOk());

        // Validate the Contrat in the database
        List<Contrat> contratList = contratRepository.findAll();
        assertThat(contratList).hasSize(databaseSizeBeforeUpdate);
        Contrat testContrat = contratList.get(contratList.size() - 1);
        assertThat(testContrat.getDatedeb()).isEqualTo(UPDATED_DATEDEB);
        assertThat(testContrat.getDatefin()).isEqualTo(UPDATED_DATEFIN);
        assertThat(testContrat.getTypePeriode()).isEqualTo(UPDATED_TYPE_PERIODE);
        assertThat(testContrat.getTypeCommission()).isEqualTo(UPDATED_TYPE_COMMISSION);
        assertThat(testContrat.getTypeMontant()).isEqualTo(UPDATED_TYPE_MONTANT);
        assertThat(testContrat.getMontantCommission()).isEqualTo(UPDATED_MONTANT_COMMISSION);
        assertThat(testContrat.isParPalier()).isEqualTo(UPDATED_PAR_PALIER);
    }

    @Test
    @Transactional
    public void updateNonExistingContrat() throws Exception {
        int databaseSizeBeforeUpdate = contratRepository.findAll().size();

        // Create the Contrat

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restContratMockMvc.perform(put("/api/contrats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contrat)))
            .andExpect(status().isBadRequest());

        // Validate the Contrat in the database
        List<Contrat> contratList = contratRepository.findAll();
        assertThat(contratList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteContrat() throws Exception {
        // Initialize the database
        contratRepository.saveAndFlush(contrat);

        int databaseSizeBeforeDelete = contratRepository.findAll().size();

        // Delete the contrat
        restContratMockMvc.perform(delete("/api/contrats/{id}", contrat.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Contrat> contratList = contratRepository.findAll();
        assertThat(contratList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Contrat.class);
        Contrat contrat1 = new Contrat();
        contrat1.setId(1L);
        Contrat contrat2 = new Contrat();
        contrat2.setId(contrat1.getId());
        assertThat(contrat1).isEqualTo(contrat2);
        contrat2.setId(2L);
        assertThat(contrat1).isNotEqualTo(contrat2);
        contrat1.setId(null);
        assertThat(contrat1).isNotEqualTo(contrat2);
    }
}
