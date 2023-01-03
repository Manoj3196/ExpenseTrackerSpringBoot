package in.manojwagh.expensetracker.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.manojwagh.expensetracker.config.CustomUserDetailsService;
import in.manojwagh.expensetracker.config.JwtUtil;
import in.manojwagh.expensetracker.entity.JwtResponse;
import in.manojwagh.expensetracker.entity.LoginModel;
import in.manojwagh.expensetracker.entity.User;
import in.manojwagh.expensetracker.entity.UserModel;
import in.manojwagh.expensetracker.service.UserService;

@RestController
@RequestMapping
public class AuthController {

	@Autowired
	private UserService userService;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private CustomUserDetailsService userDetailsService;

	@Autowired
	private JwtUtil jwtTokenUtil;

	@PostMapping("/logins")
	public ResponseEntity<?> getLogin(@RequestBody LoginModel loginModel) throws Exception {

		try {
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(loginModel.getEmail(), loginModel.getPassword()));

			// SecurityContextHolder.getContext().setAuthentication(authentication);
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}
		final UserDetails userDetails = userDetailsService.loadUserByUsername(loginModel.getEmail());

		final String token = jwtTokenUtil.generateToken(userDetails);

		return new ResponseEntity<>(new JwtResponse(token), HttpStatus.OK);
	}

	@PostMapping("/register")
	public ResponseEntity<User> createUser(@Valid @RequestBody UserModel userModel) {
		return new ResponseEntity<User>(userService.createUser(userModel), HttpStatus.CREATED);

	}

}
