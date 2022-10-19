package cz.muni.fi.pv168.cashflow.ui.tabs;

import cz.muni.fi.pv168.cashflow.business.model.Category;
import cz.muni.fi.pv168.cashflow.business.model.Currency;
import cz.muni.fi.pv168.cashflow.ui.dialogs.TemplateDialog;
import cz.muni.fi.pv168.cashflow.ui.model.CategoryTableModel;
import cz.muni.fi.pv168.cashflow.ui.model.CurrencyTableModel;
import cz.muni.fi.pv168.cashflow.ui.model.TemplateTableModel;
import cz.muni.fi.pv168.cashflow.ui.renderers.BasicCellRenderer;
import cz.muni.fi.pv168.cashflow.ui.renderers.CategoryRenderer;
import cz.muni.fi.pv168.cashflow.ui.renderers.CurrencyRenderer;
import cz.muni.fi.pv168.cashflow.ui.renderers.LocalDateRenderer;

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
import java.time.LocalDate;

public class TemplatesTab extends JPanel {

    private final TemplateTableModel templateTableModel;
    private final CategoryTableModel categoryTableModel;
    private final CurrencyTableModel currencyTableModel;
    private JTable table;

    public TemplatesTab(TemplateTableModel templateTableModel, CategoryTableModel categoryTableModel, CurrencyTableModel currencyTableModel) {
        this.templateTableModel = templateTableModel;
        this.categoryTableModel = categoryTableModel;
        this.currencyTableModel = currencyTableModel;

        setUpTable(templateTableModel);
    }

    private void setUpTable(TemplateTableModel templateTableModel) {
        var jTable = getjTable(templateTableModel);
        jTable.setAutoCreateRowSorter(true);
        Font largerFont = new Font("Arial", Font.PLAIN, 16);
        jTable.setFont(largerFont);
        jTable.getTableHeader().setBackground(new Color(144, 213, 236));
        jTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 16));
        jTable.setRowHeight(30);

        jTable.setDefaultRenderer(Category.class, new CategoryRenderer());
        jTable.setDefaultRenderer(LocalDate.class, new LocalDateRenderer());
        jTable.setDefaultRenderer(Category.class, new CategoryRenderer());
        jTable.setDefaultRenderer(Currency.class, new CurrencyRenderer());
        jTable.setDefaultRenderer(Object.class, new BasicCellRenderer());

        setLayout(new BorderLayout());
        JScrollPane pane = new JScrollPane(jTable);
        pane.setBorder(BorderFactory.createEmptyBorder(20, 15, 0, 15));

        add(pane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        addTemplateButton(buttonPanel);

        var rowSorter = new TableRowSorter<>(templateTableModel);
        jTable.setRowSorter(rowSorter);

        this.table = jTable;
    }

    private JTable getjTable(TemplateTableModel templateTableModel) {
        var jTable = new JTable(templateTableModel);
        jTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                var selectedRow = jTable.getSelectedRow();
                if (selectedRow != -1) {
                    var dialog = new TemplateDialog(templateTableModel, categoryTableModel, currencyTableModel, templateTableModel.getEntity(selectedRow));
                    dialog.show(jTable, "Edit Template")
                            .ifPresent(templateTableModel::updateRow);
                }
            }
        });
        return jTable;
    }

    private void addTemplateButton(JPanel buttonPanel) {
        JButton addButton = new JButton("Add Template");
        addButton.addActionListener(e -> {
            var dialog = new TemplateDialog(templateTableModel, categoryTableModel, currencyTableModel, null);
            dialog.show(table, "Add Template")
                    .ifPresent(templateTableModel::addRow);
        });

        buttonPanel.add(addButton);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));

        add(buttonPanel, BorderLayout.SOUTH);
    }
}
