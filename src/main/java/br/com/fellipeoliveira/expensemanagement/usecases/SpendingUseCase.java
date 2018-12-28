package br.com.fellipeoliveira.expensemanagement.usecases;

import br.com.fellipeoliveira.expensemanagement.domains.Spending;
import br.com.fellipeoliveira.expensemanagement.gateways.SpendingGateway;
import br.com.fellipeoliveira.expensemanagement.gateways.http.request.SpendingRequest;
import br.com.fellipeoliveira.expensemanagement.gateways.http.response.SpendingResponse;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SpendingUseCase {

  private final SpendingGateway spendingGateway;

  public List<SpendingResponse> findAllExpenses() {
    return spendingListResponseBuilder(spendingGateway.findAllExpenses());
  }

  public List<SpendingResponse> findAllExpensesByDate(LocalDate date) {
    return spendingListResponseBuilder(spendingGateway.findAllExpensesByDate(date));
  }

  public SpendingResponse findExpense(String id) {
    return spendingResponseBuilder(spendingGateway.findExpenseById(id));
  }

  public List<String> findCategories(String query) {
    return spendingGateway.findCategories(query);
  }

  public void saveSpent(SpendingRequest spendingRequest) {
    if (spendingRequest.getId() != null && spendingRequest.getCategory().isEmpty()) {
      spendingGateway
          .findExpensesByUserCode(spendingRequest.getUserCode())
          .stream()
          .filter(
              spending ->
                  spending.getDescription().equalsIgnoreCase(spendingRequest.getDescription()))
          .findFirst()
          .ifPresent(spending -> spendingRequest.setCategory(spending.getCategory()));
    }
    spendingGateway.save(spendingBuilder(spendingRequest));
  }

  private Spending spendingBuilder(SpendingRequest spendingRequest) {
    return Spending.builder()
        .description(spendingRequest.getDescription())
        .category(spendingRequest.getCategory())
        .date(spendingRequest.getDate() != null ? spendingRequest.getDate().toLocalDate() : null)
        .time(spendingRequest.getDate() != null ? spendingRequest.getDate().toLocalTime() : null)
        .userCode(spendingRequest.getUserCode())
        .value(spendingRequest.getValue())
        .build();
  }

  private List<SpendingResponse> spendingListResponseBuilder(List<Spending> spending) {
    return spending
        .stream()
        .map(spent -> spendingResponseBuilder(spent))
        .collect(Collectors.toList());
  }

  private SpendingResponse spendingResponseBuilder(Spending spending) {
    return SpendingResponse.builder()
        .id(spending.getId())
        .description(spending.getDescription())
        .category(spending.getCategory())
        .userCode(spending.getUserCode())
        .date(spending.getDate() != null ? spending.getDate().atTime(spending.getTime()) : null)
        .value(spending.getValue())
        .build();
  }
}
