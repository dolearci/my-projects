package cz.muni.fi.pv168.cashflow.business.service.export.batch;

import cz.muni.fi.pv168.cashflow.business.model.Category;
import cz.muni.fi.pv168.cashflow.business.model.Currency;
import cz.muni.fi.pv168.cashflow.business.service.export.format.FileFormat;

public interface BatchImporter extends FileFormat {

    Batch importBatch(String filePath, Iterable<Currency> currencies, Iterable<Category> categories);
}
