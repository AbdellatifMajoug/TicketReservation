package org.sid.springmvc.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor
public class Place {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private boolean vip;
	private int numero;
	@ManyToOne
	Theatre theatre;
	@OneToMany(mappedBy = "place",cascade=CascadeType.REMOVE)
	private List<Ticket> tickets;
	
	public Place(int numero,boolean vip,Theatre theatre)
	{
		this.numero = numero;
		this.vip = vip;
		this.theatre = theatre;
	}
}
