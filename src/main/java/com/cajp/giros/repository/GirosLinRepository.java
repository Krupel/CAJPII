package com.cajp.giros.repository;

import com.cajp.giros.domain.GirosLin;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the GirosLin entity.
 */
public interface GirosLinRepository extends JpaRepository<GirosLin,Long> {

}
