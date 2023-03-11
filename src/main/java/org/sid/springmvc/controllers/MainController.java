package org.sid.springmvc.controllers;

import java.util.ArrayList;
import java.util.List;

import org.sid.springmvc.entities.User;
import org.sid.springmvc.dao.CategorieRepository;
import org.sid.springmvc.dao.ImageRepository;
import org.sid.springmvc.dao.ProjectionRepository;
import org.sid.springmvc.dao.TicketRepository;
import org.sid.springmvc.dao.UserRepository;
import org.sid.springmvc.dao.VilleRepository;
import org.sid.springmvc.entities.Image;
import org.sid.springmvc.entities.Ticket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MainController {
	
	@Autowired
	private ImageRepository imageRepository;
	@Autowired
	private VilleRepository villeRepository;
	@Autowired
	private CategorieRepository categorieRepository;
	@Autowired
	private ProjectionRepository projectionRepository;
	@Autowired
	private TicketRepository ticketRepository;
	@Autowired
	private UserRepository userRepository;
	
	@GetMapping(path = {"/index","/"})
	public String index(Model model, Long idV,Long idC,String titre)
	{
		if(idC==null && idV==null)
		{
			model.addAttribute("projections",projectionRepository.findAll());
		}
		else if(idV != null && idC==null)
		{
			model.addAttribute("projections",projectionRepository.findProjectionByVilleId(idV));
		}
		else if(idC != null && idV==null)
		{
			model.addAttribute("projections",projectionRepository.findProjectionByCategorieId(idC));
		}
		else
		{
			model.addAttribute("projections",projectionRepository.findProjectionByVilleAndCategorieId(idV, idC));
		}
		if(titre!=null) 
		{
			model.addAttribute("projections", projectionRepository.findProjectionsBySpectacleName(titre));
		}
		model.addAttribute("villes",villeRepository.findAll());
		model.addAttribute("categories",categorieRepository.findAll());
		model.addAttribute("idVille",idV);
		model.addAttribute("idCategorie",idC);
		return "index";
	}
	
	@GetMapping(path = "/reservation")
	public String reservationTicket(Model model,Long idPr) {
		model.addAttribute("projection", projectionRepository.findById(idPr).get());
		model.addAttribute("nbTicketVIP", ticketRepository.listTicketsDispoByProjectionId(idPr, true).size());
		model.addAttribute("nbTicketNonVIP", ticketRepository.listTicketsDispoByProjectionId(idPr, false).size());
		return "reservationUser";
	}
	
	@PostMapping(path = "/payment")
	public String payment(Model model,int quantite,Long idPr,String vip) {
		List<Ticket> lt=new ArrayList<Ticket>();
		if(vip.equals("true"))
		{
			lt=ticketRepository.listTicketsDispoByProjectionId(idPr, true);
		}
		else
		{
			lt=ticketRepository.listTicketsDispoByProjectionId(idPr, false);
		}
		if(lt.size()<quantite)
		{
			return "redirect:/reservation?idPr="+idPr+"&error=La quantite doit etre inferieur au nombre de tickets";
		}
		else if(quantite==0)
		{
			return "redirect:/reservation?idPr="+idPr+"&error=La quantite est incorrect";
		}
		
		double prix = projectionRepository.findById(idPr).get().getPrix();
		
		if(vip.equals("true"))
		{
			prix = projectionRepository.findById(idPr).get().getPrixVIP();
		}
		
		List<Integer> lst = new ArrayList<Integer>();
		for(int i=1;i<=quantite;i++) {
			lst.add(i);
		}
		model.addAttribute("list", lst);
		model.addAttribute("prix", prix);
		model.addAttribute("vip", vip);
		model.addAttribute("idPr", idPr);
		model.addAttribute("quantite", quantite);
		model.addAttribute("projection", projectionRepository.findById(idPr).get());
		return "paymentPage";
	}
	
	@PostMapping(path = "/acheter")
	public String acheter(Model model,@RequestParam("quantite") int quantite, @RequestParam("idPr")Long idPr,@RequestParam("vip")String vip) {
		List<Ticket> lt=new ArrayList<Ticket>();
		if(vip.equals("true"))
		{
			lt=ticketRepository.listTicketsDispoByProjectionId(idPr, true);
		}
		else
		{
			lt=ticketRepository.listTicketsDispoByProjectionId(idPr, false);
		}
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User u = userRepository.findUserByUsername(auth.getName());
		int i=1; 
		for(Ticket t:lt) {
			if(i>quantite)
			{
				break;
			}
			t.setUser(u);
			t.setReservee(true);
			ticketRepository.save(t);
			i++;
		}
		return "redirect:/index";
	}
	
	@GetMapping(path = "/showTickets")
	public String showTk(Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User u = userRepository.findUserByUsername(auth.getName());
		model.addAttribute("tickets", u.getTickets());
		return "ticketsUser";
	}
	
	@GetMapping("/getImage/{fileId}")
	public ResponseEntity<ByteArrayResource> getimage(@PathVariable Long fileId){
		Image img = imageRepository.findById(fileId).get();
		return ResponseEntity.ok().contentType(MediaType.parseMediaType(img.getType()))
				.header(HttpHeaders.CONTENT_DISPOSITION,"attachement:filename=\""+img.getNom()+"\"")
				.body(new ByteArrayResource(img.getData()));
	}

}
