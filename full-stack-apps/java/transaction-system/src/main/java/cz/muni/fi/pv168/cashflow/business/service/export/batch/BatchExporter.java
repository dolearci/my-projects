package cz.muni.fi.pv168.cashflow.business.service.export.batch;

import cz.muni.fi.pv168.cashflow.business.service.export.format.FileFormat;

public interface BatchExporter extends FileFormat {

    void exportBatch(Batch batch, String filePath);
}
