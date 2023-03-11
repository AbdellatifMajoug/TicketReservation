
package org.sid.springmvc.dao;

import java.util.List;

import org.sid.springmvc.entities.Ticket;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TicketRepository extends JpaRepository<Ticket, Long>{
	@Query("select t from Ticket t where t.projection.id=:x and t.reservee=true")
	public Page<Ticket> listTicketsResByProjectionId(@Param("x")Long idPr,Pageable page);
	
	@Query("select t from Ticket t where t.projection.id=:x ")
	public List<Ticket> listTicketsByProjectionId(@Param("x")Long idPr);
	
	@Query("select t from Ticket t where t.projection.id=:x and t.reservee=false and vip=:y")
	public List<Ticket> listTicketsDispoByProjectionId(@Param("x")Long idPr,@Param("y")boolean vip);
	
	@Query("select t from Ticket t where t.projection.id=:x and t.reservee=false")
	public List<Ticket> listTicketsDispoByProjectionId(@Param("x")Long idPr);

	@Query("select t from Ticket t where t.user.id=:x")
	public List<Ticket> listTicketsByUserId(@Param("x")Long idUs);
		
}
