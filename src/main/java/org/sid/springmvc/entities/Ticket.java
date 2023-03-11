package org.sid.springmvc.entities;

import javax.persistence.*;

import lombok.*;

@Entity
@Data @AllArgsConstructor @NoArgsConstructor
public class Ticket {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private double prix;
	private boolean vip;
	private boolean reservee;
	@ManyToOne
	User user;
	@ManyToOne
	Projection projection;
	@ManyToOne
	Place place;
	
	public Ticket(double prix, boolean reservee,boolean vip, Place place,Projection projection) {
		this.prix = prix;
		this.reservee = reservee;
		this.place = place;
		this.vip = vip;
		this.projection = projection;
	}
}
