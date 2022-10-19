package cz.muni.fi.pv168.cashflow.data;

import cz.muni.fi.pv168.cashflow.business.model.Category;
import cz.muni.fi.pv168.cashflow.business.model.Currency;
import cz.muni.fi.pv168.cashflow.business.model.Template;
import cz.muni.fi.pv168.cashflow.business.model.Transaction;
import cz.muni.fi.pv168.cashflow.business.model.TransactionType;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class TestData {

    private static final List<Category> CATEGORIES = List.of(
            new Category(UUID.randomUUID().toString(), "General"),
            new Category(UUID.randomUUID().toString(), "Groceries"),
            new Category(UUID.randomUUID().toString(), "Shopping"),
            new Category(UUID.randomUUID().toString(), "Restaurants"),
            new Category(UUID.randomUUID().toString(), "Transport"),
            new Category(UUID.randomUUID().toString(), "Travel"),
            new Category(UUID.randomUUID().toString(), "Entertainment"),
            new Category(UUID.randomUUID().toString(), "Health"),
            new Category(UUID.randomUUID().toString(), "Services")
    );

    private static final List<Currency> CURRENCIES = List.of(
            new Currency(UUID.randomUUID().toString(), "Czech Koruna", "CZK", 0),
            new Currency(UUID.randomUUID().toString(), "Euro", "EUR", 25.5),
            new Currency(UUID.randomUUID().toString(), "US Dollar", "USD", 21.5),
            new Currency(UUID.randomUUID().toString(), "British Pound", "GBP", 30.5)
    );

    private static final List<Transaction> TRANSACTIONS = List.of(
            new Transaction(UUID.randomUUID().toString(), "John Doe", TransactionType.INCOME, 100.0, CURRENCIES.get(0), CATEGORIES.get(0), LocalDate.of(2023, 11, 28), "var", "const", "spec", "message"),
            new Transaction(UUID.randomUUID().toString(), "Alice Smith", TransactionType.INCOME, 250.0, CURRENCIES.get(1), CATEGORIES.get(1), LocalDate.of(2023, 5, 13), "var", "const", "spec", "message"),
            new Transaction(UUID.randomUUID().toString(), "Bob Johnson", TransactionType.OUTCOME, 50.0, CURRENCIES.get(2), CATEGORIES.get(2), LocalDate.now(), "var", "const", "spec", "message")
    );

    private static final List<Template> TEMPLATES = List.of(
            new Template(UUID.randomUUID().toString(), "Rohlik.cz", "John Doe", TransactionType.OUTCOME, 100.0, CURRENCIES.get(0), CATEGORIES.get(0), "var", "const", "spec", "message"),
            new Template(UUID.randomUUID().toString(), "Zalando", "Alice Smith", TransactionType.OUTCOME, 250.0, CURRENCIES.get(1), CATEGORIES.get(1), "var", "const", "spec", "message"),
            new Template(UUID.randomUUID().toString(), "Ryanair", "Bob Johnson", TransactionType.OUTCOME, 500.0, CURRENCIES.get(2), CATEGORIES.get(2), "var", "const", "spec", "message")
    );

    public List<Category> getCategories() {
        return CATEGORIES;
    }

    public List<Currency> getCurrencies() {
        return CURRENCIES;
    }

    public List<Transaction> getTransactions() {
        return TRANSACTIONS;
    }

    public List<Template> getTemplates() {
        return TEMPLATES;
    }
}
