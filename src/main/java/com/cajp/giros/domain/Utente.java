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
 * A Utente.
 */
@Entity
@Table(name = "T_UTENTE")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Utente implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "nome")
    private String nome;

    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
    @JsonSerialize(using = CustomLocalDateSerializer.class)
    @JsonDeserialize(using = ISO8601LocalDateDeserializer.class)
    @Column(name = "datanascimento")
    private LocalDate datanascimento;

    @Column(name = "bi")
    private String bi;

    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
    @JsonSerialize(using = CustomLocalDateSerializer.class)
    @JsonDeserialize(using = ISO8601LocalDateDeserializer.class)
    @Column(name = "validadebi")
    private LocalDate validadebi;

    @Column(name = "sexo")
    private String sexo;

    @Column(name = "niss")
    private String niss;

    @Column(name = "nacionalidade")
    private String nacionalidade;

    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
    @JsonSerialize(using = CustomLocalDateSerializer.class)
    @JsonDeserialize(using = ISO8601LocalDateDeserializer.class)
    @Column(name = "dataregisto")
    private LocalDate dataregisto;

    @Column(name = "caracteristicas")
    private String caracteristicas;

    @Column(name = "activo")
    private Boolean activo;

    @ManyToOne
    private Tipologia tipologiaAmigos;

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

    public LocalDate getDatanascimento() {
        return datanascimento;
    }

    public void setDatanascimento(LocalDate datanascimento) {
        this.datanascimento = datanascimento;
    }

    public String getBi() {
        return bi;
    }

    public void setBi(String bi) {
        this.bi = bi;
    }

    public LocalDate getValidadebi() {
        return validadebi;
    }

    public void setValidadebi(LocalDate validadebi) {
        this.validadebi = validadebi;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
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

    public LocalDate getDataregisto() {
        return dataregisto;
    }

    public void setDataregisto(LocalDate dataregisto) {
        this.dataregisto = dataregisto;
    }

    public String getCaracteristicas() {
        return caracteristicas;
    }

    public void setCaracteristicas(String caracteristicas) {
        this.caracteristicas = caracteristicas;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public Tipologia getTipologiaAmigos() {
        return tipologiaAmigos;
    }

    public void setTipologiaAmigos(Tipologia tipologia) {
        this.tipologiaAmigos = tipologia;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Utente utente = (Utente) o;

        if ( ! Objects.equals(id, utente.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Utente{" +
                "id=" + id +
                ", nome='" + nome + "'" +
                ", datanascimento='" + datanascimento + "'" +
                ", bi='" + bi + "'" +
                ", validadebi='" + validadebi + "'" +
                ", sexo='" + sexo + "'" +
                ", niss='" + niss + "'" +
                ", nacionalidade='" + nacionalidade + "'" +
                ", dataregisto='" + dataregisto + "'" +
                ", caracteristicas='" + caracteristicas + "'" +
                ", activo='" + activo + "'" +
                '}';
    }
}
