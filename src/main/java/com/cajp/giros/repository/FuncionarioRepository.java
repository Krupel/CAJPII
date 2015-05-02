package com.cajp.giros.repository;

import com.cajp.giros.domain.Funcionario;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Funcionario entity.
 */
public interface FuncionarioRepository extends JpaRepository<Funcionario,Long> {

}
