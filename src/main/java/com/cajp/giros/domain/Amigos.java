package com.cajp.giros.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.cajp.giros.domain.util.CustomLocalDateSerializer;
import com.cajp.giros.domain.util.ISO8601LocalDateDeserializer;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;
import org.joda.time.LocalDate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Amigos.
 */
@Entity
@Table(name = "T_AMIGOS")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Amigos implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "nome")
    private String nome;

    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
    @JsonSerialize(using = CustomLocalDateSerializer.class)
    @JsonDeserialize(using = ISO8601LocalDateDeserializer.class)
    @Column(name = "data_nascimento")
    private LocalDate dataNascimento;

    @Column(name = "bi")
    private String bi;

    @Column(name = "niss")
    private String niss;

    @Column(name = "nacionalidade")
    private String nacionalidade;

    @Column(name = "autorizacao_residencia")
    private String autorizacaoResidencia;

    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
    @JsonSerialize(using = CustomLocalDateSerializer.class)
    @JsonDeserialize(using = ISO8601LocalDateDeserializer.class)
    @Column(name = "data_registo")
    private LocalDate dataRegisto;

    @Column(name = "caracteristicas")
    private String caracteristicas;

    @Column(name = "observacoes")
    private String observacoes;

    @ManyToOne
    private Tipologia tipologia;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getBi() {
        return bi;
    }

    public void setBi(String bi) {
        this.bi = bi;
    }

    public String getNiss() {
        return niss;
    }

    public void setNiss(String niss) {
        this.niss = niss;
    }

    public String getNacionalidade() {
        return nacionalidade;
    }

    public void setNacionalidade(String nacionalidade) {
        this.nacionalidade = nacionalidade;
    }

    public String getAutorizacaoResidencia() {
        return autorizacaoResidencia;
    }

    public void setAutorizacaoResidencia(String autorizacaoResidencia) {
        this.autorizacaoResidencia = autorizacaoResidencia;
    }

    public LocalDate getDataRegisto() {
        return dataRegisto;
    }

    public void setDataRegisto(LocalDate dataRegisto) {
        this.dataRegisto = dataRegisto;
    }

    public String getCaracteristicas() {
        return caracteristicas;
    }

    public void setCaracteristicas(String caracteristicas) {
        this.caracteristicas = caracteristicas;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public Tipologia getTipologia() {
        return tipologia;
    }

    public void setTipologia(Tipologia tipologia) {
        this.tipologia = tipologia;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Amigos amigos = (Amigos) o;

        if ( ! Objects.equals(id, amigos.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Amigos{" +
                "id=" + id +
                ", nome='" + nome + "'" +
                ", dataNascimento='" + dataNascimento + "'" +
                ", bi='" + bi + "'" +
                ", niss='" + niss + "'" +
                ", nacionalidade='" + nacionalidade + "'" +
                ", autorizacaoResidencia='" + autorizacaoResidencia + "'" +
                ", dataRegisto='" + dataRegisto + "'" +
                ", caracteristicas='" + caracteristicas + "'" +
                ", observacoes='" + observacoes + "'" +
                '}';
    }
}
