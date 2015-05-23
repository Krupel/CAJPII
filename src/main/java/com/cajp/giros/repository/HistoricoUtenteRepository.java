package com.cajp.giros.repository;

import com.cajp.giros.domain.HistoricoUtente;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the HistoricoUtente entity.
 */
public interface HistoricoUtenteRepository extends JpaRepository<HistoricoUtente,Long> {

}
