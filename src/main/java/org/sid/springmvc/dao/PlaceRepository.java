
package org.sid.springmvc.dao;

import org.sid.springmvc.entities.Place;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlaceRepository extends JpaRepository<Place, Long>{
	
}
