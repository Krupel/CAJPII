package com.cajp.giros.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.cajp.giros.domain.Funcionario;
import com.cajp.giros.repository.FuncionarioRepository;
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
 * REST controller for managing Funcionario.
 */
@RestController
@RequestMapping("/api")
public class FuncionarioResource {

    private final Logger log = LoggerFactory.getLogger(FuncionarioResource.class);

    @Inject
    private FuncionarioRepository funcionarioRepository;

    /**
     * POST  /funcionarios -> Create a new funcionario.
     */
    @RequestMapping(value = "/funcionarios",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> create(@RequestBody Funcionario funcionario) throws URISyntaxException {
        log.debug("REST request to save Funcionario : {}", funcionario);
        if (funcionario.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new funcionario cannot already have an ID").build();
        }
        funcionarioRepository.save(funcionario);
        return ResponseEntity.created(new URI("/api/funcionarios/" + funcionario.getId())).build();
    }

    /**
     * PUT  /funcionarios -> Updates an existing funcionario.
     */
    @RequestMapping(value = "/funcionarios",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> update(@RequestBody Funcionario funcionario) throws URISyntaxException {
        log.debug("REST request to update Funcionario : {}", funcionario);
        if (funcionario.getId() == null) {
            return create(funcionario);
        }
        funcionarioRepository.save(funcionario);
        return ResponseEntity.ok().build();
    }

    /**
     * GET  /funcionarios -> get all the funcionarios.
     */
    @RequestMapping(value = "/funcionarios",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Funcionario>> getAll(@RequestParam(value = "page" , required = false) Integer offset,
                                  @RequestParam(value = "per_page", required = false) Integer limit)
        throws URISyntaxException {
        Page<Funcionario> page = funcionarioRepository.findAll(PaginationUtil.generatePageRequest(offset, limit));
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/funcionarios", offset, limit);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /funcionarios/:id -> get the "id" funcionario.
     */
    @RequestMapping(value = "/funcionarios/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Funcionario> get(@PathVariable Long id) {
        log.debug("REST request to get Funcionario : {}", id);
        return Optional.ofNullable(funcionarioRepository.findOne(id))
            .map(funcionario -> new ResponseEntity<>(
                funcionario,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /funcionarios/:id -> delete the "id" funcionario.
     */
    @RequestMapping(value = "/funcionarios/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete Funcionario : {}", id);
        funcionarioRepository.delete(id);
    }
}
