
package org.sid.springmvc.dao;

import org.sid.springmvc.entities.Spectacle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpectacleRepository extends JpaRepository<Spectacle, Long>{
	public Page<Spectacle> findAll(Pageable page);

}
