package org.sid.springmvc.entities;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data @AllArgsConstructor @NoArgsConstructor
public class Image {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nom;
	private String type;
	@Lob
	private byte[] data;
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE,mappedBy = "image")
	private Spectacle spectacle;
	
	public Image(String nom, String type, byte[] data) {
		this.nom = nom;
		this.type = type;
		this.data = data;
	}

}
