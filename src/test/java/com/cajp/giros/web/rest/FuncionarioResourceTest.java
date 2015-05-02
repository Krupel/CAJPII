package com.cajp.giros.web.rest;

import com.cajp.giros.Application;
import com.cajp.giros.domain.Funcionario;
import com.cajp.giros.repository.FuncionarioRepository;

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
 * Test class for the FuncionarioResource REST controller.
 *
 * @see FuncionarioResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class FuncionarioResourceTest {

    private static final String DEFAULT_NOME = "SAMPLE_TEXT";
    private static final String UPDATED_NOME = "UPDATED_TEXT";

    @Inject
    private FuncionarioRepository funcionarioRepository;

    private MockMvc restFuncionarioMockMvc;

    private Funcionario funcionario;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        FuncionarioResource funcionarioResource = new FuncionarioResource();
        ReflectionTestUtils.setField(funcionarioResource, "funcionarioRepository", funcionarioRepository);
        this.restFuncionarioMockMvc = MockMvcBuilders.standaloneSetup(funcionarioResource).build();
    }

    @Before
    public void initTest() {
        funcionario = new Funcionario();
        funcionario.setNome(DEFAULT_NOME);
    }

    @Test
    @Transactional
    public void createFuncionario() throws Exception {
        // Validate the database is empty
        assertThat(funcionarioRepository.findAll()).hasSize(0);

        // Create the Funcionario
        restFuncionarioMockMvc.perform(post("/api/funcionarios")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(funcionario)))
                .andExpect(status().isCreated());

        // Validate the Funcionario in the database
        List<Funcionario> funcionarios = funcionarioRepository.findAll();
        assertThat(funcionarios).hasSize(1);
        Funcionario testFuncionario = funcionarios.iterator().next();
        assertThat(testFuncionario.getNome()).isEqualTo(DEFAULT_NOME);
    }

    @Test
    @Transactional
    public void getAllFuncionarios() throws Exception {
        // Initialize the database
        funcionarioRepository.saveAndFlush(funcionario);

        // Get all the funcionarios
        restFuncionarioMockMvc.perform(get("/api/funcionarios"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].id").value(funcionario.getId().intValue()))
                .andExpect(jsonPath("$.[0].nome").value(DEFAULT_NOME.toString()));
    }

    @Test
    @Transactional
    public void getFuncionario() throws Exception {
        // Initialize the database
        funcionarioRepository.saveAndFlush(funcionario);

        // Get the funcionario
        restFuncionarioMockMvc.perform(get("/api/funcionarios/{id}", funcionario.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(funcionario.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingFuncionario() throws Exception {
        // Get the funcionario
        restFuncionarioMockMvc.perform(get("/api/funcionarios/{id}", 1L))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFuncionario() throws Exception {
        // Initialize the database
        funcionarioRepository.saveAndFlush(funcionario);

        // Update the funcionario
        funcionario.setNome(UPDATED_NOME);
        restFuncionarioMockMvc.perform(put("/api/funcionarios")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(funcionario)))
                .andExpect(status().isOk());

        // Validate the Funcionario in the database
        List<Funcionario> funcionarios = funcionarioRepository.findAll();
        assertThat(funcionarios).hasSize(1);
        Funcionario testFuncionario = funcionarios.iterator().next();
        assertThat(testFuncionario.getNome()).isEqualTo(UPDATED_NOME);
    }

    @Test
    @Transactional
    public void deleteFuncionario() throws Exception {
        // Initialize the database
        funcionarioRepository.saveAndFlush(funcionario);

        // Get the funcionario
        restFuncionarioMockMvc.perform(delete("/api/funcionarios/{id}", funcionario.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Funcionario> funcionarios = funcionarioRepository.findAll();
        assertThat(funcionarios).hasSize(0);
    }
}
