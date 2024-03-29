package com.caller.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.caller.config.JwtTokenUtil;
import com.caller.dao.CallManagementDao;
import com.caller.exceptionMapper.RecordNotFoundException;
import com.caller.exceptionMapper.UnAuthorizedException;
import com.caller.model.RegisterUser;

import io.jsonwebtoken.ExpiredJwtException;

@Service
public class CallManagementService {

	@Autowired
	private CallManagementDao callManagementDao;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	public static final Logger log = LoggerFactory.getLogger(CallManagementService.class);

	/**
	 * register new user
	 * 
	 * @param user
	 * @param request
	 * @return
	 */
	public RegisterUser create(RegisterUser user, HttpServletRequest request) {
		if (user == null) {
			log.error("User not found");
			throw new RecordNotFoundException("User not found send proper request");
		}
		RegisterUser existingUser = callManagementDao.findUserByPhonenumber(user.getPhonenumber());
		if (existingUser != null) {
			log.error("User already exists with phonenumber : " + user.getPhonenumber());
			throw new RecordNotFoundException("User already exists with phonenumber : " + user.getPhonenumber());
		}
		// match the logged-in user
		String username = getUsernameFromAuthToken(request);

		if (!username.equals(user.getPhonenumber())) {
			log.error("User is not allowed to register with phonenumber : " + user.getPhonenumber());
			throw new UnAuthorizedException("User is not allowed to register with phonenumber : "
					+ user.getPhonenumber() + " for logged-in user : " + username);
		}
		return callManagementDao.save(user);
	}

	/**
	 *find user by phone number 
	 * 
	 * @param phonenumber
	 * @return
	 */
	public RegisterUser findByPhonenumber(String phonenumber) {
		RegisterUser existingUser = callManagementDao.findUserByPhonenumber(phonenumber);
		if (existingUser == null) {
			log.error("User not found with phonenumber : " + phonenumber);
			throw new RecordNotFoundException("User not found with phonenumber : " + phonenumber);
		}
		return existingUser;
	}

	/**
	 * find user by name 
	 * 
	 * @param name
	 * @return
	 */
	public List<RegisterUser> findByName(String name) {
		List<RegisterUser> userList = callManagementDao.findByName(name);
		if (CollectionUtils.isEmpty(userList)) {
			log.error("User not found for name : " + name);
			throw new RecordNotFoundException("User not found for name : " + name);
		}
		return userList;
	}

	/**
	 * 
	 * mark user as spam
	 * 
	 * @param phonenumber
	 * @param spam
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public String update(String phonenumber, boolean spam, HttpServletRequest request) throws Exception {
		RegisterUser existingUser = findByPhonenumber(phonenumber);
		try {
			// match the logged-in user
			String username = getUsernameFromAuthToken(request);

			if (username.equals(existingUser.getPhonenumber())) {
				log.error("User is not allowed to mark spam with phonenumber : " + existingUser.getPhonenumber());
				throw new UnAuthorizedException("User is not allowed to mark spam with phonenumber : "
						+ existingUser.getPhonenumber() + " for logged-in user : " + username);
			}
			callManagementDao.update(existingUser.getPhonenumber(), spam);
		} catch (Exception e) {
			log.error("unable to mark spam for phonenumber : " + phonenumber);
			throw new Exception("unable to mark spam for phonenumber : " + phonenumber);
		}
		if (spam)
			return "User marked as spam successfully";
		else
			return "User marked as not spam successfully";
	}

	/**
	 * 
	 * get username from Authorization token
	 * 
	 * @param request
	 * @return
	 */
	private String getUsernameFromAuthToken(HttpServletRequest request) {
		final String requestTokenHeader = request.getHeader("Authorization");

		String username = null;
		String jwtToken = null;
		// JWT Token is in the form "Bearer token". Remove Bearer word and get
		// only the Token
		if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
			jwtToken = requestTokenHeader.substring(7);
			try {
				username = jwtTokenUtil.getUsernameFromToken(jwtToken);
			} catch (IllegalArgumentException e) {
				log.warn("Unable to get JWT Token");
			} catch (ExpiredJwtException e) {
				log.warn("JWT Token has expired");
			}
		} else {
			log.warn("JWT Token does not begin with Bearer String");
		}
		return username;
	}

	/**
	 * block user 
	 * 
	 * @param phonenumber
	 * @param block
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public String blockUser(String phonenumber, boolean block, HttpServletRequest request) throws Exception {
		RegisterUser existingUser = findByPhonenumber(phonenumber);
		try {
			// match the logged-in user
			String username = getUsernameFromAuthToken(request);

			if (username.equals(existingUser.getPhonenumber())) {
				log.error("User is not allowed to mark block with phonenumber : " + existingUser.getPhonenumber());
				throw new UnAuthorizedException("User is not allowed to mark block with phonenumber : "
						+ existingUser.getPhonenumber() + " for logged-in user : " + username);
			}
			callManagementDao.block(existingUser.getPhonenumber(), block);
		} catch (Exception e) {
			log.error("unable to block for phonenumber : " + phonenumber);
			throw new Exception("unable to block for phonenumber : " + phonenumber);
		}
		if (block)
			return "User blocked successfully";
		else
			return "User is not blocked";
	}
}
