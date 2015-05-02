package com.cajp.giros.web.rest;

import com.cajp.giros.Application;
import com.cajp.giros.domain.Amigos;
import com.cajp.giros.repository.AmigosRepository;

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
 * Test class for the AmigosResource REST controller.
 *
 * @see AmigosResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class AmigosResourceTest {

    private static final String DEFAULT_NOME = "SAMPLE_TEXT";
    private static final String UPDATED_NOME = "UPDATED_TEXT";

    private static final LocalDate DEFAULT_DATA_NASCIMENTO = new LocalDate(0L);
    private static final LocalDate UPDATED_DATA_NASCIMENTO = new LocalDate();
    private static final String DEFAULT_BI = "SAMPLE_TEXT";
    private static final String UPDATED_BI = "UPDATED_TEXT";
    private static final String DEFAULT_NISS = "SAMPLE_TEXT";
    private static final String UPDATED_NISS = "UPDATED_TEXT";
    private static final String DEFAULT_NACIONALIDADE = "SAMPLE_TEXT";
    private static final String UPDATED_NACIONALIDADE = "UPDATED_TEXT";
    private static final String DEFAULT_AUTORIZACAO_RESIDENCIA = "SAMPLE_TEXT";
    private static final String UPDATED_AUTORIZACAO_RESIDENCIA = "UPDATED_TEXT";

    private static final LocalDate DEFAULT_DATA_REGISTO = new LocalDate(0L);
    private static final LocalDate UPDATED_DATA_REGISTO = new LocalDate();
    private static final String DEFAULT_CARACTERISTICAS = "SAMPLE_TEXT";
    private static final String UPDATED_CARACTERISTICAS = "UPDATED_TEXT";
    private static final String DEFAULT_OBSERVACOES = "SAMPLE_TEXT";
    private static final String UPDATED_OBSERVACOES = "UPDATED_TEXT";

    @Inject
    private AmigosRepository amigosRepository;

    private MockMvc restAmigosMockMvc;

    private Amigos amigos;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        AmigosResource amigosResource = new AmigosResource();
        ReflectionTestUtils.setField(amigosResource, "amigosRepository", amigosRepository);
        this.restAmigosMockMvc = MockMvcBuilders.standaloneSetup(amigosResource).build();
    }

    @Before
    public void initTest() {
        amigos = new Amigos();
        amigos.setNome(DEFAULT_NOME);
        amigos.setDataNascimento(DEFAULT_DATA_NASCIMENTO);
        amigos.setBi(DEFAULT_BI);
        amigos.setNiss(DEFAULT_NISS);
        amigos.setNacionalidade(DEFAULT_NACIONALIDADE);
        amigos.setAutorizacaoResidencia(DEFAULT_AUTORIZACAO_RESIDENCIA);
        amigos.setDataRegisto(DEFAULT_DATA_REGISTO);
        amigos.setCaracteristicas(DEFAULT_CARACTERISTICAS);
        amigos.setObservacoes(DEFAULT_OBSERVACOES);
    }

    @Test
    @Transactional
    public void createAmigos() throws Exception {
        // Validate the database is empty
        assertThat(amigosRepository.findAll()).hasSize(0);

        // Create the Amigos
        restAmigosMockMvc.perform(post("/api/amigoss")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(amigos)))
                .andExpect(status().isCreated());

        // Validate the Amigos in the database
        List<Amigos> amigoss = amigosRepository.findAll();
        assertThat(amigoss).hasSize(1);
        Amigos testAmigos = amigoss.iterator().next();
        assertThat(testAmigos.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testAmigos.getDataNascimento()).isEqualTo(DEFAULT_DATA_NASCIMENTO);
        assertThat(testAmigos.getBi()).isEqualTo(DEFAULT_BI);
        assertThat(testAmigos.getNiss()).isEqualTo(DEFAULT_NISS);
        assertThat(testAmigos.getNacionalidade()).isEqualTo(DEFAULT_NACIONALIDADE);
        assertThat(testAmigos.getAutorizacaoResidencia()).isEqualTo(DEFAULT_AUTORIZACAO_RESIDENCIA);
        assertThat(testAmigos.getDataRegisto()).isEqualTo(DEFAULT_DATA_REGISTO);
        assertThat(testAmigos.getCaracteristicas()).isEqualTo(DEFAULT_CARACTERISTICAS);
        assertThat(testAmigos.getObservacoes()).isEqualTo(DEFAULT_OBSERVACOES);
    }

    @Test
    @Transactional
    public void getAllAmigoss() throws Exception {
        // Initialize the database
        amigosRepository.saveAndFlush(amigos);

        // Get all the amigoss
        restAmigosMockMvc.perform(get("/api/amigoss"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].id").value(amigos.getId().intValue()))
                .andExpect(jsonPath("$.[0].nome").value(DEFAULT_NOME.toString()))
                .andExpect(jsonPath("$.[0].dataNascimento").value(DEFAULT_DATA_NASCIMENTO.toString()))
                .andExpect(jsonPath("$.[0].bi").value(DEFAULT_BI.toString()))
                .andExpect(jsonPath("$.[0].niss").value(DEFAULT_NISS.toString()))
                .andExpect(jsonPath("$.[0].nacionalidade").value(DEFAULT_NACIONALIDADE.toString()))
                .andExpect(jsonPath("$.[0].autorizacaoResidencia").value(DEFAULT_AUTORIZACAO_RESIDENCIA.toString()))
                .andExpect(jsonPath("$.[0].dataRegisto").value(DEFAULT_DATA_REGISTO.toString()))
                .andExpect(jsonPath("$.[0].caracteristicas").value(DEFAULT_CARACTERISTICAS.toString()))
                .andExpect(jsonPath("$.[0].observacoes").value(DEFAULT_OBSERVACOES.toString()));
    }

    @Test
    @Transactional
    public void getAmigos() throws Exception {
        // Initialize the database
        amigosRepository.saveAndFlush(amigos);

        // Get the amigos
        restAmigosMockMvc.perform(get("/api/amigoss/{id}", amigos.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(amigos.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME.toString()))
            .andExpect(jsonPath("$.dataNascimento").value(DEFAULT_DATA_NASCIMENTO.toString()))
            .andExpect(jsonPath("$.bi").value(DEFAULT_BI.toString()))
            .andExpect(jsonPath("$.niss").value(DEFAULT_NISS.toString()))
            .andExpect(jsonPath("$.nacionalidade").value(DEFAULT_NACIONALIDADE.toString()))
            .andExpect(jsonPath("$.autorizacaoResidencia").value(DEFAULT_AUTORIZACAO_RESIDENCIA.toString()))
            .andExpect(jsonPath("$.dataRegisto").value(DEFAULT_DATA_REGISTO.toString()))
            .andExpect(jsonPath("$.caracteristicas").value(DEFAULT_CARACTERISTICAS.toString()))
            .andExpect(jsonPath("$.observacoes").value(DEFAULT_OBSERVACOES.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAmigos() throws Exception {
        // Get the amigos
        restAmigosMockMvc.perform(get("/api/amigoss/{id}", 1L))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAmigos() throws Exception {
        // Initialize the database
        amigosRepository.saveAndFlush(amigos);

        // Update the amigos
        amigos.setNome(UPDATED_NOME);
        amigos.setDataNascimento(UPDATED_DATA_NASCIMENTO);
        amigos.setBi(UPDATED_BI);
        amigos.setNiss(UPDATED_NISS);
        amigos.setNacionalidade(UPDATED_NACIONALIDADE);
        amigos.setAutorizacaoResidencia(UPDATED_AUTORIZACAO_RESIDENCIA);
        amigos.setDataRegisto(UPDATED_DATA_REGISTO);
        amigos.setCaracteristicas(UPDATED_CARACTERISTICAS);
        amigos.setObservacoes(UPDATED_OBSERVACOES);
        restAmigosMockMvc.perform(put("/api/amigoss")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(amigos)))
                .andExpect(status().isOk());

        // Validate the Amigos in the database
        List<Amigos> amigoss = amigosRepository.findAll();
        assertThat(amigoss).hasSize(1);
        Amigos testAmigos = amigoss.iterator().next();
        assertThat(testAmigos.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testAmigos.getDataNascimento()).isEqualTo(UPDATED_DATA_NASCIMENTO);
        assertThat(testAmigos.getBi()).isEqualTo(UPDATED_BI);
        assertThat(testAmigos.getNiss()).isEqualTo(UPDATED_NISS);
        assertThat(testAmigos.getNacionalidade()).isEqualTo(UPDATED_NACIONALIDADE);
        assertThat(testAmigos.getAutorizacaoResidencia()).isEqualTo(UPDATED_AUTORIZACAO_RESIDENCIA);
        assertThat(testAmigos.getDataRegisto()).isEqualTo(UPDATED_DATA_REGISTO);
        assertThat(testAmigos.getCaracteristicas()).isEqualTo(UPDATED_CARACTERISTICAS);
        assertThat(testAmigos.getObservacoes()).isEqualTo(UPDATED_OBSERVACOES);
    }

    @Test
    @Transactional
    public void deleteAmigos() throws Exception {
        // Initialize the database
        amigosRepository.saveAndFlush(amigos);

        // Get the amigos
        restAmigosMockMvc.perform(delete("/api/amigoss/{id}", amigos.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Amigos> amigoss = amigosRepository.findAll();
        assertThat(amigoss).hasSize(0);
    }
}
