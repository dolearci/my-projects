package cz.muni.fi.pv168.cashflow.business.service.export;

import cz.muni.fi.pv168.cashflow.business.model.Category;
import cz.muni.fi.pv168.cashflow.business.model.Currency;
import cz.muni.fi.pv168.cashflow.business.model.Template;
import cz.muni.fi.pv168.cashflow.business.model.Transaction;
import cz.muni.fi.pv168.cashflow.business.service.crud.CrudService;
import cz.muni.fi.pv168.cashflow.business.service.export.batch.BatchImporter;
import cz.muni.fi.pv168.cashflow.business.service.export.batch.BatchOperationException;
import cz.muni.fi.pv168.cashflow.business.service.export.format.Format;
import cz.muni.fi.pv168.cashflow.business.service.export.format.FormatMapping;

import java.util.Collection;

public class GenericImportService implements ImportService {

    private final CrudService<Transaction> transactionCrudServices;
    private final CrudService<Category> categoryCrudService;
    private final CrudService<Currency> currencyCrudService;
    private final CrudService<Template> templateCrudService;
    private final FormatMapping<BatchImporter> importers;

    public GenericImportService(
            CrudService<Currency> currencyCrudService,
            CrudService<Category> categoryCrudService,
            CrudService<Transaction> transactionCrudServices,
            CrudService<Template> templateCrudService,
            Collection<BatchImporter> importers
    ) {
        this.currencyCrudService = currencyCrudService;
        this.categoryCrudService = categoryCrudService;
        this.transactionCrudServices = transactionCrudServices;
        this.templateCrudService = templateCrudService;
        this.importers = new FormatMapping<>(importers);
    }

    @Override
    public void importData(String filePath, boolean deleteAll) {

        var batch = getImporter(filePath).importBatch(filePath,
                currencyCrudService.findAll(), categoryCrudService.findAll());

        if (deleteAll) {
            transactionCrudServices.deleteAll();
        }

        batch.categories().forEach(this::createCategories);
        batch.currencies().forEach(this::createCurrency);
        batch.transactions().forEach(this::createTransactions);
    }

    private void createTransactions(Transaction transaction) {
        transactionCrudServices.create(transaction)
                .intoException();
    }

    private void createCategories(Category category) {
        categoryCrudService.create(category)
                .intoException();
    }

    private void createCurrency(Currency currency) {
        currencyCrudService.create(currency)
                .intoException();
    }

    @Override
    public Collection<Format> getFormats() {
        return importers.getFormats();
    }

    private BatchImporter getImporter(String filePath) {
        var extension = filePath.substring(filePath.lastIndexOf('.') + 1);
        var importer = importers.findByExtension(extension);
        if (importer == null) {
            throw new BatchOperationException("Extension %s has no registered formatter".formatted(extension));
        }
        return importer;
    }
}
