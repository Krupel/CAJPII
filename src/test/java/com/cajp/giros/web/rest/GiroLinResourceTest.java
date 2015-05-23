package com.cajp.giros.web.rest;

import com.cajp.giros.Application;
import com.cajp.giros.domain.GiroLin;
import com.cajp.giros.repository.GiroLinRepository;

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
 * Test class for the GiroLinResource REST controller.
 *
 * @see GiroLinResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class GiroLinResourceTest {

    private static final String DEFAULT_OBSERVACOES = "SAMPLE_TEXT";
    private static final String UPDATED_OBSERVACOES = "UPDATED_TEXT";

    @Inject
    private GiroLinRepository giroLinRepository;

    private MockMvc restGiroLinMockMvc;

    private GiroLin giroLin;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        GiroLinResource giroLinResource = new GiroLinResource();
        ReflectionTestUtils.setField(giroLinResource, "giroLinRepository", giroLinRepository);
        this.restGiroLinMockMvc = MockMvcBuilders.standaloneSetup(giroLinResource).build();
    }

    @Before
    public void initTest() {
        giroLin = new GiroLin();
        giroLin.setObservacoes(DEFAULT_OBSERVACOES);
    }

    @Test
    @Transactional
    public void createGiroLin() throws Exception {
        // Validate the database is empty
        assertThat(giroLinRepository.findAll()).hasSize(0);

        // Create the GiroLin
        restGiroLinMockMvc.perform(post("/api/giroLins")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(giroLin)))
                .andExpect(status().isCreated());

        // Validate the GiroLin in the database
        List<GiroLin> giroLins = giroLinRepository.findAll();
        assertThat(giroLins).hasSize(1);
        GiroLin testGiroLin = giroLins.iterator().next();
        assertThat(testGiroLin.getObservacoes()).isEqualTo(DEFAULT_OBSERVACOES);
    }

    @Test
    @Transactional
    public void getAllGiroLins() throws Exception {
        // Initialize the database
        giroLinRepository.saveAndFlush(giroLin);

        // Get all the giroLins
        restGiroLinMockMvc.perform(get("/api/giroLins"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].id").value(giroLin.getId().intValue()))
                .andExpect(jsonPath("$.[0].observacoes").value(DEFAULT_OBSERVACOES.toString()));
    }

    @Test
    @Transactional
    public void getGiroLin() throws Exception {
        // Initialize the database
        giroLinRepository.saveAndFlush(giroLin);

        // Get the giroLin
        restGiroLinMockMvc.perform(get("/api/giroLins/{id}", giroLin.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(giroLin.getId().intValue()))
            .andExpect(jsonPath("$.observacoes").value(DEFAULT_OBSERVACOES.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingGiroLin() throws Exception {
        // Get the giroLin
        restGiroLinMockMvc.perform(get("/api/giroLins/{id}", 1L))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGiroLin() throws Exception {
        // Initialize the database
        giroLinRepository.saveAndFlush(giroLin);

        // Update the giroLin
        giroLin.setObservacoes(UPDATED_OBSERVACOES);
        restGiroLinMockMvc.perform(put("/api/giroLins")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(giroLin)))
                .andExpect(status().isOk());

        // Validate the GiroLin in the database
        List<GiroLin> giroLins = giroLinRepository.findAll();
        assertThat(giroLins).hasSize(1);
        GiroLin testGiroLin = giroLins.iterator().next();
        assertThat(testGiroLin.getObservacoes()).isEqualTo(UPDATED_OBSERVACOES);
    }

    @Test
    @Transactional
    public void deleteGiroLin() throws Exception {
        // Initialize the database
        giroLinRepository.saveAndFlush(giroLin);

        // Get the giroLin
        restGiroLinMockMvc.perform(delete("/api/giroLins/{id}", giroLin.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<GiroLin> giroLins = giroLinRepository.findAll();
        assertThat(giroLins).hasSize(0);
    }
}
