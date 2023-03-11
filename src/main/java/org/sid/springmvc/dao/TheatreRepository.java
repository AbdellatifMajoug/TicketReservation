
package org.sid.springmvc.dao;

import org.sid.springmvc.entities.Theatre;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TheatreRepository extends JpaRepository<Theatre, Long>{
	public Page<Theatre> findAll(Pageable page);

}
