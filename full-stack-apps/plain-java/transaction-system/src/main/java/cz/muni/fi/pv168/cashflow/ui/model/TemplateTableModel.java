package cz.muni.fi.pv168.cashflow.ui.model;

import cz.muni.fi.pv168.cashflow.business.model.Category;
import cz.muni.fi.pv168.cashflow.business.model.Currency;
import cz.muni.fi.pv168.cashflow.business.model.Template;
import cz.muni.fi.pv168.cashflow.business.model.TransactionType;
import cz.muni.fi.pv168.cashflow.business.service.crud.CrudService;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

/**
 * {@link javax.swing.table.TableModel} for {@link Template} objects.
 */
public class TemplateTableModel extends AbstractTableModel implements EntityTableModel<Template> {

    private final List<Column<Template, ?>> columns = List.of(
            Column.readonly("Template name", String.class, Template::getTemplateName),
            Column.readonly("Receiver", String.class, Template::getReceiver),
            Column.readonly("Transaction type", TransactionType.class, Template::getTransactionType),
            Column.readonly("Amount", double.class, Template::getAmount),
            Column.readonly("Currency", Currency.class, Template::getCurrency),
            Column.readonly("Category", Category.class, Template::getCategory),
            Column.readonly("Variable symbol", String.class, Template::getVariableSymbol),
            Column.readonly("Constant symbol", String.class, Template::getConstantSymbol),
            Column.readonly("Specific symbol", String.class, Template::getSpecificSymbol),
            Column.readonly("Message", String.class, Template::getMessage)
    );
    private final CrudService<Template> templateCrudService;
    private List<Template> templates;

    public TemplateTableModel(CrudService<Template> templateCrudService) {
        this.templateCrudService = templateCrudService;
        this.templates = new ArrayList<>(templateCrudService.findAll());
    }

    public List<Template> getTemplates() {
        return templates;
    }

    @Override
    public int getRowCount() {
        return templates.size();
    }

    @Override
    public int getColumnCount() {
        return columns.size();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        var template = getEntity(rowIndex);
        return columns.get(columnIndex).getValue(template);
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
            var template = getEntity(rowIndex);
            columns.get(columnIndex).setValue(value, template);
            updateRow(template);
        }
    }

    public void deleteRow(Template template) {
        templateCrudService.deleteByGuid(template.getGuid());
        int rowIndex = templates.indexOf(template);
        templates.remove(template);
        fireTableRowsDeleted(rowIndex, rowIndex);
    }

    public void addRow(Template template) {
        templateCrudService.create(template).intoException();
        int newRowIndex = templates.size();
        templates.add(template);
        fireTableRowsInserted(newRowIndex, newRowIndex);
    }

    public void updateRow(Template template) {
        templateCrudService.update(template);
        int rowIndex = templates.indexOf(template);
        fireTableRowsUpdated(rowIndex, rowIndex);
    }

    public void refresh() {
        templates = new ArrayList<>(templateCrudService.findAll());
        fireTableDataChanged();
    }

    @Override
    public Template getEntity(int rowIndex) {
        return templates.get(rowIndex);
    }
}
