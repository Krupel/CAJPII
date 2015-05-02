package com.cajp.giros.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.cajp.giros.domain.GirosLin;
import com.cajp.giros.repository.GirosLinRepository;
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
 * REST controller for managing GirosLin.
 */
@RestController
@RequestMapping("/api")
public class GirosLinResource {

    private final Logger log = LoggerFactory.getLogger(GirosLinResource.class);

    @Inject
    private GirosLinRepository girosLinRepository;

    /**
     * POST  /girosLins -> Create a new girosLin.
     */
    @RequestMapping(value = "/girosLins",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> create(@RequestBody GirosLin girosLin) throws URISyntaxException {
        log.debug("REST request to save GirosLin : {}", girosLin);
        if (girosLin.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new girosLin cannot already have an ID").build();
        }
        girosLinRepository.save(girosLin);
        return ResponseEntity.created(new URI("/api/girosLins/" + girosLin.getId())).build();
    }

    /**
     * PUT  /girosLins -> Updates an existing girosLin.
     */
    @RequestMapping(value = "/girosLins",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> update(@RequestBody GirosLin girosLin) throws URISyntaxException {
        log.debug("REST request to update GirosLin : {}", girosLin);
        if (girosLin.getId() == null) {
            return create(girosLin);
        }
        girosLinRepository.save(girosLin);
        return ResponseEntity.ok().build();
    }

    /**
     * GET  /girosLins -> get all the girosLins.
     */
    @RequestMapping(value = "/girosLins",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<GirosLin>> getAll(@RequestParam(value = "page" , required = false) Integer offset,
                                  @RequestParam(value = "per_page", required = false) Integer limit)
        throws URISyntaxException {
        Page<GirosLin> page = girosLinRepository.findAll(PaginationUtil.generatePageRequest(offset, limit));
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/girosLins", offset, limit);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /girosLins/:id -> get the "id" girosLin.
     */
    @RequestMapping(value = "/girosLins/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<GirosLin> get(@PathVariable Long id) {
        log.debug("REST request to get GirosLin : {}", id);
        return Optional.ofNullable(girosLinRepository.findOne(id))
            .map(girosLin -> new ResponseEntity<>(
                girosLin,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /girosLins/:id -> delete the "id" girosLin.
     */
    @RequestMapping(value = "/girosLins/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete GirosLin : {}", id);
        girosLinRepository.delete(id);
    }
}
