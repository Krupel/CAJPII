package com.cajp.giros.web.rest;

import com.cajp.giros.Application;
import com.cajp.giros.domain.Utente;
import com.cajp.giros.repository.UtenteRepository;

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
 * Test class for the UtenteResource REST controller.
 *
 * @see UtenteResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class UtenteResourceTest {

    private static final String DEFAULT_NOME = "SAMPLE_TEXT";
    private static final String UPDATED_NOME = "UPDATED_TEXT";

    private static final LocalDate DEFAULT_DATANASCIMENTO = new LocalDate(0L);
    private static final LocalDate UPDATED_DATANASCIMENTO = new LocalDate();
    private static final String DEFAULT_BI = "SAMPLE_TEXT";
    private static final String UPDATED_BI = "UPDATED_TEXT";

    private static final LocalDate DEFAULT_VALIDADEBI = new LocalDate(0L);
    private static final LocalDate UPDATED_VALIDADEBI = new LocalDate();
    private static final String DEFAULT_SEXO = "SAMPLE_TEXT";
    private static final String UPDATED_SEXO = "UPDATED_TEXT";
    private static final String DEFAULT_NISS = "SAMPLE_TEXT";
    private static final String UPDATED_NISS = "UPDATED_TEXT";
    private static final String DEFAULT_NACIONALIDADE = "SAMPLE_TEXT";
    private static final String UPDATED_NACIONALIDADE = "UPDATED_TEXT";

    private static final LocalDate DEFAULT_DATAREGISTO = new LocalDate(0L);
    private static final LocalDate UPDATED_DATAREGISTO = new LocalDate();
    private static final String DEFAULT_CARACTERISTICAS = "SAMPLE_TEXT";
    private static final String UPDATED_CARACTERISTICAS = "UPDATED_TEXT";

    private static final Boolean DEFAULT_ACTIVO = false;
    private static final Boolean UPDATED_ACTIVO = true;

    @Inject
    private UtenteRepository utenteRepository;

    private MockMvc restUtenteMockMvc;

    private Utente utente;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        UtenteResource utenteResource = new UtenteResource();
        ReflectionTestUtils.setField(utenteResource, "utenteRepository", utenteRepository);
        this.restUtenteMockMvc = MockMvcBuilders.standaloneSetup(utenteResource).build();
    }

    @Before
    public void initTest() {
        utente = new Utente();
        utente.setNome(DEFAULT_NOME);
        utente.setDatanascimento(DEFAULT_DATANASCIMENTO);
        utente.setBi(DEFAULT_BI);
        utente.setValidadebi(DEFAULT_VALIDADEBI);
        utente.setSexo(DEFAULT_SEXO);
        utente.setNiss(DEFAULT_NISS);
        utente.setNacionalidade(DEFAULT_NACIONALIDADE);
        utente.setDataregisto(DEFAULT_DATAREGISTO);
        utente.setCaracteristicas(DEFAULT_CARACTERISTICAS);
        utente.setActivo(DEFAULT_ACTIVO);
    }

    @Test
    @Transactional
    public void createUtente() throws Exception {
        // Validate the database is empty
        assertThat(utenteRepository.findAll()).hasSize(0);

        // Create the Utente
        restUtenteMockMvc.perform(post("/api/utentes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(utente)))
                .andExpect(status().isCreated());

        // Validate the Utente in the database
        List<Utente> utentes = utenteRepository.findAll();
        assertThat(utentes).hasSize(1);
        Utente testUtente = utentes.iterator().next();
        assertThat(testUtente.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testUtente.getDatanascimento()).isEqualTo(DEFAULT_DATANASCIMENTO);
        assertThat(testUtente.getBi()).isEqualTo(DEFAULT_BI);
        assertThat(testUtente.getValidadebi()).isEqualTo(DEFAULT_VALIDADEBI);
        assertThat(testUtente.getSexo()).isEqualTo(DEFAULT_SEXO);
        assertThat(testUtente.getNiss()).isEqualTo(DEFAULT_NISS);
        assertThat(testUtente.getNacionalidade()).isEqualTo(DEFAULT_NACIONALIDADE);
        assertThat(testUtente.getDataregisto()).isEqualTo(DEFAULT_DATAREGISTO);
        assertThat(testUtente.getCaracteristicas()).isEqualTo(DEFAULT_CARACTERISTICAS);
        assertThat(testUtente.getActivo()).isEqualTo(DEFAULT_ACTIVO);
    }

    @Test
    @Transactional
    public void getAllUtentes() throws Exception {
        // Initialize the database
        utenteRepository.saveAndFlush(utente);

        // Get all the utentes
        restUtenteMockMvc.perform(get("/api/utentes"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].id").value(utente.getId().intValue()))
                .andExpect(jsonPath("$.[0].nome").value(DEFAULT_NOME.toString()))
                .andExpect(jsonPath("$.[0].datanascimento").value(DEFAULT_DATANASCIMENTO.toString()))
                .andExpect(jsonPath("$.[0].bi").value(DEFAULT_BI.toString()))
                .andExpect(jsonPath("$.[0].validadebi").value(DEFAULT_VALIDADEBI.toString()))
                .andExpect(jsonPath("$.[0].sexo").value(DEFAULT_SEXO.toString()))
                .andExpect(jsonPath("$.[0].niss").value(DEFAULT_NISS.toString()))
                .andExpect(jsonPath("$.[0].nacionalidade").value(DEFAULT_NACIONALIDADE.toString()))
                .andExpect(jsonPath("$.[0].dataregisto").value(DEFAULT_DATAREGISTO.toString()))
                .andExpect(jsonPath("$.[0].caracteristicas").value(DEFAULT_CARACTERISTICAS.toString()))
                .andExpect(jsonPath("$.[0].activo").value(DEFAULT_ACTIVO.booleanValue()));
    }

    @Test
    @Transactional
    public void getUtente() throws Exception {
        // Initialize the database
        utenteRepository.saveAndFlush(utente);

        // Get the utente
        restUtenteMockMvc.perform(get("/api/utentes/{id}", utente.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(utente.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME.toString()))
            .andExpect(jsonPath("$.datanascimento").value(DEFAULT_DATANASCIMENTO.toString()))
            .andExpect(jsonPath("$.bi").value(DEFAULT_BI.toString()))
            .andExpect(jsonPath("$.validadebi").value(DEFAULT_VALIDADEBI.toString()))
            .andExpect(jsonPath("$.sexo").value(DEFAULT_SEXO.toString()))
            .andExpect(jsonPath("$.niss").value(DEFAULT_NISS.toString()))
            .andExpect(jsonPath("$.nacionalidade").value(DEFAULT_NACIONALIDADE.toString()))
            .andExpect(jsonPath("$.dataregisto").value(DEFAULT_DATAREGISTO.toString()))
            .andExpect(jsonPath("$.caracteristicas").value(DEFAULT_CARACTERISTICAS.toString()))
            .andExpect(jsonPath("$.activo").value(DEFAULT_ACTIVO.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingUtente() throws Exception {
        // Get the utente
        restUtenteMockMvc.perform(get("/api/utentes/{id}", 1L))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUtente() throws Exception {
        // Initialize the database
        utenteRepository.saveAndFlush(utente);

        // Update the utente
        utente.setNome(UPDATED_NOME);
        utente.setDatanascimento(UPDATED_DATANASCIMENTO);
        utente.setBi(UPDATED_BI);
        utente.setValidadebi(UPDATED_VALIDADEBI);
        utente.setSexo(UPDATED_SEXO);
        utente.setNiss(UPDATED_NISS);
        utente.setNacionalidade(UPDATED_NACIONALIDADE);
        utente.setDataregisto(UPDATED_DATAREGISTO);
        utente.setCaracteristicas(UPDATED_CARACTERISTICAS);
        utente.setActivo(UPDATED_ACTIVO);
        restUtenteMockMvc.perform(put("/api/utentes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(utente)))
                .andExpect(status().isOk());

        // Validate the Utente in the database
        List<Utente> utentes = utenteRepository.findAll();
        assertThat(utentes).hasSize(1);
        Utente testUtente = utentes.iterator().next();
        assertThat(testUtente.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testUtente.getDatanascimento()).isEqualTo(UPDATED_DATANASCIMENTO);
        assertThat(testUtente.getBi()).isEqualTo(UPDATED_BI);
        assertThat(testUtente.getValidadebi()).isEqualTo(UPDATED_VALIDADEBI);
        assertThat(testUtente.getSexo()).isEqualTo(UPDATED_SEXO);
        assertThat(testUtente.getNiss()).isEqualTo(UPDATED_NISS);
        assertThat(testUtente.getNacionalidade()).isEqualTo(UPDATED_NACIONALIDADE);
        assertThat(testUtente.getDataregisto()).isEqualTo(UPDATED_DATAREGISTO);
        assertThat(testUtente.getCaracteristicas()).isEqualTo(UPDATED_CARACTERISTICAS);
        assertThat(testUtente.getActivo()).isEqualTo(UPDATED_ACTIVO);
    }

    @Test
    @Transactional
    public void deleteUtente() throws Exception {
        // Initialize the database
        utenteRepository.saveAndFlush(utente);

        // Get the utente
        restUtenteMockMvc.perform(delete("/api/utentes/{id}", utente.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Utente> utentes = utenteRepository.findAll();
        assertThat(utentes).hasSize(0);
    }
}
