package cz.muni.fi.pv168.cashflow.export.csv;

import cz.muni.fi.pv168.cashflow.business.model.Category;
import cz.muni.fi.pv168.cashflow.business.model.Currency;
import cz.muni.fi.pv168.cashflow.business.model.Transaction;
import cz.muni.fi.pv168.cashflow.business.service.export.DataManipulationException;
import cz.muni.fi.pv168.cashflow.business.service.export.batch.Batch;
import cz.muni.fi.pv168.cashflow.business.service.export.batch.BatchExporter;
import cz.muni.fi.pv168.cashflow.business.service.export.format.Format;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class BatchCsvExporter implements BatchExporter {

    private static final String SEPARATOR = ",";
    private static final String CATEGORY_CURRENCY_SEPARATOR = ":";
    private static final Format FORMAT = new Format("CSV", List.of("csv"));

    @Override
    public Format getFormat() {
        return FORMAT;
    }

    @Override
    public void exportBatch(Batch batch, String filePath) {

        try (var writer = Files.newBufferedWriter(Path.of(filePath), StandardCharsets.UTF_8)) {
            for (var transaction : batch.transactions()) {
                String line = createCsvLine(transaction);
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException exception) {
            throw new DataManipulationException("Unable to write to file", exception);
        }
    }

    private String createCsvLine(Transaction transaction) {
        return serializeTransaction(transaction);
    }

    private String serializeCategory(Category category) {
        return String.join(CATEGORY_CURRENCY_SEPARATOR,
                category.getName()
        );
    }

    private String serializeCurrencies(Currency currency) {
        return String.join(CATEGORY_CURRENCY_SEPARATOR,
                currency.getName(),
                currency.getCode(),
                String.valueOf(currency.getRate())
        );
    }

    private String serializeTransaction(Transaction transaction) {
        return String.join(SEPARATOR,
                transaction.getReceiver(),
                transaction.getTransactionType().name(),
                String.valueOf(transaction.getAmount()),
                serializeCurrencies(transaction.getCurrency()),
                serializeCategory(transaction.getCategory()),
                transaction.getDate().toString(),
                transaction.getVariableSymbol(),
                transaction.getConstantSymbol(),
                transaction.getSpecificSymbol(),
                transaction.getMessage()
        );
    }
}
