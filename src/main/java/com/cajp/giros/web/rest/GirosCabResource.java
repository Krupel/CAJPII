package com.cajp.giros.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.cajp.giros.domain.GirosCab;
import com.cajp.giros.repository.GirosCabRepository;
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
 * REST controller for managing GirosCab.
 */
@RestController
@RequestMapping("/api")
public class GirosCabResource {

    private final Logger log = LoggerFactory.getLogger(GirosCabResource.class);

    @Inject
    private GirosCabRepository girosCabRepository;

    /**
     * POST  /girosCabs -> Create a new girosCab.
     */
    @RequestMapping(value = "/girosCabs",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> create(@Valid @RequestBody GirosCab girosCab) throws URISyntaxException {
        log.debug("REST request to save GirosCab : {}", girosCab);
        if (girosCab.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new girosCab cannot already have an ID").build();
        }
        girosCabRepository.save(girosCab);
        return ResponseEntity.created(new URI("/api/girosCabs/" + girosCab.getId())).build();
    }

    /**
     * PUT  /girosCabs -> Updates an existing girosCab.
     */
    @RequestMapping(value = "/girosCabs",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> update(@Valid @RequestBody GirosCab girosCab) throws URISyntaxException {
        log.debug("REST request to update GirosCab : {}", girosCab);
        if (girosCab.getId() == null) {
            return create(girosCab);
        }
        girosCabRepository.save(girosCab);
        return ResponseEntity.ok().build();
    }

    /**
     * GET  /girosCabs -> get all the girosCabs.
     */
    @RequestMapping(value = "/girosCabs",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<GirosCab>> getAll(@RequestParam(value = "page" , required = false) Integer offset,
                                  @RequestParam(value = "per_page", required = false) Integer limit)
        throws URISyntaxException {
        Page<GirosCab> page = girosCabRepository.findAll(PaginationUtil.generatePageRequest(offset, limit));
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/girosCabs", offset, limit);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /girosCabs/:id -> get the "id" girosCab.
     */
    @RequestMapping(value = "/girosCabs/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<GirosCab> get(@PathVariable Long id) {
        log.debug("REST request to get GirosCab : {}", id);
        return Optional.ofNullable(girosCabRepository.findOne(id))
            .map(girosCab -> new ResponseEntity<>(
                girosCab,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /girosCabs/:id -> delete the "id" girosCab.
     */
    @RequestMapping(value = "/girosCabs/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete GirosCab : {}", id);
        girosCabRepository.delete(id);
    }
}
