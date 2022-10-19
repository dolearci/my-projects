package cz.muni.fi.pv168.cashflow.ui.model;

import cz.muni.fi.pv168.cashflow.business.model.Category;
import cz.muni.fi.pv168.cashflow.business.model.Currency;
import cz.muni.fi.pv168.cashflow.business.model.Transaction;
import cz.muni.fi.pv168.cashflow.business.model.TransactionType;
import cz.muni.fi.pv168.cashflow.business.service.crud.CrudService;

import javax.swing.table.AbstractTableModel;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * {@link javax.swing.table.TableModel} for {@link Transaction} objects.
 */
public class TransactionTableModel extends AbstractTableModel implements EntityTableModel<Transaction> {

    private final List<Column<Transaction, ?>> columns = List.of(
            Column.readonly("Receiver", String.class, Transaction::getReceiver),
            Column.readonly("Transaction type", TransactionType.class, Transaction::getTransactionType),
            Column.readonly("Amount", double.class, Transaction::getAmount),
            Column.readonly("Currency", Currency.class, Transaction::getCurrency),
            Column.readonly("Category", Category.class, Transaction::getCategory),
            Column.readonly("Date", LocalDate.class, Transaction::getDate),
            Column.readonly("Variable symbol", String.class, Transaction::getVariableSymbol),
            Column.readonly("Constant symbol", String.class, Transaction::getConstantSymbol),
            Column.readonly("Specific symbol", String.class, Transaction::getSpecificSymbol),
            Column.readonly("Message", String.class, Transaction::getMessage)
    );
    private final CrudService<Transaction> transactionCrudService;
    private List<Transaction> transactions;

    public TransactionTableModel(CrudService<Transaction> transactionCrudService) {
        this.transactionCrudService = transactionCrudService;
        this.transactions = new ArrayList<>(transactionCrudService.findAll());
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    @Override
    public int getRowCount() {
        return transactions.size();
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

    public void deleteRow(Transaction transaction) {
        transactionCrudService.deleteByGuid(transaction.getGuid());
        int rowIndex = transactions.indexOf(transaction);
        transactions.remove(transaction);
        fireTableRowsDeleted(rowIndex, rowIndex);
    }

    public void addRow(Transaction transaction) {
        transactionCrudService.create(transaction).intoException();
        int newRowIndex = transactions.size();
        transactions.add(transaction);
        fireTableRowsInserted(newRowIndex, newRowIndex);
    }

    public void updateRow(Transaction transaction) {
        transactionCrudService.update(transaction);
        int rowIndex = transactions.indexOf(transaction);
        fireTableRowsUpdated(rowIndex, rowIndex);
    }

    public void refresh() {
        transactions = new ArrayList<>(transactionCrudService.findAll());
        fireTableDataChanged();
    }

    @Override
    public Transaction getEntity(int rowIndex) {
        return transactions.get(rowIndex);
    }
}
