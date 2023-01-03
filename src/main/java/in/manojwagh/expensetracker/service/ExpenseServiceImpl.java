package in.manojwagh.expensetracker.service;

import java.sql.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import in.manojwagh.expensetracker.entity.Expense;
import in.manojwagh.expensetracker.entity.User;
import in.manojwagh.expensetracker.exceptions.ResourceNotFoundException;
import in.manojwagh.expensetracker.repository.ExpenseRepository;

@Service
public class ExpenseServiceImpl implements ExpenseService {

	@Autowired
	private ExpenseRepository expenseRepo;

	@Autowired
	private UserService userService;

	public User getCurrentUser() {
		return userService.getLoggedInUser();
	}

	@Override
	public Page<Expense> getAllExpenses(Pageable page) {
		Long id = getCurrentUser().getId();
		return expenseRepo.findByUserId(id, page);
	}

	@Override
	public Expense getExpenseById(Long id) {

		Optional<Expense> expense = expenseRepo.findByUserIdAndId(getCurrentUser().getId(), id);
		if (expense.isPresent()) {
			return expense.get();
		} else {
			throw new ResourceNotFoundException("Expense not found with the id - " + id);
		}
	}

	@Override
	public String deleteById(Long id) {
		Optional<Expense> expense = expenseRepo.findByUserIdAndId(getCurrentUser().getId(), id);
		if (!expense.isPresent()) {
			throw new ResourceNotFoundException("Expense not found with the id - " + id);
		}
		expenseRepo.deleteById(id);
		return "Expense deleted successfully";

	}

	@Override
	public Expense saveExpense(Expense expense) {
		expense.setUser(userService.getLoggedInUser());
		expenseRepo.save(expense);
		return expense;
	}

	@Override
	public Expense updateExpense(Expense expense, Long id) {
		Expense existingExpense = expenseRepo.findByUserIdAndId(getCurrentUser().getId(), id)
				.orElseThrow(() -> new ResourceNotFoundException("Expense not found with the id - " + id));
		existingExpense.setName(expense.getName() != null ? expense.getName() : existingExpense.getName());
		existingExpense
				.setCategory(expense.getCategory() != null ? expense.getCategory() : existingExpense.getCategory());
		existingExpense.setDiscription(
				expense.getDiscription() != null ? expense.getDiscription() : existingExpense.getDiscription());
		existingExpense.setAmount(expense.getAmount() != null ? expense.getAmount() : existingExpense.getAmount());
		existingExpense.setDate(expense.getDate() != null ? expense.getDate() : existingExpense.getDate());
		return expenseRepo.save(existingExpense);
	}

	@Override
	public Page<Expense> readByCategory(String category, Pageable page) {

		return expenseRepo.findByUserIdAndCategory(getCurrentUser().getId(), category, page);
	}

	@Override
	public Page<Expense> readByNameContaining(String keyword, Pageable page) {

		return expenseRepo.findByUserIdAndNameContaining(getCurrentUser().getId(), keyword, page);
	}

	@Override
	public Page<Expense> readByDateBetween(Date startDate, Date endDate, Pageable page) {
		if (startDate == null) {
			startDate = new Date(0);
		}
		if (endDate == null) {
			endDate = new Date(System.currentTimeMillis());
		}
		return expenseRepo.findByUserIdAndDateBetween(getCurrentUser().getId(), startDate, endDate, page);
	}

}
