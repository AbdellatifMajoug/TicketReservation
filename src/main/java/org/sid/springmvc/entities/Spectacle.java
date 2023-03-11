package org.sid.springmvc.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data @AllArgsConstructor @NoArgsConstructor
public class Spectacle {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String titre;
	private Double duree;
	private String realisateur;
	@Column(length = 5000)
	private String description;
	@ManyToOne
	Categorie categorie;
	@OneToMany(mappedBy = "spectacle",cascade=CascadeType.REMOVE)
	private List<Projection> projections;
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	@JoinColumn(name="img_id")
	private Image image;
	
	public Spectacle(String titre, double duree, String realisateur, String description, Image image,
			Categorie categorie) {
		this.titre = titre;
		this.duree = duree;
		this.realisateur = realisateur;
		this.description = description;
		this.image = image;
		this.categorie = categorie;
	}

}
