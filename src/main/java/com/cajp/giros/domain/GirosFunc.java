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
 * A GirosFunc.
 */
@Entity
@Table(name = "T_GIROSFUNC")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class GirosFunc implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private GirosCab girosCab;

    @ManyToOne
    private Funcionario funcionario;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public GirosCab getGirosCab() {
        return girosCab;
    }

    public void setGirosCab(GirosCab girosCab) {
        this.girosCab = girosCab;
    }

    public Funcionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        GirosFunc girosFunc = (GirosFunc) o;

        if ( ! Objects.equals(id, girosFunc.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "GirosFunc{" +
                "id=" + id +
                '}';
    }
}
