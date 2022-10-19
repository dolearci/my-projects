package cz.muni.fi.pv168.cashflow.ui.model;

import cz.muni.fi.pv168.cashflow.business.model.Currency;
import cz.muni.fi.pv168.cashflow.business.model.Template;
import cz.muni.fi.pv168.cashflow.business.model.Transaction;
import cz.muni.fi.pv168.cashflow.business.service.crud.CrudService;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * {@link javax.swing.table.TableModel} for {@link Currency} objects.
 */
public class CurrencyTableModel extends AbstractTableModel implements EntityTableModel<Currency> {

    private final List<Column<Currency, ?>> columns = List.of(
            Column.readonly("Currency", String.class, Currency::getName),
            Column.readonly("Code", String.class, Currency::getCode),
            Column.readonly("Exchange Rate", double.class, Currency::getRate)
    );
    private final Currency mainCurrency = new Currency(UUID.randomUUID().toString(), "Czech Koruna", "CZK", 0);
    private final CrudService<Currency> currencyCrudService;
    private final CrudService<Transaction> transactionCrudService;
    private final CrudService<Template> templateCrudService;
    private ArrayList<Currency> currencies;

    public CurrencyTableModel(CrudService<Currency> currencyCrudService, CrudService<Transaction> transactionCrudService, CrudService<Template> templateCrudService) {
        this.currencyCrudService = currencyCrudService;
        this.transactionCrudService = transactionCrudService;
        this.templateCrudService = templateCrudService;
        this.currencies = new ArrayList<>(currencyCrudService.findAll());
        if (currencyCrudService.findAll().stream().noneMatch(currency -> currency.getCode().equals(mainCurrency.getCode()))) {
            currencyCrudService.create(mainCurrency);
        } else {
            mainCurrency.setGuid(currencyCrudService.findAll().stream().filter(currency -> currency.getCode().equals(mainCurrency.getCode())).findFirst().get().getGuid());
        }
    }

    public Currency getMainCurrency() {
        return mainCurrency;
    }

    public ArrayList<Currency> getCurrencies() {
        return currencies;
    }

    @Override
    public int getRowCount() {
        return currencies.size();
    }

    @Override
    public int getColumnCount() {
        return columns.size();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        var transaction = getEntity(rowIndex);
        return columns.get(columnIndex).getValue(transaction);
    }

    @Override
    public String getColumnName(int columnIndex) {
        return columns.get(columnIndex).getName();
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return columns.get(columnIndex).getColumnType();
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return columns.get(columnIndex).isEditable();
    }

    @Override
    public void setValueAt(Object value, int rowIndex, int columnIndex) {
        if (value != null) {
            var transaction = getEntity(rowIndex);
            columns.get(columnIndex).setValue(value, transaction);
            updateRow(transaction);
        }
    }

    public void deleteRow(Currency currency) {
        if (transactionCrudService.findAll().stream().anyMatch(e -> e.getCurrency().equals(currency)) ||
                templateCrudService.findAll().stream().anyMatch(e -> e.getCurrency().equals(currency))) {
            throw new IllegalStateException("Cannot delete currency that is used in transactions or templates.");
        } else {
            currencyCrudService.deleteByGuid(currency.getGuid());
            int rowIndex = currencies.indexOf(currency);
            currencies.remove(currency);
            fireTableRowsDeleted(rowIndex, rowIndex);
        }
    }

    public void addRow(Currency currency) {
        currencyCrudService.create(currency).intoException();
        int newRowIndex = currencies.size();
        currencies.add(currency);
        fireTableRowsInserted(newRowIndex, newRowIndex);
    }

    public void updateRow(Currency currency) {
        currencyCrudService.update(currency);
        int rowIndex = currencies.indexOf(currency);
        fireTableRowsUpdated(rowIndex, rowIndex);
    }

    public void refresh() {
        currencies = new ArrayList<>(currencyCrudService.findAll());
        fireTableDataChanged();
    }

    @Override
    public Currency getEntity(int rowIndex) {
        return currencies.get(rowIndex);
    }
}
