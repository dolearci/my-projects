package cz.muni.fi.pv168.cashflow.export.json;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.json.JsonMapper;
import cz.muni.fi.pv168.cashflow.business.model.Category;
import cz.muni.fi.pv168.cashflow.business.model.Currency;
import cz.muni.fi.pv168.cashflow.business.model.Transaction;
import cz.muni.fi.pv168.cashflow.business.model.TransactionType;
import cz.muni.fi.pv168.cashflow.business.service.export.DataManipulationException;
import cz.muni.fi.pv168.cashflow.business.service.export.batch.Batch;
import cz.muni.fi.pv168.cashflow.business.service.export.batch.BatchImporter;
import cz.muni.fi.pv168.cashflow.business.service.export.format.Format;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.StreamSupport;

public class BatchJsonImporter implements BatchImporter {

    private static final JsonMapper JSON_MAPPER = new JsonMapper();
    private static final Format FORMAT = new Format("JSON", List.of("json"));

    @Override
    public Batch importBatch(String filePath, Iterable<Currency> currencies, Iterable<Category> categories) {
        try {
            JsonNode jacksonList = JSON_MAPPER.readTree(new File(filePath));
            var newCategories = new HashMap<String, Category>();
            var newCurrencies = new HashMap<String, Currency>();
            List<Transaction> transactions = new ArrayList<>();

            for (JsonNode transaction : jacksonList.get("transactions")) {
                transactions.add(parseTransaction(transaction, newCategories, newCurrencies, categories, currencies));
            }
            return new Batch(transactions, newCategories.values(), newCurrencies.values());
        } catch (Exception e) {
            throw new DataManipulationException("Unable to read file: " + e.getMessage(), e);
        }
    }

    @Override
    public Format getFormat() {
        return FORMAT;
    }

    private Transaction parseTransaction(JsonNode transaction,
                                         HashMap<String, Category> newCategories, HashMap<String, Currency> newCurrencies,
                                         Iterable<Category> categories, Iterable<Currency> currencies) {
        var category = parseCategory(newCategories, categories, transaction.get("category"));
        var currency = parseCurrency(newCurrencies, currencies, transaction.get("currency"));
        var amount = transaction.get("amount").asText();
        if (!amount.matches("\\d*\\.?\\d*")) {
            throw new DataManipulationException("Amount is not a valid number");
        }

        return new Transaction(
                null,
                transaction.get("receiver").asText(),
                TransactionType.valueOf(transaction.get("transactionType").asText()),
                Double.parseDouble(amount),
                currency,
                category,
                LocalDate.parse(transaction.get("date").asText()),
                transaction.get("variableSymbol").asText(),
                transaction.get("constantSymbol").asText(),
                transaction.get("specificSymbol").asText(),
                transaction.get("message").asText()
        );
    }

    private Category parseCategory(HashMap<String, Category> newCategories, Iterable<Category> categories, JsonNode categoryInfo) {
        var category = StreamSupport.stream(categories.spliterator(), false)
                .filter(c -> c.getName().equals(categoryInfo.get("name").asText()))
                .findFirst();

        return category.orElseGet(() -> newCategories.computeIfAbsent(categoryInfo.get("name").asText(), num ->
                new Category(null, categoryInfo.get("name").asText())));
    }

    private Currency parseCurrency(HashMap<String, Currency> newCurrencies, Iterable<Currency> currencies, JsonNode currencyInfo) {
        var name = currencyInfo.get("name").asText();
        var code = currencyInfo.get("code").asText();
        var rate = Double.parseDouble(currencyInfo.get("rate").asText());
        var currency = StreamSupport.stream(currencies.spliterator(), false)
                .filter(c -> c.getName().equals(name) && c.getCode().equals(code) && c.getRate() == rate)
                .findFirst();

        return currency.orElseGet(() -> newCurrencies.computeIfAbsent(currencyInfo.get("name").asText(), num ->
                new Currency(null, name, code, rate)));
    }
}
