package com.cajp.giros.web.rest;

import com.cajp.giros.Application;
import com.cajp.giros.domain.Tecnico;
import com.cajp.giros.repository.TecnicoRepository;

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
 * Test class for the TecnicoResource REST controller.
 *
 * @see TecnicoResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class TecnicoResourceTest {

    private static final String DEFAULT_NOME = "SAMPLE_TEXT";
    private static final String UPDATED_NOME = "UPDATED_TEXT";
    private static final String DEFAULT_BI = "SAMPLE_TEXT";
    private static final String UPDATED_BI = "UPDATED_TEXT";
    private static final String DEFAULT_TELEFONE = "SAMPLE_TEXT";
    private static final String UPDATED_TELEFONE = "UPDATED_TEXT";
    private static final String DEFAULT_EMAIL = "SAMPLE_TEXT";
    private static final String UPDATED_EMAIL = "UPDATED_TEXT";

    @Inject
    private TecnicoRepository tecnicoRepository;

    private MockMvc restTecnicoMockMvc;

    private Tecnico tecnico;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        TecnicoResource tecnicoResource = new TecnicoResource();
        ReflectionTestUtils.setField(tecnicoResource, "tecnicoRepository", tecnicoRepository);
        this.restTecnicoMockMvc = MockMvcBuilders.standaloneSetup(tecnicoResource).build();
    }

    @Before
    public void initTest() {
        tecnico = new Tecnico();
        tecnico.setNome(DEFAULT_NOME);
        tecnico.setBi(DEFAULT_BI);
        tecnico.setTelefone(DEFAULT_TELEFONE);
        tecnico.setEmail(DEFAULT_EMAIL);
    }

    @Test
    @Transactional
    public void createTecnico() throws Exception {
        // Validate the database is empty
        assertThat(tecnicoRepository.findAll()).hasSize(0);

        // Create the Tecnico
        restTecnicoMockMvc.perform(post("/api/tecnicos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(tecnico)))
                .andExpect(status().isCreated());

        // Validate the Tecnico in the database
        List<Tecnico> tecnicos = tecnicoRepository.findAll();
        assertThat(tecnicos).hasSize(1);
        Tecnico testTecnico = tecnicos.iterator().next();
        assertThat(testTecnico.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testTecnico.getBi()).isEqualTo(DEFAULT_BI);
        assertThat(testTecnico.getTelefone()).isEqualTo(DEFAULT_TELEFONE);
        assertThat(testTecnico.getEmail()).isEqualTo(DEFAULT_EMAIL);
    }

    @Test
    @Transactional
    public void getAllTecnicos() throws Exception {
        // Initialize the database
        tecnicoRepository.saveAndFlush(tecnico);

        // Get all the tecnicos
        restTecnicoMockMvc.perform(get("/api/tecnicos"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].id").value(tecnico.getId().intValue()))
                .andExpect(jsonPath("$.[0].nome").value(DEFAULT_NOME.toString()))
                .andExpect(jsonPath("$.[0].bi").value(DEFAULT_BI.toString()))
                .andExpect(jsonPath("$.[0].telefone").value(DEFAULT_TELEFONE.toString()))
                .andExpect(jsonPath("$.[0].email").value(DEFAULT_EMAIL.toString()));
    }

    @Test
    @Transactional
    public void getTecnico() throws Exception {
        // Initialize the database
        tecnicoRepository.saveAndFlush(tecnico);

        // Get the tecnico
        restTecnicoMockMvc.perform(get("/api/tecnicos/{id}", tecnico.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(tecnico.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME.toString()))
            .andExpect(jsonPath("$.bi").value(DEFAULT_BI.toString()))
            .andExpect(jsonPath("$.telefone").value(DEFAULT_TELEFONE.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTecnico() throws Exception {
        // Get the tecnico
        restTecnicoMockMvc.perform(get("/api/tecnicos/{id}", 1L))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTecnico() throws Exception {
        // Initialize the database
        tecnicoRepository.saveAndFlush(tecnico);

        // Update the tecnico
        tecnico.setNome(UPDATED_NOME);
        tecnico.setBi(UPDATED_BI);
        tecnico.setTelefone(UPDATED_TELEFONE);
        tecnico.setEmail(UPDATED_EMAIL);
        restTecnicoMockMvc.perform(put("/api/tecnicos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(tecnico)))
                .andExpect(status().isOk());

        // Validate the Tecnico in the database
        List<Tecnico> tecnicos = tecnicoRepository.findAll();
        assertThat(tecnicos).hasSize(1);
        Tecnico testTecnico = tecnicos.iterator().next();
        assertThat(testTecnico.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testTecnico.getBi()).isEqualTo(UPDATED_BI);
        assertThat(testTecnico.getTelefone()).isEqualTo(UPDATED_TELEFONE);
        assertThat(testTecnico.getEmail()).isEqualTo(UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void deleteTecnico() throws Exception {
        // Initialize the database
        tecnicoRepository.saveAndFlush(tecnico);

        // Get the tecnico
        restTecnicoMockMvc.perform(delete("/api/tecnicos/{id}", tecnico.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Tecnico> tecnicos = tecnicoRepository.findAll();
        assertThat(tecnicos).hasSize(0);
    }
}
