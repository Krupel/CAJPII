package com.cajp.giros.web.rest;

import com.cajp.giros.Application;
import com.cajp.giros.domain.Tipologia;
import com.cajp.giros.repository.TipologiaRepository;

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
 * Test class for the TipologiaResource REST controller.
 *
 * @see TipologiaResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class TipologiaResourceTest {

    private static final String DEFAULT_DESCRICAO = "SAMPLE_TEXT";
    private static final String UPDATED_DESCRICAO = "UPDATED_TEXT";

    @Inject
    private TipologiaRepository tipologiaRepository;

    private MockMvc restTipologiaMockMvc;

    private Tipologia tipologia;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        TipologiaResource tipologiaResource = new TipologiaResource();
        ReflectionTestUtils.setField(tipologiaResource, "tipologiaRepository", tipologiaRepository);
        this.restTipologiaMockMvc = MockMvcBuilders.standaloneSetup(tipologiaResource).build();
    }

    @Before
    public void initTest() {
        tipologia = new Tipologia();
        tipologia.setDescricao(DEFAULT_DESCRICAO);
    }

    @Test
    @Transactional
    public void createTipologia() throws Exception {
        // Validate the database is empty
        assertThat(tipologiaRepository.findAll()).hasSize(0);

        // Create the Tipologia
        restTipologiaMockMvc.perform(post("/api/tipologias")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(tipologia)))
                .andExpect(status().isCreated());

        // Validate the Tipologia in the database
        List<Tipologia> tipologias = tipologiaRepository.findAll();
        assertThat(tipologias).hasSize(1);
        Tipologia testTipologia = tipologias.iterator().next();
        assertThat(testTipologia.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllTipologias() throws Exception {
        // Initialize the database
        tipologiaRepository.saveAndFlush(tipologia);

        // Get all the tipologias
        restTipologiaMockMvc.perform(get("/api/tipologias"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].id").value(tipologia.getId().intValue()))
                .andExpect(jsonPath("$.[0].descricao").value(DEFAULT_DESCRICAO.toString()));
    }

    @Test
    @Transactional
    public void getTipologia() throws Exception {
        // Initialize the database
        tipologiaRepository.saveAndFlush(tipologia);

        // Get the tipologia
        restTipologiaMockMvc.perform(get("/api/tipologias/{id}", tipologia.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(tipologia.getId().intValue()))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTipologia() throws Exception {
        // Get the tipologia
        restTipologiaMockMvc.perform(get("/api/tipologias/{id}", 1L))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTipologia() throws Exception {
        // Initialize the database
        tipologiaRepository.saveAndFlush(tipologia);

        // Update the tipologia
        tipologia.setDescricao(UPDATED_DESCRICAO);
        restTipologiaMockMvc.perform(put("/api/tipologias")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(tipologia)))
                .andExpect(status().isOk());

        // Validate the Tipologia in the database
        List<Tipologia> tipologias = tipologiaRepository.findAll();
        assertThat(tipologias).hasSize(1);
        Tipologia testTipologia = tipologias.iterator().next();
        assertThat(testTipologia.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void deleteTipologia() throws Exception {
        // Initialize the database
        tipologiaRepository.saveAndFlush(tipologia);

        // Get the tipologia
        restTipologiaMockMvc.perform(delete("/api/tipologias/{id}", tipologia.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Tipologia> tipologias = tipologiaRepository.findAll();
        assertThat(tipologias).hasSize(0);
    }
}
