package com.veganet.api.web.rest;

import com.veganet.api.PropApp;
import com.veganet.api.domain.Transaction;
import com.veganet.api.repository.TransactionRepository;
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
 * Integration tests for the {@Link TransactionResource} REST controller.
 */
@SpringBootTest(classes = PropApp.class)
public class TransactionResourceIT {

    private static final String DEFAULT_ACTIVATIONSTATUS = "AAAAAAAAAA";
    private static final String UPDATED_ACTIVATIONSTATUS = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    private static final Double DEFAULT_AMOUNTSUBSTRACTION = 1D;
    private static final Double UPDATED_AMOUNTSUBSTRACTION = 2D;

    private static final LocalDate DEFAULT_STARTDATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_STARTDATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_ENDDATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_ENDDATE = LocalDate.now(ZoneId.systemDefault());

    private static final Double DEFAULT_TOTALAMOUNT = 1D;
    private static final Double UPDATED_TOTALAMOUNT = 2D;

    private static final Double DEFAULT_TOTALPAID = 1D;
    private static final Double UPDATED_TOTALPAID = 2D;

    private static final Double DEFAULT_ADVANCEDAMOUNT = 1D;
    private static final Double UPDATED_ADVANCEDAMOUNT = 2D;

    @Autowired
    private TransactionRepository transactionRepository;

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

    private MockMvc restTransactionMockMvc;

    private Transaction transaction;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TransactionResource transactionResource = new TransactionResource(transactionRepository);
        this.restTransactionMockMvc = MockMvcBuilders.standaloneSetup(transactionResource)
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
    public static Transaction createEntity(EntityManager em) {
        Transaction transaction = new Transaction()
            .activationstatus(DEFAULT_ACTIVATIONSTATUS)
            .address(DEFAULT_ADDRESS)
            .amountsubstraction(DEFAULT_AMOUNTSUBSTRACTION)
            .startdate(DEFAULT_STARTDATE)
            .enddate(DEFAULT_ENDDATE)
            .totalamount(DEFAULT_TOTALAMOUNT)
            .totalpaid(DEFAULT_TOTALPAID)
            .advancedamount(DEFAULT_ADVANCEDAMOUNT);
        return transaction;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Transaction createUpdatedEntity(EntityManager em) {
        Transaction transaction = new Transaction()
            .activationstatus(UPDATED_ACTIVATIONSTATUS)
            .address(UPDATED_ADDRESS)
            .amountsubstraction(UPDATED_AMOUNTSUBSTRACTION)
            .startdate(UPDATED_STARTDATE)
            .enddate(UPDATED_ENDDATE)
            .totalamount(UPDATED_TOTALAMOUNT)
            .totalpaid(UPDATED_TOTALPAID)
            .advancedamount(UPDATED_ADVANCEDAMOUNT);
        return transaction;
    }

    @BeforeEach
    public void initTest() {
        transaction = createEntity(em);
    }

    @Test
    @Transactional
    public void createTransaction() throws Exception {
        int databaseSizeBeforeCreate = transactionRepository.findAll().size();

        // Create the Transaction
        restTransactionMockMvc.perform(post("/api/transactions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transaction)))
            .andExpect(status().isCreated());

        // Validate the Transaction in the database
        List<Transaction> transactionList = transactionRepository.findAll();
        assertThat(transactionList).hasSize(databaseSizeBeforeCreate + 1);
        Transaction testTransaction = transactionList.get(transactionList.size() - 1);
        assertThat(testTransaction.getActivationstatus()).isEqualTo(DEFAULT_ACTIVATIONSTATUS);
        assertThat(testTransaction.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testTransaction.getAmountsubstraction()).isEqualTo(DEFAULT_AMOUNTSUBSTRACTION);
        assertThat(testTransaction.getStartdate()).isEqualTo(DEFAULT_STARTDATE);
        assertThat(testTransaction.getEnddate()).isEqualTo(DEFAULT_ENDDATE);
        assertThat(testTransaction.getTotalamount()).isEqualTo(DEFAULT_TOTALAMOUNT);
        assertThat(testTransaction.getTotalpaid()).isEqualTo(DEFAULT_TOTALPAID);
        assertThat(testTransaction.getAdvancedamount()).isEqualTo(DEFAULT_ADVANCEDAMOUNT);
    }

    @Test
    @Transactional
    public void createTransactionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = transactionRepository.findAll().size();

        // Create the Transaction with an existing ID
        transaction.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTransactionMockMvc.perform(post("/api/transactions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transaction)))
            .andExpect(status().isBadRequest());

        // Validate the Transaction in the database
        List<Transaction> transactionList = transactionRepository.findAll();
        assertThat(transactionList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllTransactions() throws Exception {
        // Initialize the database
        transactionRepository.saveAndFlush(transaction);

        // Get all the transactionList
        restTransactionMockMvc.perform(get("/api/transactions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(transaction.getId().intValue())))
            .andExpect(jsonPath("$.[*].activationstatus").value(hasItem(DEFAULT_ACTIVATIONSTATUS.toString())))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS.toString())))
            .andExpect(jsonPath("$.[*].amountsubstraction").value(hasItem(DEFAULT_AMOUNTSUBSTRACTION.doubleValue())))
            .andExpect(jsonPath("$.[*].startdate").value(hasItem(DEFAULT_STARTDATE.toString())))
            .andExpect(jsonPath("$.[*].enddate").value(hasItem(DEFAULT_ENDDATE.toString())))
            .andExpect(jsonPath("$.[*].totalamount").value(hasItem(DEFAULT_TOTALAMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].totalpaid").value(hasItem(DEFAULT_TOTALPAID.doubleValue())))
            .andExpect(jsonPath("$.[*].advancedamount").value(hasItem(DEFAULT_ADVANCEDAMOUNT.doubleValue())));
    }
    
    @Test
    @Transactional
    public void getTransaction() throws Exception {
        // Initialize the database
        transactionRepository.saveAndFlush(transaction);

        // Get the transaction
        restTransactionMockMvc.perform(get("/api/transactions/{id}", transaction.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(transaction.getId().intValue()))
            .andExpect(jsonPath("$.activationstatus").value(DEFAULT_ACTIVATIONSTATUS.toString()))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS.toString()))
            .andExpect(jsonPath("$.amountsubstraction").value(DEFAULT_AMOUNTSUBSTRACTION.doubleValue()))
            .andExpect(jsonPath("$.startdate").value(DEFAULT_STARTDATE.toString()))
            .andExpect(jsonPath("$.enddate").value(DEFAULT_ENDDATE.toString()))
            .andExpect(jsonPath("$.totalamount").value(DEFAULT_TOTALAMOUNT.doubleValue()))
            .andExpect(jsonPath("$.totalpaid").value(DEFAULT_TOTALPAID.doubleValue()))
            .andExpect(jsonPath("$.advancedamount").value(DEFAULT_ADVANCEDAMOUNT.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingTransaction() throws Exception {
        // Get the transaction
        restTransactionMockMvc.perform(get("/api/transactions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTransaction() throws Exception {
        // Initialize the database
        transactionRepository.saveAndFlush(transaction);

        int databaseSizeBeforeUpdate = transactionRepository.findAll().size();

        // Update the transaction
        Transaction updatedTransaction = transactionRepository.findById(transaction.getId()).get();
        // Disconnect from session so that the updates on updatedTransaction are not directly saved in db
        em.detach(updatedTransaction);
        updatedTransaction
            .activationstatus(UPDATED_ACTIVATIONSTATUS)
            .address(UPDATED_ADDRESS)
            .amountsubstraction(UPDATED_AMOUNTSUBSTRACTION)
            .startdate(UPDATED_STARTDATE)
            .enddate(UPDATED_ENDDATE)
            .totalamount(UPDATED_TOTALAMOUNT)
            .totalpaid(UPDATED_TOTALPAID)
            .advancedamount(UPDATED_ADVANCEDAMOUNT);

        restTransactionMockMvc.perform(put("/api/transactions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTransaction)))
            .andExpect(status().isOk());

        // Validate the Transaction in the database
        List<Transaction> transactionList = transactionRepository.findAll();
        assertThat(transactionList).hasSize(databaseSizeBeforeUpdate);
        Transaction testTransaction = transactionList.get(transactionList.size() - 1);
        assertThat(testTransaction.getActivationstatus()).isEqualTo(UPDATED_ACTIVATIONSTATUS);
        assertThat(testTransaction.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testTransaction.getAmountsubstraction()).isEqualTo(UPDATED_AMOUNTSUBSTRACTION);
        assertThat(testTransaction.getStartdate()).isEqualTo(UPDATED_STARTDATE);
        assertThat(testTransaction.getEnddate()).isEqualTo(UPDATED_ENDDATE);
        assertThat(testTransaction.getTotalamount()).isEqualTo(UPDATED_TOTALAMOUNT);
        assertThat(testTransaction.getTotalpaid()).isEqualTo(UPDATED_TOTALPAID);
        assertThat(testTransaction.getAdvancedamount()).isEqualTo(UPDATED_ADVANCEDAMOUNT);
    }

    @Test
    @Transactional
    public void updateNonExistingTransaction() throws Exception {
        int databaseSizeBeforeUpdate = transactionRepository.findAll().size();

        // Create the Transaction

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTransactionMockMvc.perform(put("/api/transactions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transaction)))
            .andExpect(status().isBadRequest());

        // Validate the Transaction in the database
        List<Transaction> transactionList = transactionRepository.findAll();
        assertThat(transactionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTransaction() throws Exception {
        // Initialize the database
        transactionRepository.saveAndFlush(transaction);

        int databaseSizeBeforeDelete = transactionRepository.findAll().size();

        // Delete the transaction
        restTransactionMockMvc.perform(delete("/api/transactions/{id}", transaction.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Transaction> transactionList = transactionRepository.findAll();
        assertThat(transactionList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Transaction.class);
        Transaction transaction1 = new Transaction();
        transaction1.setId(1L);
        Transaction transaction2 = new Transaction();
        transaction2.setId(transaction1.getId());
        assertThat(transaction1).isEqualTo(transaction2);
        transaction2.setId(2L);
        assertThat(transaction1).isNotEqualTo(transaction2);
        transaction1.setId(null);
        assertThat(transaction1).isNotEqualTo(transaction2);
    }
}
