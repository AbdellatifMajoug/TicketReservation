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
@Data @AllArgsConstructor @NoArgsConstructor
public class Theatre {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nom;
	private int nombrePlaces;
	private int nombrePlacesVIP;
	@ManyToOne
	Ville ville;
	@OneToMany(mappedBy = "theatre",cascade=CascadeType.REMOVE)
	private List<Place> places;
	@OneToMany(mappedBy = "theatre",cascade=CascadeType.REMOVE)
	private List<Projection> projections;
	
	public Theatre(String nom, int nombrePlaces,int nombrePlacesVIP,Ville ville) {
		this.nom = nom;
		this.nombrePlaces = nombrePlaces;
		this.nombrePlacesVIP = nombrePlacesVIP;
		this.ville = ville;
	}

}
