package in.manojwagh.expensetracker.controller;

import java.sql.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import in.manojwagh.expensetracker.entity.Expense;
import in.manojwagh.expensetracker.service.ExpenseService;

@RestController
@RequestMapping("/expense")
public class ExpenseController {

	@Autowired
	private ExpenseService expenseService;

	@GetMapping("date")
	public List<Expense> FilterByDate(@RequestParam(required = false) Date startDate,
			@RequestParam(required = false) Date endDate, Pageable page) {

		return expenseService.readByDateBetween(startDate, endDate, page).toList();

	}

	@GetMapping("category")
	public List<Expense> getByCategoy(@RequestParam String category, Pageable page) {
		return expenseService.readByCategory(category, page).toList();
	}

	@GetMapping("name")
	public List<Expense> getByNameContaining(@RequestParam String keyword, Pageable page) {
		return expenseService.readByNameContaining(keyword, page).toList();
	}

	@GetMapping()
	public List<Expense> getExpenses(Pageable page) {

		return expenseService.getAllExpenses(page).toList();
	}

	@GetMapping("{id}")
	public Expense getExpenseById(@PathVariable("id") Long id) {
		return expenseService.getExpenseById(id);
	}

	/*
	 * @GetMapping("getExpenses") public Expense getExpensesById(@RequestParam("id")
	 * Long id) { return expenseService.getExpenseById(id); }
	 */

	@PostMapping()
	public Expense saveExpense(@Valid @RequestBody Expense expense) {
		return expenseService.saveExpense(expense);
	}

	@DeleteMapping("{id}")
	public String deleteExpenseById(@PathVariable Long id) {
		return expenseService.deleteById(id);
	}

	@PutMapping("{id}")
	public Expense updateExpense(@RequestBody Expense expense, @PathVariable Long id) {
		return expenseService.updateExpense(expense, id);

	}

}
