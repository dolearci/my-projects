package cz.muni.fi.pv168.cashflow.ui.model;

import cz.muni.fi.pv168.cashflow.business.model.Category;
import cz.muni.fi.pv168.cashflow.business.model.Template;
import cz.muni.fi.pv168.cashflow.business.model.Transaction;
import cz.muni.fi.pv168.cashflow.business.service.crud.CrudService;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

/**
 * {@link javax.swing.table.TableModel} for {@link Category} objects.
 */
public class CategoryTableModel extends AbstractTableModel implements EntityTableModel<Category> {

    private final List<Column<Category, ?>> columns = List.of(
            Column.readonly("Category name", String.class, Category::getName)
    );
    private final CrudService<Category> categoryCrudService;
    private final CrudService<Transaction> transactionCrudService;
    private final CrudService<Template> templateCrudService;
    private List<Category> categories;

    public CategoryTableModel(CrudService<Category> categoryCrudService, CrudService<Transaction> transactionCrudService, CrudService<Template> templateCrudService) {
        this.categoryCrudService = categoryCrudService;
        this.transactionCrudService = transactionCrudService;
        this.templateCrudService = templateCrudService;
        this.categories = new ArrayList<>(categoryCrudService.findAll());
    }

    public List<Category> getCategories() {
        return categories;
    }

    @Override
    public int getRowCount() {
        return categories.size();
    }

    @Override
    public int getColumnCount() {
        return columns.size();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        var category = getEntity(rowIndex);
        return columns.get(columnIndex).getValue(category);
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
            var category = getEntity(rowIndex);
            columns.get(columnIndex).setValue(value, category);
            updateRow(category);
        }
    }

    public void deleteRow(Category category) {
        if (templateCrudService.findAll().stream().anyMatch(c -> c.getCategory().equals(category)) ||
                transactionCrudService.findAll().stream().anyMatch(c -> c.getCategory().equals(category))) {
            throw new IllegalArgumentException("Category is used in some transaction or template");
        } else {
            categoryCrudService.deleteByGuid(category.getGuid());
            int rowIndex = categories.indexOf(category);
            categories.remove(category);
            fireTableRowsDeleted(rowIndex, rowIndex);
        }
    }

    public void addRow(Category category) {
        categoryCrudService.create(category).intoException();
        int newRowIndex = categories.size();
        categories.add(category);
        fireTableRowsInserted(newRowIndex, newRowIndex);
    }

    public void updateRow(Category category) {
        categoryCrudService.update(category).intoException();
        int rowIndex = categories.indexOf(category);
        fireTableRowsUpdated(rowIndex, rowIndex);
    }

    public void refresh() {
        categories = new ArrayList<>(categoryCrudService.findAll());
        fireTableDataChanged();
    }

    @Override
    public Category getEntity(int rowIndex) {
        return categories.get(rowIndex);
    }
}
