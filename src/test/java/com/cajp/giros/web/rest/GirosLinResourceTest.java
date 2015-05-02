package com.cajp.giros.web.rest;

import com.cajp.giros.Application;
import com.cajp.giros.domain.GirosLin;
import com.cajp.giros.repository.GirosLinRepository;

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
 * Test class for the GirosLinResource REST controller.
 *
 * @see GirosLinResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class GirosLinResourceTest {

    private static final String DEFAULT_OBSERVACOES = "SAMPLE_TEXT";
    private static final String UPDATED_OBSERVACOES = "UPDATED_TEXT";

    @Inject
    private GirosLinRepository girosLinRepository;

    private MockMvc restGirosLinMockMvc;

    private GirosLin girosLin;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        GirosLinResource girosLinResource = new GirosLinResource();
        ReflectionTestUtils.setField(girosLinResource, "girosLinRepository", girosLinRepository);
        this.restGirosLinMockMvc = MockMvcBuilders.standaloneSetup(girosLinResource).build();
    }

    @Before
    public void initTest() {
        girosLin = new GirosLin();
        girosLin.setObservacoes(DEFAULT_OBSERVACOES);
    }

    @Test
    @Transactional
    public void createGirosLin() throws Exception {
        // Validate the database is empty
        assertThat(girosLinRepository.findAll()).hasSize(0);

        // Create the GirosLin
        restGirosLinMockMvc.perform(post("/api/girosLins")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(girosLin)))
                .andExpect(status().isCreated());

        // Validate the GirosLin in the database
        List<GirosLin> girosLins = girosLinRepository.findAll();
        assertThat(girosLins).hasSize(1);
        GirosLin testGirosLin = girosLins.iterator().next();
        assertThat(testGirosLin.getObservacoes()).isEqualTo(DEFAULT_OBSERVACOES);
    }

    @Test
    @Transactional
    public void getAllGirosLins() throws Exception {
        // Initialize the database
        girosLinRepository.saveAndFlush(girosLin);

        // Get all the girosLins
        restGirosLinMockMvc.perform(get("/api/girosLins"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].id").value(girosLin.getId().intValue()))
                .andExpect(jsonPath("$.[0].observacoes").value(DEFAULT_OBSERVACOES.toString()));
    }

    @Test
    @Transactional
    public void getGirosLin() throws Exception {
        // Initialize the database
        girosLinRepository.saveAndFlush(girosLin);

        // Get the girosLin
        restGirosLinMockMvc.perform(get("/api/girosLins/{id}", girosLin.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(girosLin.getId().intValue()))
            .andExpect(jsonPath("$.observacoes").value(DEFAULT_OBSERVACOES.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingGirosLin() throws Exception {
        // Get the girosLin
        restGirosLinMockMvc.perform(get("/api/girosLins/{id}", 1L))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGirosLin() throws Exception {
        // Initialize the database
        girosLinRepository.saveAndFlush(girosLin);

        // Update the girosLin
        girosLin.setObservacoes(UPDATED_OBSERVACOES);
        restGirosLinMockMvc.perform(put("/api/girosLins")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(girosLin)))
                .andExpect(status().isOk());

        // Validate the GirosLin in the database
        List<GirosLin> girosLins = girosLinRepository.findAll();
        assertThat(girosLins).hasSize(1);
        GirosLin testGirosLin = girosLins.iterator().next();
        assertThat(testGirosLin.getObservacoes()).isEqualTo(UPDATED_OBSERVACOES);
    }

    @Test
    @Transactional
    public void deleteGirosLin() throws Exception {
        // Initialize the database
        girosLinRepository.saveAndFlush(girosLin);

        // Get the girosLin
        restGirosLinMockMvc.perform(delete("/api/girosLins/{id}", girosLin.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<GirosLin> girosLins = girosLinRepository.findAll();
        assertThat(girosLins).hasSize(0);
    }
}
