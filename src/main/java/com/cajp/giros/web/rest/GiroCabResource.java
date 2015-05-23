package com.cajp.giros.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.cajp.giros.domain.GiroCab;
import com.cajp.giros.repository.GiroCabRepository;
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
 * REST controller for managing GiroCab.
 */
@RestController
@RequestMapping("/api")
public class GiroCabResource {

    private final Logger log = LoggerFactory.getLogger(GiroCabResource.class);

    @Inject
    private GiroCabRepository giroCabRepository;

    /**
     * POST  /giroCabs -> Create a new giroCab.
     */
    @RequestMapping(value = "/giroCabs",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> create(@RequestBody GiroCab giroCab) throws URISyntaxException {
        log.debug("REST request to save GiroCab : {}", giroCab);
        if (giroCab.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new giroCab cannot already have an ID").build();
        }
        giroCabRepository.save(giroCab);
        return ResponseEntity.created(new URI("/api/giroCabs/" + giroCab.getId())).build();
    }

    /**
     * PUT  /giroCabs -> Updates an existing giroCab.
     */
    @RequestMapping(value = "/giroCabs",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> update(@RequestBody GiroCab giroCab) throws URISyntaxException {
        log.debug("REST request to update GiroCab : {}", giroCab);
        if (giroCab.getId() == null) {
            return create(giroCab);
        }
        giroCabRepository.save(giroCab);
        return ResponseEntity.ok().build();
    }

    /**
     * GET  /giroCabs -> get all the giroCabs.
     */
    @RequestMapping(value = "/giroCabs",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<GiroCab>> getAll(@RequestParam(value = "page" , required = false) Integer offset,
                                  @RequestParam(value = "per_page", required = false) Integer limit)
        throws URISyntaxException {
        Page<GiroCab> page = giroCabRepository.findAll(PaginationUtil.generatePageRequest(offset, limit));
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/giroCabs", offset, limit);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /giroCabs/:id -> get the "id" giroCab.
     */
    @RequestMapping(value = "/giroCabs/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<GiroCab> get(@PathVariable Long id) {
        log.debug("REST request to get GiroCab : {}", id);
        return Optional.ofNullable(giroCabRepository.findOne(id))
            .map(giroCab -> new ResponseEntity<>(
                giroCab,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /giroCabs/:id -> delete the "id" giroCab.
     */
    @RequestMapping(value = "/giroCabs/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete GiroCab : {}", id);
        giroCabRepository.delete(id);
    }
}
