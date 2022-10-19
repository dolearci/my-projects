package cz.muni.fi.pv168.cashflow.business.service.export;

import cz.muni.fi.pv168.cashflow.business.service.export.format.Format;

import java.util.Collection;

public interface ExportService {

    void exportData(String filePath);

    Collection<Format> getFormats();
}
