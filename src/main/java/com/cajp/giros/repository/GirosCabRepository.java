package com.cajp.giros.repository;

import com.cajp.giros.domain.GirosCab;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the GirosCab entity.
 */
public interface GirosCabRepository extends JpaRepository<GirosCab,Long> {

}
