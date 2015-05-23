package com.cajp.giros.repository;

import com.cajp.giros.domain.GiroLin;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the GiroLin entity.
 */
public interface GiroLinRepository extends JpaRepository<GiroLin,Long> {

}
