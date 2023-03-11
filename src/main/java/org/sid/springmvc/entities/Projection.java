package org.sid.springmvc.entities;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data @AllArgsConstructor @NoArgsConstructor
public class Projection {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date datePr;
	private double prix;
	private double prixVIP;
	@Temporal(TemporalType.TIME)
	private Date heurePr;
	@OneToMany(mappedBy = "projection",cascade=CascadeType.REMOVE)
	List<Ticket> tickets;
	@ManyToOne
	Spectacle spectacle;
	@ManyToOne
	Theatre theatre;
	
	public String getDatePrStr() {
		return new SimpleDateFormat("dd/MM/yyyy").format(datePr);
	}
	
	public int isClosed() {
		//if -1 closed 
		return new Date().compareTo(this.datePr);
	}
}
