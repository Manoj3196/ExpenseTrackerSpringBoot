package in.manojwagh.expensetracker.service;

import java.sql.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import in.manojwagh.expensetracker.entity.Expense;

public interface ExpenseService {

	Page<Expense> getAllExpenses(Pageable page);

	Expense getExpenseById(Long id);

	String deleteById(Long id);

	Expense saveExpense(Expense expense);

	Expense updateExpense(Expense expense, Long id);

	Page<Expense> readByCategory(String category, Pageable page);

	Page<Expense> readByNameContaining(String keyword, Pageable page);

	Page<Expense> readByDateBetween(Date startDate, Date endDate, Pageable page);

}
