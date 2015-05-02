package com.cajp.giros.web.rest;

import com.cajp.giros.Application;
import com.cajp.giros.domain.GirosFunc;
import com.cajp.giros.repository.GirosFuncRepository;

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
 * Test class for the GirosFuncResource REST controller.
 *
 * @see GirosFuncResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class GirosFuncResourceTest {


    @Inject
    private GirosFuncRepository girosFuncRepository;

    private MockMvc restGirosFuncMockMvc;

    private GirosFunc girosFunc;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        GirosFuncResource girosFuncResource = new GirosFuncResource();
        ReflectionTestUtils.setField(girosFuncResource, "girosFuncRepository", girosFuncRepository);
        this.restGirosFuncMockMvc = MockMvcBuilders.standaloneSetup(girosFuncResource).build();
    }

    @Before
    public void initTest() {
        girosFunc = new GirosFunc();
    }

    @Test
    @Transactional
    public void createGirosFunc() throws Exception {
        // Validate the database is empty
        assertThat(girosFuncRepository.findAll()).hasSize(0);

        // Create the GirosFunc
        restGirosFuncMockMvc.perform(post("/api/girosFuncs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(girosFunc)))
                .andExpect(status().isCreated());

        // Validate the GirosFunc in the database
        List<GirosFunc> girosFuncs = girosFuncRepository.findAll();
        assertThat(girosFuncs).hasSize(1);
        GirosFunc testGirosFunc = girosFuncs.iterator().next();
    }

    @Test
    @Transactional
    public void getAllGirosFuncs() throws Exception {
        // Initialize the database
        girosFuncRepository.saveAndFlush(girosFunc);

        // Get all the girosFuncs
        restGirosFuncMockMvc.perform(get("/api/girosFuncs"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].id").value(girosFunc.getId().intValue()));
    }

    @Test
    @Transactional
    public void getGirosFunc() throws Exception {
        // Initialize the database
        girosFuncRepository.saveAndFlush(girosFunc);

        // Get the girosFunc
        restGirosFuncMockMvc.perform(get("/api/girosFuncs/{id}", girosFunc.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(girosFunc.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingGirosFunc() throws Exception {
        // Get the girosFunc
        restGirosFuncMockMvc.perform(get("/api/girosFuncs/{id}", 1L))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGirosFunc() throws Exception {
        // Initialize the database
        girosFuncRepository.saveAndFlush(girosFunc);

        // Update the girosFunc
        restGirosFuncMockMvc.perform(put("/api/girosFuncs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(girosFunc)))
                .andExpect(status().isOk());

        // Validate the GirosFunc in the database
        List<GirosFunc> girosFuncs = girosFuncRepository.findAll();
        assertThat(girosFuncs).hasSize(1);
        GirosFunc testGirosFunc = girosFuncs.iterator().next();
    }

    @Test
    @Transactional
    public void deleteGirosFunc() throws Exception {
        // Initialize the database
        girosFuncRepository.saveAndFlush(girosFunc);

        // Get the girosFunc
        restGirosFuncMockMvc.perform(delete("/api/girosFuncs/{id}", girosFunc.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<GirosFunc> girosFuncs = girosFuncRepository.findAll();
        assertThat(girosFuncs).hasSize(0);
    }
}
