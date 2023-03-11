
package org.sid.springmvc.dao;

import org.sid.springmvc.entities.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long>{

}
