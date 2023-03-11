package org.sid.springmvc.controllers;

import java.util.List;

import org.sid.springmvc.dao.CategorieRepository;
import org.sid.springmvc.dao.SpectacleRepository;
import org.sid.springmvc.entities.Categorie;
import org.sid.springmvc.entities.Image;
import org.sid.springmvc.entities.Spectacle;
import org.sid.springmvc.services.ImageStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class SpectacleController {
	
	@Autowired
	private SpectacleRepository spectacleRepository;
	@Autowired
	private CategorieRepository categorieRepository;
	@Autowired
	private ImageStorageService imageStorageService;
	
	@GetMapping(path = "/gestionSpectacle")
	public String GestionTheatre(Model model ,
		@RequestParam(name="page" ,defaultValue = "0") int page,
		@RequestParam(name="size" ,defaultValue = "5") int size){
		List<Categorie> categories = categorieRepository.findAll();
		Page<Spectacle> spectacles = spectacleRepository.findAll((PageRequest.of(page, size)));
		model.addAttribute("spectacles",spectacles.getContent());
		model.addAttribute("pages",new int[spectacles.getTotalPages()]);
		model.addAttribute("currentPage",page);
		model.addAttribute("categories",categories);
		return "gestionSpectacles";
		}
	
	@PostMapping(path = "/saveSpectacle")
	public String saveSpectacle(String titre,double duree,String realisateur,Long categorie,String description,@RequestParam("photo") MultipartFile photo)
	{
		Categorie c = categorieRepository.findById(categorie).get();
		Image image = imageStorageService.saveFile(photo);
		Spectacle sp = new Spectacle(titre, duree, realisateur, description, image, c);
		spectacleRepository.save(sp);
		return "redirect:gestionSpectacle";
	}
	
	@GetMapping(path = "/deleteSpectacle")
	public String deleteSpectacle(Long id)
	{
		spectacleRepository.deleteById(id);
		return "redirect:gestionSpectacle";
	}
	
	@GetMapping(path = "editSpectacles")
	public String editTheatre(Model model,Long id) {
		Spectacle sp = spectacleRepository.findById(id).get();
		List<Categorie> categories = categorieRepository.findAll();
		model.addAttribute("spectacle", sp);
		model.addAttribute("categories", categories);
		return "editSpectacle";
	}
	@PostMapping(value="modifierSpectacle")
	public String modifierSpectacle(@RequestParam("id")Long id,String title,double duree,String realisateur,Long categorie,String description, @RequestParam("photo") MultipartFile photo) {
		Categorie c = categorieRepository.findById(categorie).get();
		Spectacle spectacle=spectacleRepository.findById(id).get();
		if(!photo.isEmpty()) {
			Image image = imageStorageService.saveFile(photo);
			spectacle.setImage(image);
		}
		spectacle.setCategorie(c);
		spectacle.setDescription(description);
		spectacle.setDuree(duree);
		spectacle.setRealisateur(realisateur);
		spectacle.setTitre(title);
		spectacleRepository.save(spectacle);
		return "redirect:gestionSpectacle";
	}


}
