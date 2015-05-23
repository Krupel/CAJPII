package com.cajp.giros.web.rest;

import com.cajp.giros.Application;
import com.cajp.giros.domain.GiroCab;
import com.cajp.giros.repository.GiroCabRepository;

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
 * Test class for the GiroCabResource REST controller.
 *
 * @see GiroCabResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class GiroCabResourceTest {


    private static final LocalDate DEFAULT_DATA = new LocalDate(0L);
    private static final LocalDate UPDATED_DATA = new LocalDate();
    private static final String DEFAULT_OBSERVACOES = "SAMPLE_TEXT";
    private static final String UPDATED_OBSERVACOES = "UPDATED_TEXT";

    @Inject
    private GiroCabRepository giroCabRepository;

    private MockMvc restGiroCabMockMvc;

    private GiroCab giroCab;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        GiroCabResource giroCabResource = new GiroCabResource();
        ReflectionTestUtils.setField(giroCabResource, "giroCabRepository", giroCabRepository);
        this.restGiroCabMockMvc = MockMvcBuilders.standaloneSetup(giroCabResource).build();
    }

    @Before
    public void initTest() {
        giroCab = new GiroCab();
        giroCab.setData(DEFAULT_DATA);
        giroCab.setObservacoes(DEFAULT_OBSERVACOES);
    }

    @Test
    @Transactional
    public void createGiroCab() throws Exception {
        // Validate the database is empty
        assertThat(giroCabRepository.findAll()).hasSize(0);

        // Create the GiroCab
        restGiroCabMockMvc.perform(post("/api/giroCabs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(giroCab)))
                .andExpect(status().isCreated());

        // Validate the GiroCab in the database
        List<GiroCab> giroCabs = giroCabRepository.findAll();
        assertThat(giroCabs).hasSize(1);
        GiroCab testGiroCab = giroCabs.iterator().next();
        assertThat(testGiroCab.getData()).isEqualTo(DEFAULT_DATA);
        assertThat(testGiroCab.getObservacoes()).isEqualTo(DEFAULT_OBSERVACOES);
    }

    @Test
    @Transactional
    public void getAllGiroCabs() throws Exception {
        // Initialize the database
        giroCabRepository.saveAndFlush(giroCab);

        // Get all the giroCabs
        restGiroCabMockMvc.perform(get("/api/giroCabs"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].id").value(giroCab.getId().intValue()))
                .andExpect(jsonPath("$.[0].data").value(DEFAULT_DATA.toString()))
                .andExpect(jsonPath("$.[0].observacoes").value(DEFAULT_OBSERVACOES.toString()));
    }

    @Test
    @Transactional
    public void getGiroCab() throws Exception {
        // Initialize the database
        giroCabRepository.saveAndFlush(giroCab);

        // Get the giroCab
        restGiroCabMockMvc.perform(get("/api/giroCabs/{id}", giroCab.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(giroCab.getId().intValue()))
            .andExpect(jsonPath("$.data").value(DEFAULT_DATA.toString()))
            .andExpect(jsonPath("$.observacoes").value(DEFAULT_OBSERVACOES.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingGiroCab() throws Exception {
        // Get the giroCab
        restGiroCabMockMvc.perform(get("/api/giroCabs/{id}", 1L))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGiroCab() throws Exception {
        // Initialize the database
        giroCabRepository.saveAndFlush(giroCab);

        // Update the giroCab
        giroCab.setData(UPDATED_DATA);
        giroCab.setObservacoes(UPDATED_OBSERVACOES);
        restGiroCabMockMvc.perform(put("/api/giroCabs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(giroCab)))
                .andExpect(status().isOk());

        // Validate the GiroCab in the database
        List<GiroCab> giroCabs = giroCabRepository.findAll();
        assertThat(giroCabs).hasSize(1);
        GiroCab testGiroCab = giroCabs.iterator().next();
        assertThat(testGiroCab.getData()).isEqualTo(UPDATED_DATA);
        assertThat(testGiroCab.getObservacoes()).isEqualTo(UPDATED_OBSERVACOES);
    }

    @Test
    @Transactional
    public void deleteGiroCab() throws Exception {
        // Initialize the database
        giroCabRepository.saveAndFlush(giroCab);

        // Get the giroCab
        restGiroCabMockMvc.perform(delete("/api/giroCabs/{id}", giroCab.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<GiroCab> giroCabs = giroCabRepository.findAll();
        assertThat(giroCabs).hasSize(0);
    }
}
