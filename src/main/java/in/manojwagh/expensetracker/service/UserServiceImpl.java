package in.manojwagh.expensetracker.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import in.manojwagh.expensetracker.entity.User;
import in.manojwagh.expensetracker.entity.UserModel;
import in.manojwagh.expensetracker.exceptions.ItemAlreadyExistsException;
import in.manojwagh.expensetracker.exceptions.ResourceNotFoundException;
import in.manojwagh.expensetracker.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public User createUser(UserModel userModel) {
		if (userRepository.existsByEmail(userModel.getEmail())) {
			throw new ItemAlreadyExistsException("User already Registerd with email :" + userModel.getEmail());
		}
		User user = new User();
		UserModel model = new UserModel();
		model.setEmail(userModel.getEmail());
		model.setPassword(passwordEncoder.encode(userModel.getPassword()));
		model.setName(userModel.getName());
		model.setAge(userModel.getAge());
		BeanUtils.copyProperties(model, user);
		return userRepository.save(user);
	}

	@Override
	public User readUser() {
		Long id = getLoggedInUser().getId();

		return userRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("User not found with id : " + id));
	}

	@Override
	public User updateUser(UserModel user) {
		User existingUser = readUser();
		if (userRepository.existsByEmail(user.getEmail())) {
			if (existingUser.getEmail().equals(user.getEmail())) {

			} else {
				throw new ItemAlreadyExistsException("User already Registerd with email :" + user.getEmail());
			}
		}
		existingUser.setEmail(user.getEmail() != null ? user.getEmail() : existingUser.getEmail());
		existingUser.setAge(user.getAge() != null ? user.getAge() : existingUser.getAge());
		existingUser.setName(user.getName() != null ? user.getName() : existingUser.getName());
		existingUser.setPassword(
				user.getPassword() != null ? passwordEncoder.encode(user.getPassword()) : existingUser.getPassword());
		return userRepository.save(existingUser);

	}

	@Override
	public void deleteUser() {
		User user = readUser();
		userRepository.delete(user);

	}

	@Override
	public User getLoggedInUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String email = authentication.getName();

		return userRepository.findByEmail(email).orElseThrow();
	}

}
