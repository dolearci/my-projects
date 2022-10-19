package cz.muni.fi.pv168.cashflow.ui.wiring;

import cz.muni.fi.pv168.cashflow.business.model.Category;
import cz.muni.fi.pv168.cashflow.business.model.Currency;
import cz.muni.fi.pv168.cashflow.business.model.Template;
import cz.muni.fi.pv168.cashflow.business.model.Transaction;
import cz.muni.fi.pv168.cashflow.business.model.UuidGuidProvider;
import cz.muni.fi.pv168.cashflow.business.repository.CategoryRepository;
import cz.muni.fi.pv168.cashflow.business.repository.CurrencyRepository;
import cz.muni.fi.pv168.cashflow.business.repository.TemplateRepository;
import cz.muni.fi.pv168.cashflow.business.repository.TransactionRepository;
import cz.muni.fi.pv168.cashflow.business.service.crud.CategoryCrudService;
import cz.muni.fi.pv168.cashflow.business.service.crud.CrudService;
import cz.muni.fi.pv168.cashflow.business.service.crud.CurrencyCrudService;
import cz.muni.fi.pv168.cashflow.business.service.crud.TemplateCrudService;
import cz.muni.fi.pv168.cashflow.business.service.crud.TransactionCrudService;
import cz.muni.fi.pv168.cashflow.business.service.export.ExportService;
import cz.muni.fi.pv168.cashflow.business.service.export.GenericExportService;
import cz.muni.fi.pv168.cashflow.business.service.export.GenericImportService;
import cz.muni.fi.pv168.cashflow.business.service.export.ImportService;
import cz.muni.fi.pv168.cashflow.business.service.validation.CategoryValidator;
import cz.muni.fi.pv168.cashflow.business.service.validation.CurrencyValidator;
import cz.muni.fi.pv168.cashflow.business.service.validation.TemplateValidator;
import cz.muni.fi.pv168.cashflow.business.service.validation.TransactionValidator;
import cz.muni.fi.pv168.cashflow.business.service.validation.Validator;
import cz.muni.fi.pv168.cashflow.export.csv.BatchCsvExporter;
import cz.muni.fi.pv168.cashflow.export.csv.BatchCsvImporter;
import cz.muni.fi.pv168.cashflow.export.json.BatchJsonExporter;
import cz.muni.fi.pv168.cashflow.export.json.BatchJsonImporter;
import cz.muni.fi.pv168.cashflow.export.xml.BatchXmlExporter;
import cz.muni.fi.pv168.cashflow.export.xml.BatchXmlImporter;
import cz.muni.fi.pv168.cashflow.storage.sql.CategorySqlRepository;
import cz.muni.fi.pv168.cashflow.storage.sql.CurrencySqlRepository;
import cz.muni.fi.pv168.cashflow.storage.sql.TemplateSqlRepository;
import cz.muni.fi.pv168.cashflow.storage.sql.TransactionSqlRepository;
import cz.muni.fi.pv168.cashflow.storage.sql.TransactionalImportService;
import cz.muni.fi.pv168.cashflow.storage.sql.dao.CategoryDao;
import cz.muni.fi.pv168.cashflow.storage.sql.dao.CurrencyDao;
import cz.muni.fi.pv168.cashflow.storage.sql.dao.TemplateDao;
import cz.muni.fi.pv168.cashflow.storage.sql.dao.TransactionDao;
import cz.muni.fi.pv168.cashflow.storage.sql.db.DatabaseManager;
import cz.muni.fi.pv168.cashflow.storage.sql.db.TransactionConnectionSupplier;
import cz.muni.fi.pv168.cashflow.storage.sql.db.TransactionExecutor;
import cz.muni.fi.pv168.cashflow.storage.sql.db.TransactionExecutorImpl;
import cz.muni.fi.pv168.cashflow.storage.sql.db.TransactionManagerImpl;
import cz.muni.fi.pv168.cashflow.storage.sql.entity.mapper.CategoryMapper;
import cz.muni.fi.pv168.cashflow.storage.sql.entity.mapper.CurrencyMapper;
import cz.muni.fi.pv168.cashflow.storage.sql.entity.mapper.TemplateMapper;
import cz.muni.fi.pv168.cashflow.storage.sql.entity.mapper.TransactionMapper;

import java.util.List;

public class CommonDependencyProvider implements DependencyProvider {

    private final TransactionRepository transactions;
    private final TemplateRepository templates;
    private final CurrencyRepository currencies;
    private final CategoryRepository categories;

    private final DatabaseManager databaseManager;
    private final TransactionExecutor transactionExecutor;
    private final CrudService<Transaction> transactionCrudService;
    private final CrudService<Template> templateCrudService;
    private final CrudService<Currency> currencyCrudService;
    private final CrudService<Category> categoryCrudService;

    private final ImportService importService;
    private final ExportService exportService;
    private final TransactionValidator transactionValidator;
    private final TemplateValidator templateValidator;
    private final CurrencyValidator currencyValidator;
    private final CategoryValidator categoryValidator;

    public CommonDependencyProvider(DatabaseManager databaseManager) {
        transactionValidator = new TransactionValidator();
        templateValidator = new TemplateValidator();
        currencyValidator = new CurrencyValidator();
        categoryValidator = new CategoryValidator();
        var guidProvider = new UuidGuidProvider();

        this.databaseManager = databaseManager;
        var transactionManager = new TransactionManagerImpl(databaseManager);
        this.transactionExecutor = new TransactionExecutorImpl(transactionManager::beginTransaction);
        var transactionConnectionSupplier = new TransactionConnectionSupplier(transactionManager, databaseManager);

        var currencyMapper = new CurrencyMapper();
        var categoryMapper = new CategoryMapper();
        var transactionMapper = new TransactionMapper(
                new CurrencyDao(transactionConnectionSupplier),
                new CategoryDao(transactionConnectionSupplier),
                currencyMapper,
                categoryMapper);
        var templateMapper = new TemplateMapper(
                new CurrencyDao(transactionConnectionSupplier),
                new CategoryDao(transactionConnectionSupplier),
                currencyMapper,
                categoryMapper);
        var transactionDao = new TransactionDao(transactionConnectionSupplier);
        var templateDao = new TemplateDao(transactionConnectionSupplier);
        var currencyDao = new CurrencyDao(transactionConnectionSupplier);
        var categoryDao = new CategoryDao(transactionConnectionSupplier);

        this.transactions = new TransactionSqlRepository(
                transactionDao,
                transactionMapper
        );
        this.templates = new TemplateSqlRepository(
                templateDao,
                templateMapper
        );
        this.currencies = new CurrencySqlRepository(
                currencyDao,
                currencyMapper
        );
        this.categories = new CategorySqlRepository(
                categoryDao,
                categoryMapper
        );

        transactionCrudService = new TransactionCrudService(transactions, transactionValidator, guidProvider);
        templateCrudService = new TemplateCrudService(templates, templateValidator, guidProvider);
        currencyCrudService = new CurrencyCrudService(currencies, currencyValidator, guidProvider);
        categoryCrudService = new CategoryCrudService(categories, categoryValidator, guidProvider);

        exportService = new GenericExportService(currencyCrudService, categoryCrudService, transactionCrudService,
                List.of(new BatchCsvExporter(), new BatchJsonExporter(), new BatchXmlExporter()));
        var genericImportService = new GenericImportService(currencyCrudService, categoryCrudService, transactionCrudService,
                templateCrudService, List.of(new BatchCsvImporter(), new BatchJsonImporter(), new BatchXmlImporter()));
        importService = new TransactionalImportService(genericImportService, transactionExecutor);
    }

    @Override
    public DatabaseManager getDatabaseManager() {
        return databaseManager;
    }

    @Override
    public TransactionRepository getTransactionRepository() {
        return transactions;
    }

    @Override
    public TemplateRepository getTemplateRepository() {
        return templates;
    }

    @Override
    public CurrencyRepository getCurrencyRepository() {
        return currencies;
    }

    @Override
    public CategoryRepository getCategoryRepository() {
        return categories;
    }

    @Override
    public TransactionExecutor getTransactionExecutor() {
        return transactionExecutor;
    }

    @Override
    public CrudService<Transaction> getTransactionCrudService() {
        return transactionCrudService;
    }

    @Override
    public CrudService<Template> getTemplateCrudService() {
        return templateCrudService;
    }

    @Override
    public CrudService<Currency> getCurrencyCrudService() {
        return currencyCrudService;
    }

    @Override
    public CrudService<Category> getCategoryCrudService() {
        return categoryCrudService;
    }

    @Override
    public ImportService getImportService() {
        return importService;
    }

    @Override
    public ExportService getExportService() {
        return exportService;
    }

    @Override
    public Validator<Transaction> getTransactionValidator() {
        return transactionValidator;
    }

    @Override
    public Validator<Template> getTemplateValidator() {
        return templateValidator;
    }

    @Override
    public Validator<Currency> getCurrencyValidator() {
        return currencyValidator;
    }

    @Override
    public Validator<Category> getCategoryValidator() {
        return categoryValidator;
    }

}
