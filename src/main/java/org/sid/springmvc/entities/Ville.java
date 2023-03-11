package org.sid.springmvc.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data @AllArgsConstructor @NoArgsConstructor
public class Ville {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nom;
	@OneToMany(mappedBy = "ville",cascade=CascadeType.REMOVE)
	private List<User> users;
	@OneToMany(mappedBy = "ville",cascade=CascadeType.REMOVE)
	private List<Theatre> theatres;
	
	public Ville(String nom)
	{
		this.nom = nom;
	}
}
