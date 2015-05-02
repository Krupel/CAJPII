package com.cajp.giros.repository;

import com.cajp.giros.domain.Tipologia;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Tipologia entity.
 */
public interface TipologiaRepository extends JpaRepository<Tipologia,Long> {

}
