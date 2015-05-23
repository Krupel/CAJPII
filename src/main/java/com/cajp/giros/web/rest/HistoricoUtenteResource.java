package com.cajp.giros.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.cajp.giros.domain.HistoricoUtente;
import com.cajp.giros.repository.HistoricoUtenteRepository;
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
 * REST controller for managing HistoricoUtente.
 */
@RestController
@RequestMapping("/api")
public class HistoricoUtenteResource {

    private final Logger log = LoggerFactory.getLogger(HistoricoUtenteResource.class);

    @Inject
    private HistoricoUtenteRepository historicoUtenteRepository;

    /**
     * POST  /historicoUtentes -> Create a new historicoUtente.
     */
    @RequestMapping(value = "/historicoUtentes",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> create(@RequestBody HistoricoUtente historicoUtente) throws URISyntaxException {
        log.debug("REST request to save HistoricoUtente : {}", historicoUtente);
        if (historicoUtente.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new historicoUtente cannot already have an ID").build();
        }
        historicoUtenteRepository.save(historicoUtente);
        return ResponseEntity.created(new URI("/api/historicoUtentes/" + historicoUtente.getId())).build();
    }

    /**
     * PUT  /historicoUtentes -> Updates an existing historicoUtente.
     */
    @RequestMapping(value = "/historicoUtentes",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> update(@RequestBody HistoricoUtente historicoUtente) throws URISyntaxException {
        log.debug("REST request to update HistoricoUtente : {}", historicoUtente);
        if (historicoUtente.getId() == null) {
            return create(historicoUtente);
        }
        historicoUtenteRepository.save(historicoUtente);
        return ResponseEntity.ok().build();
    }

    /**
     * GET  /historicoUtentes -> get all the historicoUtentes.
     */
    @RequestMapping(value = "/historicoUtentes",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<HistoricoUtente>> getAll(@RequestParam(value = "page" , required = false) Integer offset,
                                  @RequestParam(value = "per_page", required = false) Integer limit)
        throws URISyntaxException {
        Page<HistoricoUtente> page = historicoUtenteRepository.findAll(PaginationUtil.generatePageRequest(offset, limit));
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/historicoUtentes", offset, limit);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /historicoUtentes/:id -> get the "id" historicoUtente.
     */
    @RequestMapping(value = "/historicoUtentes/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<HistoricoUtente> get(@PathVariable Long id) {
        log.debug("REST request to get HistoricoUtente : {}", id);
        return Optional.ofNullable(historicoUtenteRepository.findOne(id))
            .map(historicoUtente -> new ResponseEntity<>(
                historicoUtente,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /historicoUtentes/:id -> delete the "id" historicoUtente.
     */
    @RequestMapping(value = "/historicoUtentes/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete HistoricoUtente : {}", id);
        historicoUtenteRepository.delete(id);
    }
}
