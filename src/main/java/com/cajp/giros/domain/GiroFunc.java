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
 * A GiroFunc.
 */
@Entity
@Table(name = "T_GIROFUNC")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class GiroFunc implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private Tecnico tecnico;

    @ManyToOne
    private GiroCab giroCab;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Tecnico getTecnico() {
        return tecnico;
    }

    public void setTecnico(Tecnico tecnico) {
        this.tecnico = tecnico;
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

        GiroFunc giroFunc = (GiroFunc) o;

        if ( ! Objects.equals(id, giroFunc.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "GiroFunc{" +
                "id=" + id +
                '}';
    }
}
