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
 * A GirosLin.
 */
@Entity
@Table(name = "T_GIROSLIN")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class GirosLin implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "observacoes")
    private String observacoes;

    @ManyToOne
    private GirosCab girosCab;

    @ManyToOne
    private Amigos amigos;

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

    public GirosCab getGirosCab() {
        return girosCab;
    }

    public void setGirosCab(GirosCab girosCab) {
        this.girosCab = girosCab;
    }

    public Amigos getAmigos() {
        return amigos;
    }

    public void setAmigos(Amigos amigos) {
        this.amigos = amigos;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        GirosLin girosLin = (GirosLin) o;

        if ( ! Objects.equals(id, girosLin.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "GirosLin{" +
                "id=" + id +
                ", observacoes='" + observacoes + "'" +
                '}';
    }
}
