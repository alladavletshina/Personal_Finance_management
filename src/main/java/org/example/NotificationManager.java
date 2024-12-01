package org.example;
import java.util.Map;

public class NotificationManager {
    private Transaction transaction;
    private Wallet wallet;

    public NotificationManager(Transaction transaction, Wallet wallet) {
        this.transaction = transaction;
        this.wallet = wallet;
    }

    public void checkExceedingExpenses() {
        double totalIncome = transaction.getTotalIncome();
        double totalExpenses = transaction.getTotalExpense();

        if (totalExpenses > totalIncome) {
            System.out.println();
            System.out.printf("Внимание! Ваши расходы %,.2f превышают ваши доходы %,.2f.\n", totalExpenses, totalIncome);
        }
    }

    public void checkBudgetLimitExceeded() {
        for (Map.Entry<String, Double> entry : wallet.budgets.entrySet()) {
            String category = entry.getKey();
            double limit = entry.getValue();
            double expense = transaction.getSpentAmount(category);

            if (expense > limit) {
                System.out.printf("Превышен лимит бюджета по категории '%s': лимит %,.2f, расходы %,.2f.\n", category, limit, expense);;
            }
        }
    }
}
