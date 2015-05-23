package com.cajp.giros.web.rest;

import com.cajp.giros.Application;
import com.cajp.giros.domain.HistoricoUtente;
import com.cajp.giros.repository.HistoricoUtenteRepository;

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
 * Test class for the HistoricoUtenteResource REST controller.
 *
 * @see HistoricoUtenteResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class HistoricoUtenteResourceTest {


    private static final LocalDate DEFAULT_DATA = new LocalDate(0L);
    private static final LocalDate UPDATED_DATA = new LocalDate();

    @Inject
    private HistoricoUtenteRepository historicoUtenteRepository;

    private MockMvc restHistoricoUtenteMockMvc;

    private HistoricoUtente historicoUtente;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        HistoricoUtenteResource historicoUtenteResource = new HistoricoUtenteResource();
        ReflectionTestUtils.setField(historicoUtenteResource, "historicoUtenteRepository", historicoUtenteRepository);
        this.restHistoricoUtenteMockMvc = MockMvcBuilders.standaloneSetup(historicoUtenteResource).build();
    }

    @Before
    public void initTest() {
        historicoUtente = new HistoricoUtente();
        historicoUtente.setData(DEFAULT_DATA);
    }

    @Test
    @Transactional
    public void createHistoricoUtente() throws Exception {
        // Validate the database is empty
        assertThat(historicoUtenteRepository.findAll()).hasSize(0);

        // Create the HistoricoUtente
        restHistoricoUtenteMockMvc.perform(post("/api/historicoUtentes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(historicoUtente)))
                .andExpect(status().isCreated());

        // Validate the HistoricoUtente in the database
        List<HistoricoUtente> historicoUtentes = historicoUtenteRepository.findAll();
        assertThat(historicoUtentes).hasSize(1);
        HistoricoUtente testHistoricoUtente = historicoUtentes.iterator().next();
        assertThat(testHistoricoUtente.getData()).isEqualTo(DEFAULT_DATA);
    }

    @Test
    @Transactional
    public void getAllHistoricoUtentes() throws Exception {
        // Initialize the database
        historicoUtenteRepository.saveAndFlush(historicoUtente);

        // Get all the historicoUtentes
        restHistoricoUtenteMockMvc.perform(get("/api/historicoUtentes"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].id").value(historicoUtente.getId().intValue()))
                .andExpect(jsonPath("$.[0].data").value(DEFAULT_DATA.toString()));
    }

    @Test
    @Transactional
    public void getHistoricoUtente() throws Exception {
        // Initialize the database
        historicoUtenteRepository.saveAndFlush(historicoUtente);

        // Get the historicoUtente
        restHistoricoUtenteMockMvc.perform(get("/api/historicoUtentes/{id}", historicoUtente.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(historicoUtente.getId().intValue()))
            .andExpect(jsonPath("$.data").value(DEFAULT_DATA.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingHistoricoUtente() throws Exception {
        // Get the historicoUtente
        restHistoricoUtenteMockMvc.perform(get("/api/historicoUtentes/{id}", 1L))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateHistoricoUtente() throws Exception {
        // Initialize the database
        historicoUtenteRepository.saveAndFlush(historicoUtente);

        // Update the historicoUtente
        historicoUtente.setData(UPDATED_DATA);
        restHistoricoUtenteMockMvc.perform(put("/api/historicoUtentes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(historicoUtente)))
                .andExpect(status().isOk());

        // Validate the HistoricoUtente in the database
        List<HistoricoUtente> historicoUtentes = historicoUtenteRepository.findAll();
        assertThat(historicoUtentes).hasSize(1);
        HistoricoUtente testHistoricoUtente = historicoUtentes.iterator().next();
        assertThat(testHistoricoUtente.getData()).isEqualTo(UPDATED_DATA);
    }

    @Test
    @Transactional
    public void deleteHistoricoUtente() throws Exception {
        // Initialize the database
        historicoUtenteRepository.saveAndFlush(historicoUtente);

        // Get the historicoUtente
        restHistoricoUtenteMockMvc.perform(delete("/api/historicoUtentes/{id}", historicoUtente.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<HistoricoUtente> historicoUtentes = historicoUtenteRepository.findAll();
        assertThat(historicoUtentes).hasSize(0);
    }
}
