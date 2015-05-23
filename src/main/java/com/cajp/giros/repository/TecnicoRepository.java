package com.cajp.giros.repository;

import com.cajp.giros.domain.Tecnico;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Tecnico entity.
 */
public interface TecnicoRepository extends JpaRepository<Tecnico,Long> {

}
