package cz.muni.fi.pv168.cashflow.ui.wiring;

import cz.muni.fi.pv168.cashflow.business.model.Category;
import cz.muni.fi.pv168.cashflow.business.model.Currency;
import cz.muni.fi.pv168.cashflow.business.model.Template;
import cz.muni.fi.pv168.cashflow.business.model.Transaction;
import cz.muni.fi.pv168.cashflow.business.repository.CategoryRepository;
import cz.muni.fi.pv168.cashflow.business.repository.CurrencyRepository;
import cz.muni.fi.pv168.cashflow.business.repository.TemplateRepository;
import cz.muni.fi.pv168.cashflow.business.repository.TransactionRepository;
import cz.muni.fi.pv168.cashflow.business.service.crud.CrudService;
import cz.muni.fi.pv168.cashflow.business.service.export.ExportService;
import cz.muni.fi.pv168.cashflow.business.service.export.ImportService;
import cz.muni.fi.pv168.cashflow.business.service.validation.Validator;
import cz.muni.fi.pv168.cashflow.storage.sql.db.DatabaseManager;
import cz.muni.fi.pv168.cashflow.storage.sql.db.TransactionExecutor;

public interface DependencyProvider {

    DatabaseManager getDatabaseManager();

    TransactionRepository getTransactionRepository();

    TemplateRepository getTemplateRepository();

    CurrencyRepository getCurrencyRepository();

    CategoryRepository getCategoryRepository();

    TransactionExecutor getTransactionExecutor();

    CrudService<Transaction> getTransactionCrudService();

    CrudService<Template> getTemplateCrudService();

    CrudService<Currency> getCurrencyCrudService();

    CrudService<Category> getCategoryCrudService();

    ImportService getImportService();

    ExportService getExportService();

    Validator<Transaction> getTransactionValidator();

    Validator<Template> getTemplateValidator();

    Validator<Currency> getCurrencyValidator();

    Validator<Category> getCategoryValidator();
}


