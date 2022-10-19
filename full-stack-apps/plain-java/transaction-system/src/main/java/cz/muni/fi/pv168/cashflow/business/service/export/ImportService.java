package cz.muni.fi.pv168.cashflow.business.service.export;

import cz.muni.fi.pv168.cashflow.business.service.export.format.Format;

import java.util.Collection;

public interface ImportService {

    void importData(String filePath, boolean deleteAll);

    Collection<Format> getFormats();
}
