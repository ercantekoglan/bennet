package com.partymanager.finalproject.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.partymanager.finalproject.domain.User;
import com.partymanager.finalproject.service.UserService;

@Controller
public class LoginController {

	@Autowired
	private AuthenticationManager authManager;
	@Autowired
	private UserService userService;

	@GetMapping("/login")
	public String getLogin(ModelMap model) {

		return "login";
	}

	@PostMapping("/login")
	public ResponseEntity<HttpStatus> login(@RequestBody User user) throws Exception {
		Authentication auth;

		try {
			auth = authManager
					.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
			SecurityContextHolder.getContext().setAuthentication(auth);
		} catch (BadCredentialsException e) {
			throw new Exception("Authentication Failed");
		}

		return new ResponseEntity<HttpStatus>(HttpStatus.OK);

	}


	@GetMapping("/player")
	public String getPlayer(ModelMap model, @PathVariable Long userId) {
		// you can put user info here
		User userById = userService.findById(userId);
		model.put("userById", userById);

		return "player";
	}
}
