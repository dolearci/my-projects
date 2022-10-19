package cz.muni.fi.pv168.cashflow.storage.sql;

import cz.muni.fi.pv168.cashflow.business.service.export.ImportService;
import cz.muni.fi.pv168.cashflow.business.service.export.format.Format;
import cz.muni.fi.pv168.cashflow.storage.sql.db.TransactionExecutor;

import java.util.Collection;

public class TransactionalImportService implements ImportService {

    private final ImportService importService;

    private final TransactionExecutor transactionExecutor;

    public TransactionalImportService(ImportService importService, TransactionExecutor transactionExecutor) {
        this.importService = importService;
        this.transactionExecutor = transactionExecutor;
    }

    @Override
    public void importData(String filePath, boolean deleteAll) {
        transactionExecutor.executeInTransaction(() -> importService.importData(filePath, deleteAll));
    }

    @Override
    public Collection<Format> getFormats() {
        return importService.getFormats();
    }
}
