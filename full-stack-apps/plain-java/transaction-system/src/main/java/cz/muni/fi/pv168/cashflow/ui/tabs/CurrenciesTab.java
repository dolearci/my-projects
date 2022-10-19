package cz.muni.fi.pv168.cashflow.ui.tabs;

import cz.muni.fi.pv168.cashflow.business.model.Currency;
import cz.muni.fi.pv168.cashflow.business.model.Template;
import cz.muni.fi.pv168.cashflow.business.model.Transaction;
import cz.muni.fi.pv168.cashflow.business.service.crud.CrudService;
import cz.muni.fi.pv168.cashflow.ui.dialogs.CurrencyDialog;
import cz.muni.fi.pv168.cashflow.ui.model.CurrencyTableModel;
import cz.muni.fi.pv168.cashflow.ui.renderers.BasicCellRenderer;

import javax.swing.BorderFactory;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.TableCellEditor;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CurrenciesTab extends JPanel {

    private final CurrencyTableModel currencyTableModel;
    private final CrudService<Transaction> transactionCrudService;
    private final CrudService<Template> templateCrudService;
    private final Currency mainCurrency;
    private JTable table;

    public CurrenciesTab(CurrencyTableModel currencyTableModel, CrudService<Transaction> transactionCrudService, CrudService<Template> templateCrudService, Currency mainCurrency) {
        this.currencyTableModel = currencyTableModel;
        this.transactionCrudService = transactionCrudService;
        this.templateCrudService = templateCrudService;
        this.mainCurrency = mainCurrency;

        createUI();
    }

    private void createUI() {
        setLayout(new BorderLayout());
        createLabelsPanel();
        createTable();
        createAddCurrencyButton();
    }

    private void createLabelsPanel() {
        JLabel mainCurrencyLabel = new JLabel(String.format("Main Currency: %s (%s)", mainCurrency.getName(), mainCurrency.getCode()));
        mainCurrencyLabel.setFont(new Font("Arial", Font.BOLD, 20));
        mainCurrencyLabel.setBorder(BorderFactory.createEmptyBorder(30, 20, 30, 10));

        JLabel tableLabel = new JLabel("Available currencies:");
        tableLabel.setFont(new Font("Arial", Font.BOLD, 16));
        tableLabel.setBorder(BorderFactory.createEmptyBorder(0, 20, 10, 10));

        JPanel labelsPanel = new JPanel();
        labelsPanel.setLayout(new BorderLayout());
        labelsPanel.add(mainCurrencyLabel, BorderLayout.NORTH);
        labelsPanel.add(tableLabel, BorderLayout.SOUTH);

        add(labelsPanel, BorderLayout.NORTH);
    }

    private void createTable() {
        JTable jTable = getjTable();
        Font largerFont = new Font("Arial", Font.BOLD, 16);
        jTable.setFont(largerFont);
        jTable.setRowHeight(30);
        jTable.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
        jTable.getTableHeader().setBackground(new Color(144, 213, 236));
        jTable.getTableHeader().setFont(largerFont);

        TableCellEditor editor = new DefaultCellEditor(new JTextField());
        jTable.setCellEditor(editor);
        jTable.setDefaultRenderer(Object.class, new BasicCellRenderer());
        JScrollPane pane = new JScrollPane(jTable);
        pane.setBorder(BorderFactory.createEmptyBorder(20, 15, 0, 15));

        add(pane, BorderLayout.CENTER);
        this.table = jTable;
    }

    private JTable getjTable() {
        JTable jTable = new JTable(currencyTableModel);
        jTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                var selectedRow = jTable.getSelectedRow();
                if (selectedRow != -1) {
                    var dialog = new CurrencyDialog(currencyTableModel, transactionCrudService, templateCrudService, currencyTableModel.getEntity(selectedRow));
                    dialog.show(jTable, "Edit Currency")
                            .ifPresent(currencyTableModel::updateRow);
                }
            }
        });
        return jTable;
    }

    private void createAddCurrencyButton() {
        JButton addButton = new JButton("Add Currency");
        addButton.addActionListener(e -> {
            var dialog = new CurrencyDialog(currencyTableModel, transactionCrudService, templateCrudService, null);
            dialog.show(table, "Add Currency")
                    .ifPresent(currencyTableModel::addRow);
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));

        add(buttonPanel, BorderLayout.SOUTH);
    }
}
