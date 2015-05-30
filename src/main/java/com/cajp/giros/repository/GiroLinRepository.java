package com.cajp.giros.repository;

import com.cajp.giros.domain.GiroLin;
import com.cajp.giros.domain.GiroCab;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import javax.persistence.TemporalType;
import java.util.Date;
import java.util.List;

/**
 * Spring Data JPA repository for the GiroLin entity.
 */
public interface GiroLinRepository extends JpaRepository<GiroLin,Long> {

    String FIND_RESUMO = "SELECT gl FROM GiroLin gl LEFT join gl.giroCab where gl.giroCab.data >= :date_de and gl.giroCab.data <= :date_ate ";

    @Query(FIND_RESUMO)
    Page<GiroLin> findResumo(@Param("date_de") String date_de,@Param("date_ate") String date_ate,Pageable pageable);
}
