package br.com.santander.app.converter;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import br.com.santander.app.dto.ExpenseDTO;
import br.com.santander.app.model.Category;
import br.com.santander.app.model.Expense;

public class ExpenseConverter {

	public static Expense fromDTO(final ExpenseDTO dto) {
		final Expense expense= new Expense();
		final Category category= new Category();
		expense.setCategory(category);
		expense.setId(dto.getCode());
		expense.getCategory().setDescription(dto.getDescription());
		expense.setUserCode(dto.getUserCode());
		expense.setExpenseDate(dto.getDate());
		expense.setValue(dto.getValue());
		expense.setVersion(dto.getVersion());
		return expense;
	}

	public static ExpenseDTO toDTO(final Expense model) {
		final ExpenseDTO expenseDTO= new ExpenseDTO();
		expenseDTO.setCode(model.getId());
		if(model.getCategory() != null) {
			expenseDTO.setDescription(model.getCategory().getDescription());
		}
		expenseDTO.setUserCode(model.getUserCode());
		expenseDTO.setDate(model.getExpenseDate());
		expenseDTO.setValue(model.getValue());
		expenseDTO.setVersion(model.getVersion());
		return expenseDTO;
	}

	public static List<ExpenseDTO> toDTO(final List<Expense> list){
		final List<ExpenseDTO> results = new ArrayList<>();
		for (final Expense expense : list){
			results.add(toDTO(expense));
		}
		return results;
	}

	public static ExpenseDTO toDTO(final Map<String, String> params) throws ParseException {
		final ExpenseDTO dto = new ExpenseDTO();

		for (final Map.Entry<String, String> entry : params.entrySet()) {
			if(entry.getKey().equalsIgnoreCase("userCode")) {
				dto.setUserCode(Long.parseLong(entry.getValue()));
			}
			if(entry.getKey().equalsIgnoreCase("date")) {
				final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
				dto.setDate(LocalDateTime.parse(entry.getValue(), formatter));
			}
		}
		return dto;
	}
}
