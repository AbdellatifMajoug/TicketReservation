package org.sid.springmvc.controllers;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import org.sid.springmvc.dao.ProjectionRepository;
import org.sid.springmvc.dao.SpectacleRepository;
import org.sid.springmvc.dao.TheatreRepository;
import org.sid.springmvc.dao.TicketRepository;
import org.sid.springmvc.entities.Place;
import org.sid.springmvc.entities.Projection;
import org.sid.springmvc.entities.Spectacle;
import org.sid.springmvc.entities.Theatre;
import org.sid.springmvc.entities.Ticket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ProjectionController {
	
	@Autowired
	SpectacleRepository spectacleRepository;
	@Autowired
	TheatreRepository theatreRepository;
	@Autowired
	private ProjectionRepository projectionRepository;
	@Autowired
	private TicketRepository ticketRepository;
	
	@GetMapping(path = "/gestionProjection")
	public String GestionTheatre(Model model , Long idTh, @RequestParam(name="page" ,defaultValue = "0") int page, 
			@RequestParam(name="size" ,defaultValue = "7") int size){
		List<Theatre> theatres = theatreRepository.findAll();
		List<Spectacle> spectacles = spectacleRepository.findAll();
		model.addAttribute("theatres", theatres);
		model.addAttribute("spectacles", spectacles);
		Page<Projection> projections=null;
		if(idTh == null)
		{
			projections= projectionRepository.findAll(PageRequest.of(page, size));
		}
		else
		{
			projections= projectionRepository.listProjectionsByTheatreId(idTh,PageRequest.of(page, size));
		}
		model.addAttribute("projections",projections.getContent());
		model.addAttribute("pages",new int[projections.getTotalPages()]);
		model.addAttribute("currentPage",page);
		model.addAttribute("projection",new Projection());
		model.addAttribute("idTh", idTh);
		return "gestionProjections";
		}
	
	@PostMapping(path = "saveProjection")
	public String saveProjection(Projection projection,String heure,BindingResult bindingResult)
	{
		if(bindingResult.hasErrors()) 
		{
			return "redirect:gestProjection";
		}
		DateFormat df = new SimpleDateFormat("HH:mm");
		try 
		{
			projection.setHeurePr(df.parse(heure));
		} 
		catch (ParseException e) 
		{
			e.printStackTrace();
		}
		Projection p = projectionRepository.save(projection);
		
		for(Place pl : p.getTheatre().getPlaces()) {
			Ticket t = new Ticket();
			t.setPlace(pl);
			t.setProjection(p);
			t.setReservee(false);
			if(pl.isVip()) {
				t.setPrix(p.getPrixVIP());
				t.setVip(true);
			}else {
				t.setPrix(p.getPrix());
				t.setVip(false);
			}
			ticketRepository.save(t);
		}
		return "redirect:gestionProjection";
	}
	
	@GetMapping(path="deleteProjection")
	public String deletePrrojection(Long id) {
		projectionRepository.deleteById(id);
		return "redirect:gestionProjection";
	}
	
	@GetMapping(path="editProjection")
	public String editProjection(Long id,Model model) {
		Projection projection = projectionRepository.findById(id).get();
		model.addAttribute("projection",projection);
		model.addAttribute("theatres", theatreRepository.findAll());
		model.addAttribute("spectacles", spectacleRepository.findAll());
		return "editProjection";
	}
}
