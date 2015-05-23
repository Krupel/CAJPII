package com.cajp.giros.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.cajp.giros.domain.Tecnico;
import com.cajp.giros.repository.TecnicoRepository;
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
 * REST controller for managing Tecnico.
 */
@RestController
@RequestMapping("/api")
public class TecnicoResource {

    private final Logger log = LoggerFactory.getLogger(TecnicoResource.class);

    @Inject
    private TecnicoRepository tecnicoRepository;

    /**
     * POST  /tecnicos -> Create a new tecnico.
     */
    @RequestMapping(value = "/tecnicos",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> create(@RequestBody Tecnico tecnico) throws URISyntaxException {
        log.debug("REST request to save Tecnico : {}", tecnico);
        if (tecnico.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new tecnico cannot already have an ID").build();
        }
        tecnicoRepository.save(tecnico);
        return ResponseEntity.created(new URI("/api/tecnicos/" + tecnico.getId())).build();
    }

    /**
     * PUT  /tecnicos -> Updates an existing tecnico.
     */
    @RequestMapping(value = "/tecnicos",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> update(@RequestBody Tecnico tecnico) throws URISyntaxException {
        log.debug("REST request to update Tecnico : {}", tecnico);
        if (tecnico.getId() == null) {
            return create(tecnico);
        }
        tecnicoRepository.save(tecnico);
        return ResponseEntity.ok().build();
    }

    /**
     * GET  /tecnicos -> get all the tecnicos.
     */
    @RequestMapping(value = "/tecnicos",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Tecnico>> getAll(@RequestParam(value = "page" , required = false) Integer offset,
                                  @RequestParam(value = "per_page", required = false) Integer limit)
        throws URISyntaxException {
        Page<Tecnico> page = tecnicoRepository.findAll(PaginationUtil.generatePageRequest(offset, limit));
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/tecnicos", offset, limit);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /tecnicos/:id -> get the "id" tecnico.
     */
    @RequestMapping(value = "/tecnicos/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Tecnico> get(@PathVariable Long id) {
        log.debug("REST request to get Tecnico : {}", id);
        return Optional.ofNullable(tecnicoRepository.findOne(id))
            .map(tecnico -> new ResponseEntity<>(
                tecnico,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /tecnicos/:id -> delete the "id" tecnico.
     */
    @RequestMapping(value = "/tecnicos/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete Tecnico : {}", id);
        tecnicoRepository.delete(id);
    }
}
