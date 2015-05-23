package com.cajp.giros.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.cajp.giros.domain.GiroFunc;
import com.cajp.giros.repository.GiroFuncRepository;
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
 * REST controller for managing GiroFunc.
 */
@RestController
@RequestMapping("/api")
public class GiroFuncResource {

    private final Logger log = LoggerFactory.getLogger(GiroFuncResource.class);

    @Inject
    private GiroFuncRepository giroFuncRepository;

    /**
     * POST  /giroFuncs -> Create a new giroFunc.
     */
    @RequestMapping(value = "/giroFuncs",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> create(@RequestBody GiroFunc giroFunc) throws URISyntaxException {
        log.debug("REST request to save GiroFunc : {}", giroFunc);
        if (giroFunc.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new giroFunc cannot already have an ID").build();
        }
        giroFuncRepository.save(giroFunc);
        return ResponseEntity.created(new URI("/api/giroFuncs/" + giroFunc.getId())).build();
    }

    /**
     * PUT  /giroFuncs -> Updates an existing giroFunc.
     */
    @RequestMapping(value = "/giroFuncs",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> update(@RequestBody GiroFunc giroFunc) throws URISyntaxException {
        log.debug("REST request to update GiroFunc : {}", giroFunc);
        if (giroFunc.getId() == null) {
            return create(giroFunc);
        }
        giroFuncRepository.save(giroFunc);
        return ResponseEntity.ok().build();
    }

    /**
     * GET  /giroFuncs -> get all the giroFuncs.
     */
    @RequestMapping(value = "/giroFuncs",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<GiroFunc>> getAll(@RequestParam(value = "page" , required = false) Integer offset,
                                  @RequestParam(value = "per_page", required = false) Integer limit)
        throws URISyntaxException {
        Page<GiroFunc> page = giroFuncRepository.findAll(PaginationUtil.generatePageRequest(offset, limit));
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/giroFuncs", offset, limit);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /giroFuncs/:id -> get the "id" giroFunc.
     */
    @RequestMapping(value = "/giroFuncs/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<GiroFunc> get(@PathVariable Long id) {
        log.debug("REST request to get GiroFunc : {}", id);
        return Optional.ofNullable(giroFuncRepository.findOne(id))
            .map(giroFunc -> new ResponseEntity<>(
                giroFunc,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /giroFuncs/:id -> delete the "id" giroFunc.
     */
    @RequestMapping(value = "/giroFuncs/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete GiroFunc : {}", id);
        giroFuncRepository.delete(id);
    }
}
