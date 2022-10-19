package cz.muni.fi.pv168.cashflow.business.service.export;

import cz.muni.fi.pv168.cashflow.business.model.Category;
import cz.muni.fi.pv168.cashflow.business.model.Currency;
import cz.muni.fi.pv168.cashflow.business.model.Transaction;
import cz.muni.fi.pv168.cashflow.business.service.crud.CrudService;
import cz.muni.fi.pv168.cashflow.business.service.export.batch.Batch;
import cz.muni.fi.pv168.cashflow.business.service.export.batch.BatchExporter;
import cz.muni.fi.pv168.cashflow.business.service.export.batch.BatchOperationException;
import cz.muni.fi.pv168.cashflow.business.service.export.format.Format;
import cz.muni.fi.pv168.cashflow.business.service.export.format.FormatMapping;

import java.util.Collection;

public class GenericExportService implements ExportService {

    private final CrudService<Transaction> transactionCrudServices;
    private final CrudService<Category> categoryCrudService;
    private final CrudService<Currency> currencyCrudService;
    private final FormatMapping<BatchExporter> exporters;

    public GenericExportService(
            CrudService<Currency> currencyCrudService,
            CrudService<Category> categoryCrudService,
            CrudService<Transaction> transactionCrudServices,
            Collection<BatchExporter> exporters) {
        this.currencyCrudService = currencyCrudService;
        this.categoryCrudService = categoryCrudService;
        this.transactionCrudServices = transactionCrudServices;
        this.exporters = new FormatMapping<>(exporters);
    }

    @Override
    public Collection<Format> getFormats() {
        return exporters.getFormats();
    }

    @Override
    public void exportData(String filePath) {
        var exporter = getExporter(filePath);

        var batch = new Batch(transactionCrudServices.findAll(), categoryCrudService.findAll(), currencyCrudService.findAll());
        exporter.exportBatch(batch, filePath);
    }

    private BatchExporter getExporter(String filePath) {
        var extension = filePath.substring(filePath.lastIndexOf('.') + 1);
        var importer = exporters.findByExtension(extension);
        if (importer == null) {
            throw new BatchOperationException("Extension %s has no registered formatter".formatted(extension));
        }
        return importer;
    }
}
