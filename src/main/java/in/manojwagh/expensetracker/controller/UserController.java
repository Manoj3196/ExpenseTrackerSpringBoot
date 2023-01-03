package in.manojwagh.expensetracker.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.manojwagh.expensetracker.entity.User;
import in.manojwagh.expensetracker.entity.UserModel;
import in.manojwagh.expensetracker.service.UserService;

@RestController
@RequestMapping("user")
public class UserController {

	@Autowired
	private UserService userService;
	

	@GetMapping()
	public ResponseEntity<User> readUserById() {
		return new ResponseEntity<User>(userService.readUser(), HttpStatus.OK);
	}

	@PutMapping()
	public ResponseEntity<User> updateUser(@RequestBody UserModel userModel) {
		return new ResponseEntity<User>(userService.updateUser(userModel), HttpStatus.OK);
	}

	@DeleteMapping()
	public ResponseEntity<HttpStatus> deleteUser() {
		userService.deleteUser();
		return new ResponseEntity<HttpStatus>(HttpStatus.NO_CONTENT);
	}
}
