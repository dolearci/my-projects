package cz.muni.fi.pv168.cashflow.ui.tabs;

import cz.muni.fi.pv168.cashflow.business.model.Category;
import cz.muni.fi.pv168.cashflow.business.model.Template;
import cz.muni.fi.pv168.cashflow.business.model.Transaction;
import cz.muni.fi.pv168.cashflow.business.service.crud.CrudService;
import cz.muni.fi.pv168.cashflow.ui.dialogs.CategoryDialog;
import cz.muni.fi.pv168.cashflow.ui.model.CategoryTableModel;
import cz.muni.fi.pv168.cashflow.ui.renderers.BasicCellRenderer;
import cz.muni.fi.pv168.cashflow.ui.renderers.CategoryRenderer;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableRowSorter;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CategoriesTab extends JPanel {

    private final CategoryTableModel categoryTableModel;
    private final CrudService<Transaction> transactionCrudService;
    private final CrudService<Template> templateCrudService;
    private JTable table;

    public CategoriesTab(CategoryTableModel categoryTableModel, CrudService<Transaction> transactionCrudService, CrudService<Template> templateCrudService) {
        this.categoryTableModel = categoryTableModel;
        this.transactionCrudService = transactionCrudService;
        this.templateCrudService = templateCrudService;

        setUpTable(categoryTableModel);
    }

    private void setUpTable(CategoryTableModel categoryTableModel) {
        var jTable = getjTable(categoryTableModel);
        jTable.setAutoCreateRowSorter(true);
        Font largerFont = new Font("Arial", Font.BOLD, 16);
        jTable.setFont(largerFont);
        jTable.getTableHeader().setBackground(new Color(144, 213, 236));
        jTable.getTableHeader().setFont(largerFont);
        jTable.setRowHeight(30);
        jTable.setDefaultRenderer(Category.class, new CategoryRenderer());
        jTable.setDefaultRenderer(Object.class, new BasicCellRenderer());

        setLayout(new BorderLayout());
        JScrollPane pane = new JScrollPane(jTable);
        pane.setBorder(BorderFactory.createEmptyBorder(20, 15, 0, 15));

        add(pane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        addCategoryButton(buttonPanel);

        var rowSorter = new TableRowSorter<>(categoryTableModel);
        jTable.setRowSorter(rowSorter);

        this.table = jTable;
    }

    private JTable getjTable(CategoryTableModel categoryTableModel) {
        var jTable = new JTable(categoryTableModel);
        jTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                var selectedRow = jTable.getSelectedRow();
                if (selectedRow != -1) {
                    var dialog = new CategoryDialog(categoryTableModel, transactionCrudService, templateCrudService, categoryTableModel.getEntity(selectedRow));
                    dialog.show(jTable, "Edit Category")
                            .ifPresent(categoryTableModel::updateRow);
                }
            }
        });
        return jTable;
    }

    private void addCategoryButton(JPanel buttonPanel) {
        JButton addButton = new JButton("Add Category");
        addButton.addActionListener(e -> {
            var dialog = new CategoryDialog(categoryTableModel, transactionCrudService, templateCrudService, null);
            dialog.show(table, "Add Category")
                    .ifPresent(categoryTableModel::addRow);
        });

        buttonPanel.add(addButton);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));

        add(buttonPanel, BorderLayout.SOUTH);
    }
}
