package in.manojwagh.expensetracker.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import in.manojwagh.expensetracker.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

	boolean existsByEmail(String email);

	Optional<User> findByEmail(String email);

}
