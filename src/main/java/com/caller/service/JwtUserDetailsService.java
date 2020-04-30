package com.caller.service;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.caller.dao.UserDao;
import com.caller.exceptionMapper.RecordNotFoundException;
import com.caller.model.DAOUser;
import com.caller.model.JwtRequest;

@Service
public class JwtUserDetailsService implements UserDetailsService {

	@Autowired
	private UserDao userDao;

	@Autowired
	private PasswordEncoder bcryptEncoder;

	public static final Logger log = LoggerFactory.getLogger(JwtUserDetailsService.class);

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		DAOUser user = userDao.findByUsername(username);
		if (user == null) {
			log.error("User not found for username : " + username);
			throw new RecordNotFoundException("User not found for username : " + username);
		}
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
				new ArrayList<>());
	}

	public DAOUser save(JwtRequest authenticationRequest) {
		DAOUser newUser = new DAOUser();
		newUser.setUsername(authenticationRequest.getPhonenumber());
		newUser.setPassword(bcryptEncoder.encode(authenticationRequest.getPassword()));
		return userDao.save(newUser);
	}

	public DAOUser fetchByUsername(String username) {
		return userDao.findByUsername(username);
	}
}