
package org.sid.springmvc;

import org.sid.springmvc.fakeDataTest.ITheatreInitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TicketReservationApplication  implements CommandLineRunner{
	@Autowired
	private ITheatreInitService iTheatreInitService;


	public static void main(String[] args) {
		SpringApplication.run(TicketReservationApplication.class, args);
	}
	@Override
	public void run(String... args) throws Exception {
		/*iTheatreInitService.initVilles();
		iTheatreInitService.initCategories();
		iTheatreInitService.initTheatre(); 
		iTheatreInitService.initImages();
		iTheatreInitService.initSpectacles();
		iTheatreInitService.initPlaces();
		iTheatreInitService.initProjections();
		iTheatreInitService.initTickets();
		iTheatreInitService.initUsers();*/
	}

}
