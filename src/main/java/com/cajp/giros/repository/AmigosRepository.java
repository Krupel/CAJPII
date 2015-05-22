package com.cajp.giros.repository;

import com.cajp.giros.domain.Amigos;
import org.joda.time.DateTime;
import org.springframework.data.jpa.repository.*;

import java.util.List;


/**
 * Spring Data JPA repository for the Amigos entity.
 */
public interface AmigosRepository extends JpaRepository<Amigos,Long> {

    @Query("select a from Amigos a")
    List<Amigos> findAmigosByDateInterval(DateTime deData,DateTime ateData);

}
