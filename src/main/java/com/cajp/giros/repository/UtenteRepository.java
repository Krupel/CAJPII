package com.cajp.giros.repository;

import com.cajp.giros.domain.Utente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the Utente entity.
 */
public interface UtenteRepository extends JpaRepository<Utente,Long> {

    String FIND_ByNameNacio = "SELECT u FROM Utente u WHERE u.nome like :name and u.nacionalidade like :nacio ";

    @Query(FIND_ByNameNacio)
    Page<Utente> findByNameNacio(@Param("name") String name,@Param("nacio") String nacio, Pageable pageable);
}

