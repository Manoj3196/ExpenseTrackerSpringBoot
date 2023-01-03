package in.manojwagh.expensetracker.config;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import in.manojwagh.expensetracker.entity.User;
import in.manojwagh.expensetracker.exceptions.ResourceNotFoundException;
import in.manojwagh.expensetracker.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User existingUser = userRepository.findByEmail(email)
				.orElseThrow();
		UserDetails user = new org.springframework.security.core.userdetails.User(existingUser.getEmail(),
				existingUser.getPassword(), new ArrayList<>());
		return user;
	}

}
