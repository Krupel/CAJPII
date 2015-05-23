package com.cajp.giros.repository;

import com.cajp.giros.domain.GiroCab;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the GiroCab entity.
 */
public interface GiroCabRepository extends JpaRepository<GiroCab,Long> {

}
