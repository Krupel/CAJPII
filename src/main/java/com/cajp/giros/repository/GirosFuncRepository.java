package com.cajp.giros.repository;

import com.cajp.giros.domain.GirosFunc;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the GirosFunc entity.
 */
public interface GirosFuncRepository extends JpaRepository<GirosFunc,Long> {

}
