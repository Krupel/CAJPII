package com.cajp.giros.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.cajp.giros.domain.GiroLin;
import com.cajp.giros.repository.GiroLinRepository;
import com.cajp.giros.web.rest.util.PaginationUtil;
import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.persistence.TemporalType;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.logging.SimpleFormatter;

/**
 * REST controller for managing GiroLin.
 */
@RestController
@RequestMapping("/api")
public class GiroLinResource {

    private final Logger log = LoggerFactory.getLogger(GiroLinResource.class);

    @Inject
    private GiroLinRepository giroLinRepository;

    /**
     * POST  /giroLins -> Create a new giroLin.
     */
    @RequestMapping(value = "/giroLins",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> create(@RequestBody GiroLin giroLin) throws URISyntaxException {
        log.debug("REST request to save GiroLin : {}", giroLin);
        if (giroLin.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new giroLin cannot already have an ID").build();
        }
        giroLinRepository.save(giroLin);
        return ResponseEntity.created(new URI("/api/giroLins/" + giroLin.getId())).build();
    }

    /**
     * PUT  /giroLins -> Updates an existing giroLin.
     */
    @RequestMapping(value = "/giroLins",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> update(@RequestBody GiroLin giroLin) throws URISyntaxException {
        log.debug("REST request to update GiroLin : {}", giroLin);
        if (giroLin.getId() == null) {
            return create(giroLin);
        }
        giroLinRepository.save(giroLin);
        return ResponseEntity.ok().build();
    }

    /**
     * GET  /giroLins -> get all the giroLins.
     */
    @RequestMapping(value = "/giroLins",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<GiroLin>> getAll(@RequestParam(value = "page" , required = false) Integer offset,
                                  @RequestParam(value = "per_page", required = false) Integer limit)
        throws URISyntaxException {
        Page<GiroLin> page = giroLinRepository.findAll(PaginationUtil.generatePageRequest(offset, limit));
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/giroLins", offset, limit);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /resumobydatas -> .
     */
    @RequestMapping(value = "/resumobydatas",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<GiroLin>> getResumo(
        @RequestParam(value = "page", required = false) Integer offset,
        @RequestParam(value = "per_page", required = false) Integer limit,
        @RequestParam(value = "date_de", required = false) String date_de,
        @RequestParam(value = "date_ate", required = false) String date_ate)
        throws URISyntaxException, ParseException {
        Pageable r = PaginationUtil.generatePageRequest(offset, limit);
        LocalDate mydate_de = new LocalDate(date_de);
        LocalDate mydate_ate = new LocalDate(date_ate);
        Page<GiroLin> page = giroLinRepository.findResumo(mydate_de,mydate_ate,r);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/giroLins", offset, limit);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /giroLins/:id -> get the "id" giroLin.
     */
    @RequestMapping(value = "/giroLins/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<GiroLin> get(@PathVariable Long id) {
        log.debug("REST request to get GiroLin : {}", id);
        return Optional.ofNullable(giroLinRepository.findOne(id))
            .map(giroLin -> new ResponseEntity<>(
                giroLin,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /giroLins/:id -> delete the "id" giroLin.
     */
    @RequestMapping(value = "/giroLins/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete GiroLin : {}", id);
        giroLinRepository.delete(id);
    }
}
