
package org.sid.springmvc.security;



import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.sid.springmvc.dao.UserRepository;
import org.sid.springmvc.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class SecurityController {
	@Autowired
	private UserRepository userRepository;
	@GetMapping("/login")
	public String login() {
		return "login";
	}
	@GetMapping("/logout")
	public String logout(HttpServletRequest request) throws ServletException {
		request.logout();
		return "redirect:/index";
	}
	
	@GetMapping("/signup")
	public String signup() {
		return "registration";
	}
	
	@PostMapping(value="/registration")
	public String registration(String nom,String prenom,String email,String username,String password) {
		User u = new User();
		u.setEmail(email);
		u.setNom(nom);
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		u.setPassword(passwordEncoder.encode(password));
		u.setPrenom(prenom);
		u.setUsername(username);
		u.setRole("USER");
		userRepository.save(u);
		return "redirect:login";
	}
}

