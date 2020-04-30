package com.caller.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.caller.model.RegisterUser;
import com.caller.service.CallManagementService;

@RestController
@RequestMapping("/caller/v1")
public class CallManagementController {

	@Autowired
	private CallManagementService callManagementService;

	@Autowired
	private HttpServletRequest request;

	@PostMapping(value = "/register")
	public ResponseEntity<?> registerUser(@RequestBody RegisterUser user) {
		return ResponseEntity.ok(callManagementService.create(user, request));
	}

	@GetMapping(value = "/search/{phone_number}")
	public ResponseEntity<RegisterUser> searchByPhonenumber(
			@PathVariable(name = "phone_number", required = true) String phonenumber) {
		return ResponseEntity.ok(callManagementService.findByPhonenumber(phonenumber));
	}

	@GetMapping(value = "/search/user/{name}")
	public ResponseEntity<List<RegisterUser>> searchByName(@PathVariable(name = "name", required = true) String name) {
		return ResponseEntity.ok(callManagementService.findByName(name));
	}

	@PutMapping(value = "/spam/{phone_number}/{mark_spam}")
	public String markSpam(@PathVariable(name = "phone_number", required = true) String phonenumber,
			@PathVariable(name = "mark_spam", required = true) boolean spam) throws Exception {
		return callManagementService.update(phonenumber, spam);
	}
}