package com.cajp.giros.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A GiroLin.
 */
@Entity
@Table(name = "T_GIROLIN")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class GiroLin implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "observacoes")
    private String observacoes;

    @ManyToOne
    private Utente utente;

    @ManyToOne
    private GiroCab giroCab;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public Utente getUtente() {
        return utente;
    }

    public void setUtente(Utente utente) {
        this.utente = utente;
    }

    public GiroCab getGiroCab() {
        return giroCab;
    }

    public void setGiroCab(GiroCab giroCab) {
        this.giroCab = giroCab;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        GiroLin giroLin = (GiroLin) o;

        if ( ! Objects.equals(id, giroLin.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "GiroLin{" +
                "id=" + id +
                ", observacoes='" + observacoes + "'" +
                '}';
    }
}
