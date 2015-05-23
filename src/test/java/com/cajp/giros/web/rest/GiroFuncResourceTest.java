package com.cajp.giros.web.rest;

import com.cajp.giros.Application;
import com.cajp.giros.domain.GiroFunc;
import com.cajp.giros.repository.GiroFuncRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the GiroFuncResource REST controller.
 *
 * @see GiroFuncResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class GiroFuncResourceTest {


    @Inject
    private GiroFuncRepository giroFuncRepository;

    private MockMvc restGiroFuncMockMvc;

    private GiroFunc giroFunc;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        GiroFuncResource giroFuncResource = new GiroFuncResource();
        ReflectionTestUtils.setField(giroFuncResource, "giroFuncRepository", giroFuncRepository);
        this.restGiroFuncMockMvc = MockMvcBuilders.standaloneSetup(giroFuncResource).build();
    }

    @Before
    public void initTest() {
        giroFunc = new GiroFunc();
    }

    @Test
    @Transactional
    public void createGiroFunc() throws Exception {
        // Validate the database is empty
        assertThat(giroFuncRepository.findAll()).hasSize(0);

        // Create the GiroFunc
        restGiroFuncMockMvc.perform(post("/api/giroFuncs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(giroFunc)))
                .andExpect(status().isCreated());

        // Validate the GiroFunc in the database
        List<GiroFunc> giroFuncs = giroFuncRepository.findAll();
        assertThat(giroFuncs).hasSize(1);
        GiroFunc testGiroFunc = giroFuncs.iterator().next();
    }

    @Test
    @Transactional
    public void getAllGiroFuncs() throws Exception {
        // Initialize the database
        giroFuncRepository.saveAndFlush(giroFunc);

        // Get all the giroFuncs
        restGiroFuncMockMvc.perform(get("/api/giroFuncs"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].id").value(giroFunc.getId().intValue()));
    }

    @Test
    @Transactional
    public void getGiroFunc() throws Exception {
        // Initialize the database
        giroFuncRepository.saveAndFlush(giroFunc);

        // Get the giroFunc
        restGiroFuncMockMvc.perform(get("/api/giroFuncs/{id}", giroFunc.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(giroFunc.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingGiroFunc() throws Exception {
        // Get the giroFunc
        restGiroFuncMockMvc.perform(get("/api/giroFuncs/{id}", 1L))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGiroFunc() throws Exception {
        // Initialize the database
        giroFuncRepository.saveAndFlush(giroFunc);

        // Update the giroFunc
        restGiroFuncMockMvc.perform(put("/api/giroFuncs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(giroFunc)))
                .andExpect(status().isOk());

        // Validate the GiroFunc in the database
        List<GiroFunc> giroFuncs = giroFuncRepository.findAll();
        assertThat(giroFuncs).hasSize(1);
        GiroFunc testGiroFunc = giroFuncs.iterator().next();
    }

    @Test
    @Transactional
    public void deleteGiroFunc() throws Exception {
        // Initialize the database
        giroFuncRepository.saveAndFlush(giroFunc);

        // Get the giroFunc
        restGiroFuncMockMvc.perform(delete("/api/giroFuncs/{id}", giroFunc.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<GiroFunc> giroFuncs = giroFuncRepository.findAll();
        assertThat(giroFuncs).hasSize(0);
    }
}
