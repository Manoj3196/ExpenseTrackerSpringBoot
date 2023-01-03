package in.manojwagh.expensetracker.service;

import in.manojwagh.expensetracker.entity.User;
import in.manojwagh.expensetracker.entity.UserModel;

public interface UserService {

	User createUser(UserModel userModel);

	User readUser();

	User updateUser(UserModel user);
	
	void deleteUser();
	
	User getLoggedInUser();
}
