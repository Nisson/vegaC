package com.veganet.api.web.rest;

import com.veganet.api.domain.Contrat;
import com.veganet.api.domain.RegleCommission;
import com.veganet.api.domain.Transaction;
import com.veganet.api.repository.ContratRepository;
import com.veganet.api.repository.RegleCommissionRepository;
import com.veganet.api.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * REST controller for managing {@link com.veganet.api.domain.Contrat}.
 */

@RestController
@RequestMapping("/api")
public class ContratResource {

    private final Logger log = LoggerFactory.getLogger(ContratResource.class);

    private static final String ENTITY_NAME = "contrat";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ContratRepository contratRepository;
    private  RegleCommissionRepository regleRepository;

    public ContratResource(ContratRepository contratRepository,RegleCommissionRepository regleRepository) {
        this.contratRepository = contratRepository;
        this.regleRepository=regleRepository;
    }

    /**
     * {@code POST  /contrats} : Create a new contrat.
     *
     * @param contrat the contrat to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new contrat, or with status {@code 400 (Bad Request)} if the contrat has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/contrats")
    public ResponseEntity<Contrat> createContrat(@RequestBody Contrat contrat) throws URISyntaxException {
        log.debug("REST request to save Contrat : {}", contrat);
        if (contrat.getId() != null) {
            throw new BadRequestAlertException("A new contrat cannot already have an ID", ENTITY_NAME, "idexists");
        }

        List<RegleCommission> regleCommissions = regleRepository.saveAll(contrat.getRegleCommissions());
        System.out.println("ICI"+regleCommissions);

        Contrat result = contratRepository.save(contrat);
        for(RegleCommission r:regleCommissions)
            r.setContrat(result);
        regleRepository.saveAll(regleCommissions);
        return ResponseEntity.created(new URI("/api/contrats/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /contrats} : Updates an existing contrat.
     *
     * @param contrat the contrat to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated contrat,
     * or with status {@code 400 (Bad Request)} if the contrat is not valid,
     * or with status {@code 500 (Internal Server Error)} if the contrat couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/contrats")
    public ResponseEntity<Contrat> updateContrat(@RequestBody Contrat contrat) throws URISyntaxException {
        log.debug("REST request to update Contrat : {}", contrat);
        if (contrat.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Contrat result = contratRepository.save(contrat);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, contrat.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /contrats} : get all the contrats.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of contrats in body.
     */
    @GetMapping("/contrats")
    public List<Contrat> getAllContrats() {
        log.debug("REST request to get all Contrats");
        return contratRepository.findAll();
    }

    /**
     * {@code GET  /contrats/:id} : get the "id" contrat.
     *
     * @param id the id of the contrat to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the contrat, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/contrats/{id}")
    public ResponseEntity<Contrat> getContrat(@PathVariable Long id) {
        log.debug("REST request to get Contrat : {}", id);
        Optional<Contrat> contrat = contratRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(contrat);
    }

    /**
     * {@code DELETE  /contrats/:id} : delete the "id" contrat.
     *
     * @param id the id of the contrat to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/contrats/{id}")
    public ResponseEntity<Void> deleteContrat(@PathVariable Long id) {
        log.debug("REST request to delete Contrat : {}", id);
        contratRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
     
    
    
    //////////////////////////////////////////////////////////////////
    @GetMapping("/contrat/{id}")
    public List<Contrat> getListContrat (@PathVariable Long id){
    	
    	List<Contrat> contracts =contratRepository.findByProvider(id) ;
    	return contracts;
    	}
    ///////////////////////////////////////////////////////////////
    @RequestMapping(path="/contratByDate/{datedeb}/{datefin}/{id}", method = RequestMethod.GET)
    public List<Contrat> getListContratetProviderByDate(@PathVariable String datedeb,@PathVariable String datefin , @PathVariable Long id)
    {
    	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-");
    	System.out.println(datedeb);
    	LocalDate datedebb= LocalDate.parse(datedeb,formatter);
    	LocalDate datefinn= LocalDate.parse(datefin,formatter);
    	List<Contrat> contracts= contratRepository.findByDateDebDateFinByProvider(datedebb, datefinn, id);
    	System.out.println(contracts.get(0).getConvention().getTransactions());
    /*	Double s = (double) 0;
    	for (Transaction t:contracts.get(0).getConvention().getTransactions())
    	{
    	s= s+t.getTotalpaid();
    	System.out.println(s);
    	
    	}*/
		return contracts;
    }
    
    ///////////////////////////////////////////////////////////
    @RequestMapping(path="/contratByDate/{datedeb}/{datefin}", method = RequestMethod.GET)
    public List<Contrat> getListContratByDate(@PathVariable String datedeb,@PathVariable String datefin )
    {
    	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    	System.out.println(datedeb);
    	LocalDate datedebb= LocalDate.parse(datedeb,formatter);
    	LocalDate datefinn= LocalDate.parse(datefin,formatter);
    	List<Contrat> contracts= contratRepository.findByDateDebDateFin(datedebb, datefinn);
    	System.out.println(contracts.get(0).getConvention().getTransactions());
    
    	
    	
    	return contracts; }
		
    
    
    //////////////////////////////////////////////////////////////////
    
    @RequestMapping(path="/gainByDate/{datedeb}/{datefin}", method = RequestMethod.GET)
    public void getGainByDate(@PathVariable String datedeb,@PathVariable String datefin)
    {
    	
    	List<Contrat> contracts=getListContratByDate(datedeb,datefin);
    	
    	Map<LocalDate,Double> mymap=new HashMap<LocalDate,Double>() ;
    	Double CA=(double) 0;
    	for(int i=0;i<contracts.size();i++)
    	{
    		if(contracts.get(i).getTypeCommission().equals("FORFAITAIREPARPERIODE"))
    		{
    		if(contracts.get(i).getTypePeriode().equals("MENSUELLE"))
    		{
    			LocalDate datecontrat=contracts.get(i).getDatedeb();
    			
    			while(datecontrat.isBefore(contracts.get(i).getDatefin()))
    			{
    				mymap.put(datecontrat,contracts.get(i).getMontantCommission());
    				datecontrat.plusMonths(1);
    				
    			}
    		}
    		if(contracts.get(i).getTypePeriode().equals("TRIMESTRIELLE"))
    		{

    			LocalDate datecontrat=contracts.get(i).getDatedeb();
    			
    			while(datecontrat.isBefore(contracts.get(i).getDatefin()))
    			{
    				mymap.put(datecontrat,contracts.get(i).getMontantCommission());
    				datecontrat.plusMonths(3);
    				
    			}
    		}
    		if(contracts.get(i).getTypePeriode().equals("SEMESTRIELLE"))
    		{

    			LocalDate datecontrat=contracts.get(i).getDatedeb();
    			
    			while(datecontrat.isBefore(contracts.get(i).getDatefin()))
    			{
    				mymap.put(datecontrat,contracts.get(i).getMontantCommission());
    				datecontrat.plusMonths(6);
    				
    			}
    		}
    		if(contracts.get(i).getTypePeriode().equals("ANNUELLE"))
    		{

    			LocalDate datecontrat=contracts.get(i).getDatedeb();
    			
    			while(datecontrat.isBefore(contracts.get(i).getDatefin()))
    			{
    				mymap.put(datecontrat,contracts.get(i).getMontantCommission());
    				datecontrat.plusYears(1);
    				
    			}
    		}
    	}
    		if(contracts.get(i).getTypeCommission().equals("FORFAITAIREPARTRANSACTION"))
    		{
    			
    			if(contracts.get(i).getTypePeriode().equals("MENSUELLE"))
    			{
    				LocalDate datecontrat=contracts.get(i).getDatedeb();
    				List<Transaction> transactions=(List<Transaction>) contracts.get(i).getConvention().getTransactions();
    			
    				while(datecontrat.isBefore(contracts.get(i).getDatefin()))
    				{   
    					for(int k=0;k<transactions.size();k++)
    					{
    				
    						if(transactions.get(k).getEnddate().isBefore(contracts.get(i).getDatefin()))
    						{	
    							CA=CA+contracts.get(i).getMontantCommission()*transactions.size();
    							mymap.put(datecontrat,CA);
    							datecontrat.plusMonths(1);
    				
    					
    						}
    				}
    			}
    		}
    			if(contracts.get(i).getTypePeriode().equals("TRIMESTRIELLE"))
    			{
    				LocalDate datecontrat=contracts.get(i).getDatedeb();
    				List<Transaction> transactions=(List<Transaction>) contracts.get(i).getConvention().getTransactions();
    			
    				while(datecontrat.isBefore(contracts.get(i).getDatefin()))
    				{   
    					for(int k=0;k<transactions.size();k++)
    					{
    				
    						if(transactions.get(k).getEnddate().isBefore(contracts.get(i).getDatefin()))
    						{	
    							CA=contracts.get(i).getMontantCommission()*transactions.size();
    							mymap.put(datecontrat,CA);
    							datecontrat.plusMonths(3);
    				
    					
    						}
    				}
    			}
    		}
    			if(contracts.get(i).getTypePeriode().equals("SEMESTRIELLE"))
    			{
    				LocalDate datecontrat=contracts.get(i).getDatedeb();
    				List<Transaction> transactions=(List<Transaction>) contracts.get(i).getConvention().getTransactions();
    			
    				while(datecontrat.isBefore(contracts.get(i).getDatefin()))
    				{   
    					for(int k=0;k<transactions.size();k++)
    					{
    				
    						if(transactions.get(k).getEnddate().isBefore(contracts.get(i).getDatefin()))
    						{	
    							CA=contracts.get(i).getMontantCommission()*transactions.size();
    							mymap.put(datecontrat,CA);
    							datecontrat.plusMonths(6);
    				
    					
    						}
    				}
    			}
    		}
    			if(contracts.get(i).getTypePeriode().equals("ANNUELLE"))
    			{
    				LocalDate datecontrat=contracts.get(i).getDatedeb();
    				List<Transaction> transactions=(List<Transaction>) contracts.get(i).getConvention().getTransactions();
    			
    				while(datecontrat.isBefore(contracts.get(i).getDatefin()))
    				{   
    					for(int k=0;k<transactions.size();k++)
    					{
    				
    						if(transactions.get(k).getEnddate().isBefore(contracts.get(i).getDatefin()))
    						{	
    							CA=contracts.get(i).getMontantCommission()*transactions.size();
    							mymap.put(datecontrat,CA);
    							datecontrat.plusYears(1);
    				
    					
    						}
    				}
    			}
    		}
    	}
    }
    	
    	
    }
    
    
    
    
    
    
    
    
    
}
