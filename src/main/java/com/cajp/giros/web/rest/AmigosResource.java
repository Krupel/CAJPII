package com.cajp.giros.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.cajp.giros.domain.Amigos;
import com.cajp.giros.repository.AmigosRepository;
import com.cajp.giros.web.rest.util.PaginationUtil;
import org.joda.time.DateTime;
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
 * REST controller for managing Amigos.
 */
@RestController
@RequestMapping("/api")
public class AmigosResource {

    private final Logger log = LoggerFactory.getLogger(AmigosResource.class);

    @Inject
    private AmigosRepository amigosRepository;

    /**
     * POST  /amigoss -> Create a new amigos.
     */
    @RequestMapping(value = "/amigoss",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> create(@RequestBody Amigos amigos) throws URISyntaxException {
        log.debug("REST request to save Amigos : {}", amigos);
        if (amigos.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new amigos cannot already have an ID").build();
        }
        amigosRepository.save(amigos);
        return ResponseEntity.created(new URI("/api/amigoss/" + amigos.getId())).build();
    }

    /**
     * PUT  /amigoss -> Updates an existing amigos.
     */
    @RequestMapping(value = "/amigoss",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> update(@RequestBody Amigos amigos) throws URISyntaxException {
        log.debug("REST request to update Amigos : {}", amigos);
        if (amigos.getId() == null) {
            return create(amigos);
        }
        amigosRepository.save(amigos);
        return ResponseEntity.ok().build();
    }

    /**
     * GET  /amigoss -> get all the amigoss.
     */
    @RequestMapping(value = "/amigoss",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Amigos>> getAll(@RequestParam(value = "page" , required = false) Integer offset,
                                  @RequestParam(value = "per_page", required = false) Integer limit)
        throws URISyntaxException {
        Page<Amigos> page = amigosRepository.findAll(PaginationUtil.generatePageRequest(offset, limit));
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/amigoss", offset, limit);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /amigoss -> get all the amigoss.
     */
    @RequestMapping(value = "/amigossDate",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Amigos>> getAmigoByDate(@RequestParam(value = "date" , required = false) DateTime date,
                                               @RequestParam(value = "toDate", required = false) DateTime toDate)
        throws URISyntaxException {
        log.debug("REST request to getAmigoByDate {}", toDate);
        return null;
    }

    /**
     * GET  /amigoss/:id -> get the "id" amigos.
     */
    @RequestMapping(value = "/amigoss/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Amigos> get(@PathVariable Long id) {
        log.debug("REST request to get Amigos : {}", id);
        return Optional.ofNullable(amigosRepository.findOne(id))
            .map(amigos -> new ResponseEntity<>(
                amigos,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /amigoss/:id -> delete the "id" amigos.
     */
    @RequestMapping(value = "/amigoss/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete Amigos : {}", id);
        amigosRepository.delete(id);
    }
}
