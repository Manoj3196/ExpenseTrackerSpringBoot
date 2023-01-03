package in.manojwagh.expensetracker.entity;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class UserModel {

	@NotBlank(message = "Name must not be empty!")
	private String name;

	@NotNull(message = "Email must not be empty!")
	@Email(message = "Please enter valid email!")
	private String email;

	@NotNull(message = "Password must not be empty!")
	@Size(min = 5, message = "Password should be atleast 5 Characters!")
	private String password;

	@NotNull(message = "Age must not be empty!")
	private Long age;

}
