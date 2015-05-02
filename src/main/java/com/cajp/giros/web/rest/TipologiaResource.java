package com.cajp.giros.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.cajp.giros.domain.Tipologia;
import com.cajp.giros.repository.TipologiaRepository;
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
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Tipologia.
 */
@RestController
@RequestMapping("/api")
public class TipologiaResource {

    private final Logger log = LoggerFactory.getLogger(TipologiaResource.class);

    @Inject
    private TipologiaRepository tipologiaRepository;

    /**
     * POST  /tipologias -> Create a new tipologia.
     */
    @RequestMapping(value = "/tipologias",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> create(@Valid @RequestBody Tipologia tipologia) throws URISyntaxException {
        log.debug("REST request to save Tipologia : {}", tipologia);
        if (tipologia.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new tipologia cannot already have an ID").build();
        }
        tipologiaRepository.save(tipologia);
        return ResponseEntity.created(new URI("/api/tipologias/" + tipologia.getId())).build();
    }

    /**
     * PUT  /tipologias -> Updates an existing tipologia.
     */
    @RequestMapping(value = "/tipologias",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> update(@Valid @RequestBody Tipologia tipologia) throws URISyntaxException {
        log.debug("REST request to update Tipologia : {}", tipologia);
        if (tipologia.getId() == null) {
            return create(tipologia);
        }
        tipologiaRepository.save(tipologia);
        return ResponseEntity.ok().build();
    }

    /**
     * GET  /tipologias -> get all the tipologias.
     */
    @RequestMapping(value = "/tipologias",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Tipologia>> getAll(@RequestParam(value = "page" , required = false) Integer offset,
                                  @RequestParam(value = "per_page", required = false) Integer limit)
        throws URISyntaxException {
        Page<Tipologia> page = tipologiaRepository.findAll(PaginationUtil.generatePageRequest(offset, limit));
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/tipologias", offset, limit);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /tipologias/:id -> get the "id" tipologia.
     */
    @RequestMapping(value = "/tipologias/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Tipologia> get(@PathVariable Long id) {
        log.debug("REST request to get Tipologia : {}", id);
        return Optional.ofNullable(tipologiaRepository.findOne(id))
            .map(tipologia -> new ResponseEntity<>(
                tipologia,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /tipologias/:id -> delete the "id" tipologia.
     */
    @RequestMapping(value = "/tipologias/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete Tipologia : {}", id);
        tipologiaRepository.delete(id);
    }
}
