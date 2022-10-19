package cz.muni.fi.pv168.cashflow.ui.dialogs;

import cz.muni.fi.pv168.cashflow.business.model.Category;
import cz.muni.fi.pv168.cashflow.business.model.Template;
import cz.muni.fi.pv168.cashflow.business.model.Transaction;
import cz.muni.fi.pv168.cashflow.business.service.crud.CrudService;
import cz.muni.fi.pv168.cashflow.ui.model.CategoryTableModel;
import cz.muni.fi.pv168.cashflow.utils.PlaceholderTextField;

import javax.swing.JOptionPane;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.UUID;

public class CategoryDialog extends EntityDialog<Category> {

    private final PlaceholderTextField categoryNameField = new PlaceholderTextField("E.g. Food");
    private Category category;

    public CategoryDialog(CategoryTableModel categoryTableModel, CrudService<Transaction> transactionCrudService, CrudService<Template> templateCrudService, Category category) {
        if (category != null) {
            buttonPanel.add(deleteButton);
            if (templateCrudService.findAll().stream().anyMatch(c -> c.getCategory().equals(category)) ||
                    transactionCrudService.findAll().stream().anyMatch(c -> c.getCategory().equals(category)) ||
                    category.getName().equals("Not selected")) {
                deleteButton.setEnabled(false);
                deleteButton.setToolTipText("Cannot delete category that is used in transactions or templates.");
                if (category.getName().equals("Not selected")) {
                    deleteButton.setToolTipText("Cannot delete default category.");
                }
            } else {
                deleteButton.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent e) {
                        var deleteDialog = new DeleteDialog(category.getName(), "category");
                        deleteDialog.show(panel, "Delete Category")
                                .ifPresent(cat -> categoryTableModel.deleteRow(category));
                    }
                });
            }
            this.category = category;
            setValues();
            categoryNameField.setForeground(Color.black);
        }
        addFields();
    }

    private void setValues() {
        categoryNameField.setText(category.getName());
    }

    private void addFields() {
        add("Category name*:", categoryNameField);
    }

    @Override
    Category getEntity() {
        if (category == null) {
            this.category = new Category(UUID.randomUUID().toString(), categoryNameField.getText());
        } else {
            category.setName(categoryNameField.getText());
        }
        return category;
    }

    @Override
    boolean validateFields() {
        String categoryName = categoryNameField.getText().trim();
        if (categoryName.isEmpty()) {
            JOptionPane.showMessageDialog(panel, "Category name cannot be empty.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    @Override
    boolean areValid() {
        String categoryName = categoryNameField.getTextFinal().trim();
        return !categoryName.isEmpty();
    }

    @Override
    void resetForm() {
        categoryNameField.setText("");
    }
}
