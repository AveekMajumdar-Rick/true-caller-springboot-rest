package com.caller.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.caller.config.JwtTokenUtil;
import com.caller.model.JwtRequest;
import com.caller.model.JwtResponse;
import com.caller.service.JwtUserDetailsService;

@RestController
@CrossOrigin
@RequestMapping("/caller/v1")
public class JwtAuthenticationController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private JwtUserDetailsService userDetailsService;

	@PostMapping(value = "/authenticate")
	public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {

		// Authenticate User phone number and Password
		authenticate(authenticationRequest.getPhonenumber(), authenticationRequest.getPassword());

		//Loading user to create Authorization Token
		UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getPhonenumber());

		String token = jwtTokenUtil.generateToken(userDetails);

		Date expirationDate = jwtTokenUtil.getExpirationDateFromToken(token);

		Date issueDate = jwtTokenUtil.getLoginDateFromToken(token);
		
		return ResponseEntity.ok(new JwtResponse(expirationDate,issueDate,token));
	}

	@PostMapping(value = "/verify")
	public ResponseEntity<?> verifyUser(@RequestBody JwtRequest user){
		return ResponseEntity.ok(userDetailsService.save(user));
	}
	
	private void authenticate(String username, String password) throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}
	}
}