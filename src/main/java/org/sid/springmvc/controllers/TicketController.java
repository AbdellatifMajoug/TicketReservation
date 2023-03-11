
package org.sid.springmvc.controllers;

import org.sid.springmvc.dao.TicketRepository;
import org.sid.springmvc.entities.Ticket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class TicketController {
	
	@Autowired
	TicketRepository ticketRepository ;
	
	@GetMapping(path = "/gestionTicket")
	public String gestionProjection(Model model,Long idPr,@RequestParam(name="page",defaultValue="0")int page,
			@RequestParam(name="size",defaultValue="10")int size) {
		Page<Ticket> tickets = ticketRepository.listTicketsResByProjectionId(idPr,PageRequest.of(page, size));
		model.addAttribute("tickets", tickets);
		model.addAttribute("pages", new int[tickets.getTotalPages()]);
		model.addAttribute("size", size);
		model.addAttribute("currentPage", page);
		return "consulterTickets";
	}
}
