
package org.sid.springmvc.fakeDataTest;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.stream.Stream;
import javax.transaction.Transactional;
import org.apache.commons.io.FileUtils;
import org.sid.springmvc.dao.*;
import org.sid.springmvc.entities.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

@Service
@Transactional
public class TheatreInitServImpl implements ITheatreInitService {
	@Autowired
	private VilleRepository villeRepository;
	@Autowired
	private TheatreRepository theatreRepository;
	@Autowired
	private PlaceRepository placeRepository;
	@Autowired
	private SpectacleRepository spectacleRepository;
	@Autowired
	private CategorieRepository categorieRepository;
	@Autowired
	private ImageRepository imageRepository;
	@Autowired
	private ProjectionRepository projectionRepository;
	@Autowired
	private TicketRepository ticketRepository;
	@Autowired
	private UserRepository userRepository;
	private PasswordEncoder passwordEncoder;

	@Override
	public void initVilles() {
		Stream.of("Casablanca", "Fés", "Tanger", "Rabat", "Oujda").forEach(nameVille -> {
			villeRepository.save(new Ville(nameVille));
		});
	}

	@Override
	public void initTheatre() {
		villeRepository.findAll().forEach(v -> {
			if (v.getNom().equals("Casablanca")) 
			{
				theatreRepository.save(new Theatre("CasaArts", 600, 100, v));
			}
			if (v.getNom().equals("Rabat")) 
			{
				theatreRepository.save(new Theatre("Theatre Mohamed V", 480, 80, v));
				theatreRepository.save(new Theatre("Masrah alkabir", 750, 50, v));
			}
			if (v.getNom().equals("Oujda")) 
			{
				theatreRepository.save(new Theatre("Theatre Mohamed 6", 250, 40, v));
			}
			if (v.getNom().equals("Tanger")) 
			{
				theatreRepository.save(new Theatre("Teatro Cervantes", 620, 110, v));
			}
		});
	}

	@Override
	public void initPlaces() {
		theatreRepository.findAll().forEach(t -> {
			for (int i = 1; i <= t.getNombrePlacesVIP(); i++) {
				placeRepository.save(new Place(i, true, t));
			}
			for (int i = t.getNombrePlacesVIP() + 1; i <= t.getNombrePlaces(); i++) {
				placeRepository.save(new Place(i, false, t));
			}
		});

		
	}
	
	@Override
	public void initCategories() {
		Stream.of("opéra", "tragédie", "comédie", "théâtre de rue", "one-man show").forEach(nomCat -> {
			categorieRepository.save(new Categorie(nomCat));
		});

	}

	@Override
	public void initImages() {
		String url = "C:\\Users\\ppc\\Pictures\\imagesTst\\";
		Stream.of("les passagers.jpeg", "L’mbrouk.jpeg", "un autre ciel.jpeg", "don quichotte.jpeg", "Automne.jpeg",
				"La Chute.jpg", "Nezlou ela slamtkoum.jpeg", "Jaw jab.jpeg", "CHTATATA.png","home.png").forEach(img -> {
					File file = new File(url + img);
					try {
						byte[] content = FileUtils.readFileToByteArray(file);
						if (img.contains(".jpeg")) {
							imageRepository.save(new Image(img, "image/jpeg", content));
						} else if (img.contains(".png")) {
							imageRepository.save(new Image(img, "image/png", content));
						} else if (img.contains("jpg")) {
							imageRepository.save(new Image(img, "image/jpg", content));
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
					;
				});
	}

	@Override
	public void initSpectacles() {
		categorieRepository.findAll().forEach(c -> {
			imageRepository.findAll().forEach(img -> {
				if (c.getNom().equals("théâtre de rue")) {
					if (img.getNom().contains("passagers")) {
						spectacleRepository.save(new Spectacle("les passagers", 2, "ayoub el ayassi",
								"Inspirés par",
								img, c));
					}
				}
				if (c.getNom().equals("tragédie")) {
					if (img.getNom().contains("autre")) {
						spectacleRepository.save(new Spectacle("un autre ciel", 3.5, "mohamed elhor",
								"Inspirée de la pièce “Yerma” de",
								img, c)); // st
					} else if (img.getNom().contains("Automne")) { // st
						spectacleRepository.save(new Spectacle("Automne", 2, "Asmaa El Houri",
								"Automne, Troupe Anfass. Mise en scène de Asmaa El Houri", img, c));
					}
				}
				if (c.getNom().equals("comédie")) {
					if (img.getNom().contains("Nezlou ela slamtkoum")) {
						spectacleRepository.save(new Spectacle("Nezlou a‘la slamtkoum", 4.5, "Naïma Zitane",
								"La pièce jette ",
								img, c));
					} else if (img.getNom().contains("jab")) {
						spectacleRepository.save(new Spectacle("Jaw jab", 3.5, "Abdellatif Achraoui",
								"Vous avez envie ",
								img, c));
					}

				}

				if (c.getNom().equals("one-man show")) {
					if (img.getNom().contains("CHTATATA")) {
						spectacleRepository.save(new Spectacle("CHTATATA", 5, "EKO",
								"Après le succès ",
								img, c));
					}

				}

			});

		});

	}

	@Override
	public void initProjections() {
		double[] prices = new double[] { 100, 120, 150, 170, 200 };
		String[] heures = new String[] { "12:00", "15:00", "19:00", "21:00", "23:00" };
		int[] mois = new int[] { 1, 2, 3, 4, 5, 6 };
		int[] jours = new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25,
				26, 27, 28, 29 };
		DateFormat df = new SimpleDateFormat("HH:mm");
		theatreRepository.findAll().forEach(t -> {
			spectacleRepository.findAll().forEach(s -> {
				Projection projection = new Projection();
				projection.setSpectacle(s);
				projection.setTheatre(t);
				double pr = prices[new Random().nextInt(prices.length)];
				projection.setPrix(pr);
				projection.setPrixVIP(pr + 100);
				try {
					projection.setHeurePr(df.parse(heures[new Random().nextInt(heures.length)]));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Date d = new Date();
				d.setYear(2023-1900);
				d.setMonth(mois[new Random().nextInt(mois.length)]);
				d.setDate(jours[new Random().nextInt(jours.length)]);
				projection.setDatePr(d);
				projectionRepository.save(projection);
			});
		});
	}

	@Override
	public void initTickets() {
		projectionRepository.findAll().forEach(p -> {
			p.getTheatre().getPlaces().forEach(pl -> {
				if (pl.isVip()) {
					ticketRepository.save(new Ticket(p.getPrixVIP(), false, true, pl, p));
				} else {
					ticketRepository.save(new Ticket(p.getPrix(), false, false, pl, p));
				}

			});
		});

	}

	@Override
	public void initUsers() {
		User u = new User();
		u.setEmail("user@gmail.com");
		u.setUsername("user1");
		u.setNom("user");
		u.setPrenom("name");
		passwordEncoder = new BCryptPasswordEncoder();
		u.setPassword(passwordEncoder.encode("GMI123"));
		u.setVille(villeRepository.findById(1l).get());
		u.setRole("USER");
		userRepository.save(u);
		User u2 = new User();
		u2.setEmail("admin@gmail.com");
		u2.setNom("admin");
		u2.setPrenom("name");
		u2.setUsername("admin1");
		passwordEncoder = new BCryptPasswordEncoder();
		u2.setPassword(passwordEncoder.encode("GMI123"));
		u2.setVille(villeRepository.findById(2l).get());
		u2.setRole("ADMIN");
		userRepository.save(u2);

	}
}
