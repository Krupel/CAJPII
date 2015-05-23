package com.cajp.giros.repository;

import com.cajp.giros.domain.Utente;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Utente entity.
 */
public interface UtenteRepository extends JpaRepository<Utente,Long> {

}
