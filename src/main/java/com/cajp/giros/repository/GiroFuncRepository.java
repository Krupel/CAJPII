package com.cajp.giros.repository;

import com.cajp.giros.domain.GiroFunc;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the GiroFunc entity.
 */
public interface GiroFuncRepository extends JpaRepository<GiroFunc,Long> {

}
