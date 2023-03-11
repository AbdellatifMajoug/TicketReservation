
package org.sid.springmvc.controllers;

import java.util.List;

import org.sid.springmvc.dao.TheatreRepository;
import org.sid.springmvc.dao.VilleRepository;
import org.sid.springmvc.entities.Theatre;
import org.sid.springmvc.entities.Ville;
import org.sid.springmvc.services.TheatreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class TheatreController {
	
	@Autowired
	private TheatreRepository theatreRepository;
	@Autowired
	private TheatreService theatreService;
	@Autowired
	private VilleRepository villeRepository;
	
	@GetMapping(path = "/gestionTheatre")
	public String GestionTheatre(Model model ,
		@RequestParam(name="page" ,defaultValue = "0") int page,
		@RequestParam(name="size" ,defaultValue = "5") int size){
		List<Ville> villes = villeRepository.findAll();
		Page<Theatre> theatres = theatreRepository.findAll((PageRequest.of(page, size)));
		model.addAttribute("theatres",theatres.getContent());
		model.addAttribute("pages",new int[theatres.getTotalPages()]);
		model.addAttribute("currentPage",page);
		model.addAttribute("villes",villes);
		return "gestionTheatres";
		}
	
	@PostMapping(path = "saveTheatre")
	public String saveTheatre(String nom,int nombrePlaces,int nombrePlacesVIP,Long ville) {
		Ville v = villeRepository.findById(ville).get();
		Theatre theatre = new Theatre(nom,nombrePlaces,nombrePlacesVIP,v);
		theatreService.saveTheatre(theatre);
		return "redirect:gestionTheatre";
	}
	
	@GetMapping(path = "deleteTheatre")
	public String deleteTheatre(Long id)
	{
		theatreRepository.deleteById(id);
		return "redirect:gestionTheatre";
	}
	
	@GetMapping(path = "editTheatre")
	public String editTheatre(Model model,Long id) {
		Theatre th = theatreRepository.findById(id).get();
		List<Ville> villes = villeRepository.findAll();
		model.addAttribute("theatre", th);
		model.addAttribute("villes", villes);
		return "editTheatre";
	}
	
	@PostMapping(path = "modifierTheatre")
	public String modifierTheatre(Theatre th)
	{
		theatreRepository.save(th);
		return "redirect:gestionTheatre";
	}

}
