package com.cajp.giros.web.rest;

import com.cajp.giros.Application;
import com.cajp.giros.domain.GirosCab;
import com.cajp.giros.repository.GirosCabRepository;

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
import org.joda.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the GirosCabResource REST controller.
 *
 * @see GirosCabResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class GirosCabResourceTest {


    private static final LocalDate DEFAULT_DATA = new LocalDate(0L);
    private static final LocalDate UPDATED_DATA = new LocalDate();
    private static final String DEFAULT_OBSERVACOES = "SAMPLE_TEXT";
    private static final String UPDATED_OBSERVACOES = "UPDATED_TEXT";

    @Inject
    private GirosCabRepository girosCabRepository;

    private MockMvc restGirosCabMockMvc;

    private GirosCab girosCab;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        GirosCabResource girosCabResource = new GirosCabResource();
        ReflectionTestUtils.setField(girosCabResource, "girosCabRepository", girosCabRepository);
        this.restGirosCabMockMvc = MockMvcBuilders.standaloneSetup(girosCabResource).build();
    }

    @Before
    public void initTest() {
        girosCab = new GirosCab();
        girosCab.setData(DEFAULT_DATA);
        girosCab.setObservacoes(DEFAULT_OBSERVACOES);
    }

    @Test
    @Transactional
    public void createGirosCab() throws Exception {
        // Validate the database is empty
        assertThat(girosCabRepository.findAll()).hasSize(0);

        // Create the GirosCab
        restGirosCabMockMvc.perform(post("/api/girosCabs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(girosCab)))
                .andExpect(status().isCreated());

        // Validate the GirosCab in the database
        List<GirosCab> girosCabs = girosCabRepository.findAll();
        assertThat(girosCabs).hasSize(1);
        GirosCab testGirosCab = girosCabs.iterator().next();
        assertThat(testGirosCab.getData()).isEqualTo(DEFAULT_DATA);
        assertThat(testGirosCab.getObservacoes()).isEqualTo(DEFAULT_OBSERVACOES);
    }

    @Test
    @Transactional
    public void getAllGirosCabs() throws Exception {
        // Initialize the database
        girosCabRepository.saveAndFlush(girosCab);

        // Get all the girosCabs
        restGirosCabMockMvc.perform(get("/api/girosCabs"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].id").value(girosCab.getId().intValue()))
                .andExpect(jsonPath("$.[0].data").value(DEFAULT_DATA.toString()))
                .andExpect(jsonPath("$.[0].observacoes").value(DEFAULT_OBSERVACOES.toString()));
    }

    @Test
    @Transactional
    public void getGirosCab() throws Exception {
        // Initialize the database
        girosCabRepository.saveAndFlush(girosCab);

        // Get the girosCab
        restGirosCabMockMvc.perform(get("/api/girosCabs/{id}", girosCab.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(girosCab.getId().intValue()))
            .andExpect(jsonPath("$.data").value(DEFAULT_DATA.toString()))
            .andExpect(jsonPath("$.observacoes").value(DEFAULT_OBSERVACOES.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingGirosCab() throws Exception {
        // Get the girosCab
        restGirosCabMockMvc.perform(get("/api/girosCabs/{id}", 1L))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGirosCab() throws Exception {
        // Initialize the database
        girosCabRepository.saveAndFlush(girosCab);

        // Update the girosCab
        girosCab.setData(UPDATED_DATA);
        girosCab.setObservacoes(UPDATED_OBSERVACOES);
        restGirosCabMockMvc.perform(put("/api/girosCabs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(girosCab)))
                .andExpect(status().isOk());

        // Validate the GirosCab in the database
        List<GirosCab> girosCabs = girosCabRepository.findAll();
        assertThat(girosCabs).hasSize(1);
        GirosCab testGirosCab = girosCabs.iterator().next();
        assertThat(testGirosCab.getData()).isEqualTo(UPDATED_DATA);
        assertThat(testGirosCab.getObservacoes()).isEqualTo(UPDATED_OBSERVACOES);
    }

    @Test
    @Transactional
    public void deleteGirosCab() throws Exception {
        // Initialize the database
        girosCabRepository.saveAndFlush(girosCab);

        // Get the girosCab
        restGirosCabMockMvc.perform(delete("/api/girosCabs/{id}", girosCab.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<GirosCab> girosCabs = girosCabRepository.findAll();
        assertThat(girosCabs).hasSize(0);
    }
}
