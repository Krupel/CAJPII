package com.cajp.giros.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.cajp.giros.domain.GirosFunc;
import com.cajp.giros.repository.GirosFuncRepository;
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
 * REST controller for managing GirosFunc.
 */
@RestController
@RequestMapping("/api")
public class GirosFuncResource {

    private final Logger log = LoggerFactory.getLogger(GirosFuncResource.class);

    @Inject
    private GirosFuncRepository girosFuncRepository;

    /**
     * POST  /girosFuncs -> Create a new girosFunc.
     */
    @RequestMapping(value = "/girosFuncs",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> create(@RequestBody GirosFunc girosFunc) throws URISyntaxException {
        log.debug("REST request to save GirosFunc : {}", girosFunc);
        if (girosFunc.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new girosFunc cannot already have an ID").build();
        }
        girosFuncRepository.save(girosFunc);
        return ResponseEntity.created(new URI("/api/girosFuncs/" + girosFunc.getId())).build();
    }

    /**
     * PUT  /girosFuncs -> Updates an existing girosFunc.
     */
    @RequestMapping(value = "/girosFuncs",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> update(@RequestBody GirosFunc girosFunc) throws URISyntaxException {
        log.debug("REST request to update GirosFunc : {}", girosFunc);
        if (girosFunc.getId() == null) {
            return create(girosFunc);
        }
        girosFuncRepository.save(girosFunc);
        return ResponseEntity.ok().build();
    }

    /**
     * GET  /girosFuncs -> get all the girosFuncs.
     */
    @RequestMapping(value = "/girosFuncs",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<GirosFunc>> getAll(@RequestParam(value = "page" , required = false) Integer offset,
                                  @RequestParam(value = "per_page", required = false) Integer limit)
        throws URISyntaxException {
        Page<GirosFunc> page = girosFuncRepository.findAll(PaginationUtil.generatePageRequest(offset, limit));
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/girosFuncs", offset, limit);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /girosFuncs/:id -> get the "id" girosFunc.
     */
    @RequestMapping(value = "/girosFuncs/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<GirosFunc> get(@PathVariable Long id) {
        log.debug("REST request to get GirosFunc : {}", id);
        return Optional.ofNullable(girosFuncRepository.findOne(id))
            .map(girosFunc -> new ResponseEntity<>(
                girosFunc,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /girosFuncs/:id -> delete the "id" girosFunc.
     */
    @RequestMapping(value = "/girosFuncs/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete GirosFunc : {}", id);
        girosFuncRepository.delete(id);
    }
}
