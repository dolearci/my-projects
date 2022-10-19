package cz.muni.fi.pv168.cashflow.export.csv;

import cz.muni.fi.pv168.cashflow.business.model.Category;
import cz.muni.fi.pv168.cashflow.business.model.Currency;
import cz.muni.fi.pv168.cashflow.business.model.Transaction;
import cz.muni.fi.pv168.cashflow.business.model.TransactionType;
import cz.muni.fi.pv168.cashflow.business.service.export.DataManipulationException;
import cz.muni.fi.pv168.cashflow.business.service.export.batch.Batch;
import cz.muni.fi.pv168.cashflow.business.service.export.batch.BatchImporter;
import cz.muni.fi.pv168.cashflow.business.service.export.format.Format;

import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.stream.StreamSupport;

public class BatchCsvImporter implements BatchImporter {

    private static final String SEPARATOR = ",";
    private static final String CATEGORY_CURRENCY_SEPARATOR = ":";
    private static final Format FORMAT = new Format("CSV", List.of("csv"));

    @Override
    public Format getFormat() {
        return FORMAT;
    }

    @Override
    public Batch importBatch(String filePath, Iterable<Currency> currencies, Iterable<Category> categories) {
        var newCategories = new HashMap<String, Category>();
        var newCurrencies = new HashMap<String, Currency>();

        try (var reader = Files.newBufferedReader(Path.of(filePath))) {
            var transactions = reader.lines()
                    .map(line -> parseTransaction(line, newCategories, newCurrencies, categories, currencies))
                    .toList();

            return new Batch(transactions, newCategories.values(), newCurrencies.values());
        } catch (Exception e) {
            throw new DataManipulationException("Unable to read file: " + e.getMessage(), e);
        }
    }

    private Transaction parseTransaction(String line, HashMap<String,
            Category> newCategories, HashMap<String, Currency> newCurrencies,
                                         Iterable<Category> categories, Iterable<Currency> currencies) {
        var fields = line.split(SEPARATOR, -1);
        var category = parseCategory(newCategories, categories, fields[4]);
        var currency = parseCurrency(newCurrencies, currencies, fields[3]);
        var amount = fields[2];

        if (!amount.matches("\\d*\\.?\\d*")) {
            throw new DataManipulationException("Amount is not a valid number");
        }

        return new Transaction(
                null,
                fields[0],
                TransactionType.valueOf(fields[1]),
                Double.parseDouble(amount),
                currency,
                category,
                LocalDate.parse(fields[5]),
                fields[6],
                fields[7],
                fields[8],
                fields[9]
        );
    }

    private Category parseCategory(HashMap<String, Category> newCategories,
                                   Iterable<Category> categories, String categoryInfo) {
        String[] fields = categoryInfo.split(CATEGORY_CURRENCY_SEPARATOR);
        var category = StreamSupport.stream(categories.spliterator(), false)
                .filter(c -> c.getName().equals(fields[0]))
                .findFirst();

        return category.orElseGet(() ->
                newCategories.computeIfAbsent(fields[0], num -> new Category(null, fields[0])));
    }

    private Currency parseCurrency(HashMap<String, Currency> newCurrencies,
                                   Iterable<Currency> currencies, String currencyInfo) {
        String[] fields = currencyInfo.split(CATEGORY_CURRENCY_SEPARATOR);
        var name = fields[0];
        var code = fields[1];
        var rate = Double.parseDouble(fields[2]);
        var currency = StreamSupport.stream(currencies.spliterator(), false)
                .filter(c -> c.getName().equals(name) && c.getCode().equals(code) && c.getRate() == rate)
                .findFirst();

        return currency.orElseGet(() ->
                newCurrencies.computeIfAbsent(name, num -> new Currency(null, name, code, rate)));
    }
}
