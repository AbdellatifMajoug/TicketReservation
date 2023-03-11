
package org.sid.springmvc.dao;

import java.util.List;

import org.sid.springmvc.entities.Projection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProjectionRepository extends JpaRepository<Projection, Long>{
	
	public Page<Projection> findAll(Pageable page);
	
	@Query("select p from Projection p where p.theatre.id=:x")
	public Page<Projection> listProjectionsByTheatreId(@Param("x")Long idTh,Pageable page);
		
	@Query("select p from Projection p where p.theatre.id=:x")
	public List<Projection> listProjectionsByTheatreId(@Param("x")Long idTh);
	
	@Query("select p from Projection p where p.theatre.ville.id=:x")
	public List<Projection> findProjectionByVilleId(@Param("x")Long idV);
	
	@Query("select p from Projection p where p.spectacle.categorie.id=:x")
	public List<Projection> findProjectionByCategorieId(@Param("x")Long idC);
	
	@Query("select p from Projection p where p.theatre.ville.id=:x and p.spectacle.categorie.id=:y")
	public List<Projection> findProjectionByVilleAndCategorieId(@Param("x")Long idV,@Param("y")Long idC);
	
	@Query("select p from Projection p where p.spectacle.titre like :x%")
	public List<Projection> findProjectionsBySpectacleName(@Param("x")String nom);
	
}
