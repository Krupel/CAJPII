package com.cajp.giros.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.cajp.giros.domain.Utente;
import com.cajp.giros.repository.UtenteRepository;
import com.cajp.giros.web.rest.util.PaginationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Utente.
 */
@RestController
@RequestMapping("/api")
public class UtenteResource {

    private final Logger log = LoggerFactory.getLogger(UtenteResource.class);

    @Inject
    private UtenteRepository utenteRepository;

    /**
     * POST  /utentes -> Create a new utente.
     */
    @RequestMapping(value = "/utentes",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> create(@RequestBody Utente utente) throws URISyntaxException {
        log.debug("REST request to save Utente : {}", utente);
        if (utente.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new utente cannot already have an ID").build();
        }
        utenteRepository.save(utente);
        return ResponseEntity.created(new URI("/api/utentes/" + utente.getId())).build();
    }

    /**
     * PUT  /utentes -> Updates an existing utente.
     */
    @RequestMapping(value = "/utentes",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> update(@RequestBody Utente utente) throws URISyntaxException {
        log.debug("REST request to update Utente : {}", utente);
        if (utente.getId() == null) {
            return create(utente);
        }
        utenteRepository.save(utente);
        return ResponseEntity.ok().build();
    }

    /**
     * GET  /utentes -> get all the utentes.
     */
    @RequestMapping(value = "/utentes",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Utente>> getAll(@RequestParam(value = "page" , required = false) Integer offset,
                                  @RequestParam(value = "per_page", required = false) Integer limit)
        throws URISyntaxException {
        Page<Utente> page = utenteRepository.findAll(PaginationUtil.generatePageRequest(offset, limit));
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/utentes", offset, limit);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /utentes/:id -> get the "id" utente.
     */
    @RequestMapping(value = "/utentes/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Utente> get(@PathVariable Long id) {
        log.debug("REST request to get Utente : {}", id);
        return Optional.ofNullable(utenteRepository.findOne(id))
            .map(utente -> new ResponseEntity<>(
                utente,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /utentes/:id -> delete the "id" utente.
     */
    @RequestMapping(value = "/utentes/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete Utente : {}", id);
        utenteRepository.delete(id);
    }
}
