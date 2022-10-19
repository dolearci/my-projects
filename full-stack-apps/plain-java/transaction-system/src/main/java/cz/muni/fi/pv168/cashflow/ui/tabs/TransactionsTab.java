package cz.muni.fi.pv168.cashflow.ui.tabs;

import cz.muni.fi.pv168.cashflow.business.model.Category;
import cz.muni.fi.pv168.cashflow.business.model.Currency;
import cz.muni.fi.pv168.cashflow.business.model.TransactionType;
import cz.muni.fi.pv168.cashflow.ui.dialogs.TransactionDialog;
import cz.muni.fi.pv168.cashflow.ui.filters.TransactionTableFilter;
import cz.muni.fi.pv168.cashflow.ui.filters.components.FilterComboBoxBuilder;
import cz.muni.fi.pv168.cashflow.ui.filters.components.FilterDatePickerBuilder;
import cz.muni.fi.pv168.cashflow.ui.filters.values.SpecialFilterCategoryValues;
import cz.muni.fi.pv168.cashflow.ui.filters.values.SpecialFilterCurrencyValues;
import cz.muni.fi.pv168.cashflow.ui.filters.values.SpecialFilterTransactionTypeValues;
import cz.muni.fi.pv168.cashflow.ui.model.CategoryTableModel;
import cz.muni.fi.pv168.cashflow.ui.model.ComboBoxModelAdapter;
import cz.muni.fi.pv168.cashflow.ui.model.CurrencyTableModel;
import cz.muni.fi.pv168.cashflow.ui.model.CustomValuesModelDecorator;
import cz.muni.fi.pv168.cashflow.ui.model.LocalDateModel;
import cz.muni.fi.pv168.cashflow.ui.model.TemplateTableModel;
import cz.muni.fi.pv168.cashflow.ui.model.TransactionTableModel;
import cz.muni.fi.pv168.cashflow.ui.renderers.BasicCellRenderer;
import cz.muni.fi.pv168.cashflow.ui.renderers.CategoryRenderer;
import cz.muni.fi.pv168.cashflow.ui.renderers.CurrencyRenderer;
import cz.muni.fi.pv168.cashflow.ui.renderers.LocalDateRenderer;
import cz.muni.fi.pv168.cashflow.ui.renderers.SpecialFilterCategoryValuesRenderer;
import cz.muni.fi.pv168.cashflow.ui.renderers.SpecialFilterCurrencyValuesRenderer;
import cz.muni.fi.pv168.cashflow.ui.renderers.SpecialFilterTransactionTypeValuesRenderer;
import cz.muni.fi.pv168.cashflow.ui.renderers.TransactionTypeRenderer;
import cz.muni.fi.pv168.cashflow.utils.Either;
import org.jdatepicker.JDatePicker;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.List;
import java.util.Locale;

public class TransactionsTab extends JPanel {

    public static final Category[] CATEGORIES = new Category[0];
    public static final Currency[] CURRENCIES = new Currency[0];
    private final TransactionTableModel transactionTableModel;
    private final CategoryTableModel categoryTableModel;
    private final CurrencyTableModel currencyTableModel;
    private final TemplateTableModel templateTableModel;
    private final Currency mainCurrency;
    private final NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.getDefault());
    private JLabel filteredLabel;
    private JLabel balanceLabel;
    private JLabel categoryBalanceLabel;
    private JComboBox<Category> categoryComboBox;
    private JTable table;

    public TransactionsTab(TransactionTableModel transactionTableModel, CategoryTableModel categoryTableModel, CurrencyTableModel currencyTableModel, TemplateTableModel templateTableModel, Currency mainCurrency) {
        this.transactionTableModel = transactionTableModel;
        this.categoryTableModel = categoryTableModel;
        this.currencyTableModel = currencyTableModel;
        this.templateTableModel = templateTableModel;
        this.mainCurrency = mainCurrency;
        setUpTable(transactionTableModel);
        updateFilterLabel();
        table.getModel().addTableModelListener(e -> {
            updateFilterLabel();
            updateCategoryBalanceLabel();
            balanceLabel.setText("Balance: " + numberFormat.format(getBalance()) + "         Main Currency: " + mainCurrency.getCode());
        });
    }

    private static JComboBox<Either<SpecialFilterTransactionTypeValues, TransactionType>> createTransactionTypeFilter(
            TransactionTableFilter transactionTableFilter, JButton resetButton) {
        return FilterComboBoxBuilder.create(SpecialFilterTransactionTypeValues.class, TransactionType.values())
                .setSelectedItem(SpecialFilterTransactionTypeValues.ALL)
                .setSpecialValuesRenderer(new SpecialFilterTransactionTypeValuesRenderer())
                .setValuesRenderer(new TransactionTypeRenderer())
                .setFilter(transactionTableFilter::filterTransactionType)
                .setResetButton(resetButton)
                .build();
    }

    private static JComboBox<Either<SpecialFilterCategoryValues, Category>> createCategoryFilter(
            TransactionTableFilter transactionTableFilter, CategoryTableModel categoryTableModel, JButton resetButton) {
        return FilterComboBoxBuilder.create(SpecialFilterCategoryValues.class, categoryTableModel.getCategories().toArray(CATEGORIES))
                .setSelectedItem(SpecialFilterCategoryValues.ALL)
                .setSpecialValuesRenderer(new SpecialFilterCategoryValuesRenderer())
                .setValuesRenderer(new CategoryRenderer())
                .setFilter(transactionTableFilter::filterCategory)
                .setResetButton(resetButton)
                .build();
    }

    private static JComboBox<Either<SpecialFilterCurrencyValues, Currency>> createCurrencyFilter(
            TransactionTableFilter transactionTableFilter, CurrencyTableModel currencyTableModel, JButton resetButton) {
        return FilterComboBoxBuilder.create(SpecialFilterCurrencyValues.class, currencyTableModel.getCurrencies().toArray(CURRENCIES))
                .setSelectedItem(SpecialFilterCurrencyValues.ALL)
                .setSpecialValuesRenderer(new SpecialFilterCurrencyValuesRenderer())
                .setValuesRenderer(new CurrencyRenderer())
                .setFilter(transactionTableFilter::filterCurrency)
                .setResetButton(resetButton)
                .build();
    }

    private JDatePicker createDateFromFilter(
            TransactionTableFilter transactionTableFilter, JButton resetButton) {
        var dateModel = new LocalDateModel();
        //dateModel.setValue(LocalDate.now().with(TemporalAdjusters.firstDayOfMonth()));
        return FilterDatePickerBuilder.create()
                .setFilter(transactionTableFilter::filterDateFrom)
                .setResetButton(resetButton)
                .build(dateModel);
    }

    private JDatePicker createDateToFilter(
            TransactionTableFilter transactionTableFilter, JButton resetButton) {
        var dateModel = new LocalDateModel();
        //dateModel.setValue(LocalDate.now().with(TemporalAdjusters.lastDayOfMonth()));
        return FilterDatePickerBuilder.create()
                .setFilter(transactionTableFilter::filterDateTo)
                .setResetButton(resetButton)
                .build(dateModel);
    }

    private void setUpTable(TransactionTableModel transactionTableModel) {
        var jTable = getjTable(transactionTableModel);
        jTable.setAutoCreateRowSorter(true);
        Font largerFont = new Font("Arial", Font.PLAIN, 16);
        jTable.setFont(largerFont);
        jTable.getTableHeader().setBackground(new Color(144, 213, 236));
        jTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 16));
        jTable.setRowHeight(30);

        jTable.setDefaultRenderer(LocalDate.class, new LocalDateRenderer());
        jTable.setDefaultRenderer(Category.class, new CategoryRenderer());
        jTable.setDefaultRenderer(Currency.class, new CurrencyRenderer());
        jTable.setDefaultRenderer(Object.class, new BasicCellRenderer());

        setLayout(new BorderLayout());
        JScrollPane pane = new JScrollPane(jTable);
        pane.setBorder(BorderFactory.createEmptyBorder(20, 15, 0, 15));

        add(pane, BorderLayout.CENTER);

        JPanel topPanel = getTopPanel();
        add(topPanel, BorderLayout.NORTH);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(0, 15, 10, 15));
        addTransactionDetail(bottomPanel);

        var rowSorter = new TableRowSorter<>(transactionTableModel);
        var transactionTableFilter = new TransactionTableFilter(rowSorter);
        jTable.setRowSorter(rowSorter);
        rowSorter.setSortKeys(List.of(new RowSorter.SortKey(5, SortOrder.DESCENDING)));

        var toolbar = new JToolBar();
        JButton resetFiltersButton = new JButton("Reset filters");

        var transactionTypeFilter = createTransactionTypeFilter(transactionTableFilter, resetFiltersButton);
        transactionTypeFilter.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
        toolbar.add(transactionTypeFilter);

        var categoryFilter = createCategoryFilter(transactionTableFilter, categoryTableModel, resetFiltersButton);
        categoryTableModel.addTableModelListener(e -> {
            var categories = new ComboBoxModelAdapter<>(new DefaultComboBoxModel<>(categoryTableModel.getCategories().toArray(CATEGORIES)));
            categoryFilter.setModel(CustomValuesModelDecorator.addCustomValues(SpecialFilterCategoryValues.class, categories));
            categoryFilter.setSelectedItem(Either.left(SpecialFilterCategoryValues.ALL));
        });
        categoryFilter.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
        toolbar.add(categoryFilter);

        var currencyFilter = createCurrencyFilter(transactionTableFilter, currencyTableModel, resetFiltersButton);
        currencyTableModel.addTableModelListener(e -> {
            var currencies = new ComboBoxModelAdapter<>(new DefaultComboBoxModel<>(currencyTableModel.getCurrencies().toArray(CURRENCIES)));
            currencyFilter.setModel(CustomValuesModelDecorator.addCustomValues(SpecialFilterCurrencyValues.class, currencies));
            currencyFilter.setSelectedItem(Either.left(SpecialFilterCurrencyValues.ALL));
        });
        currencyFilter.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
        toolbar.add(currencyFilter);

        toolbar.add(new JLabel("Date from: "));
        var dateFromFilter = createDateFromFilter(transactionTableFilter, resetFiltersButton);
        dateFromFilter.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
        toolbar.add(dateFromFilter);

        toolbar.add(new JLabel("Date to: "));
        var dateToFilter = createDateToFilter(transactionTableFilter, resetFiltersButton);
        dateToFilter.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
        toolbar.add(dateToFilter);

        resetFiltersButton.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
        toolbar.add(resetFiltersButton);

        toolbar.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));

        topPanel.add(toolbar);
        this.table = jTable;
    }

    private JTable getjTable(TransactionTableModel transactionTableModel) {
        var jTable = new JTable(transactionTableModel);
        jTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                var selectedRow = table.getRowSorter().convertRowIndexToModel(table.getSelectedRow());
                if (selectedRow != -1) {
                    var dialog = new TransactionDialog(transactionTableModel, categoryTableModel, currencyTableModel, templateTableModel, transactionTableModel.getEntity(selectedRow));
                    dialog.show(jTable, "Edit Transaction")
                            .ifPresent(transactionTableModel::updateRow);
                }
            }
        });
        return jTable;
    }

    private void addTransactionDetail(JPanel bottomPanel) {
        JPanel filteredPanel = new JPanel();
        filteredLabel = new JLabel("Filtered balance: " + numberFormat.format(getBalance()) + " " + mainCurrency.getCode());
        filteredLabel.setFont(new Font("Arial", Font.BOLD, 20));
        filteredPanel.add(filteredLabel);

        JPanel categoryPanel = new JPanel(new BorderLayout());
        JPanel categoryLabelPanel = new JPanel(new BorderLayout());
        JLabel categoryLabel = new JLabel("Balance for category: ");
        categoryLabel.setFont(new Font("Arial", Font.BOLD, 15));
        categoryLabelPanel.add(categoryLabel, BorderLayout.WEST);

        categoryComboBox = new JComboBox<>(categoryTableModel.getCategories().toArray(CATEGORIES));
        categoryTableModel.addTableModelListener(e -> {
            var selectedItem = categoryComboBox.getSelectedItem();
            categoryComboBox.setModel(new ComboBoxModelAdapter<>(new DefaultComboBoxModel<>(categoryTableModel.getCategories().toArray(CATEGORIES))));
            categoryComboBox.setSelectedItem(selectedItem);
        });
        categoryComboBox.setSelectedItem(null);
        categoryComboBox.setRenderer(new CategoryRenderer());
        categoryLabelPanel.add(categoryComboBox, BorderLayout.EAST);
        categoryLabelPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        categoryPanel.add(categoryLabelPanel, BorderLayout.NORTH);

        categoryBalanceLabel = new JLabel();
        categoryBalanceLabel.setFont(new Font("Arial", Font.BOLD, 15));
        categoryPanel.add(categoryBalanceLabel, BorderLayout.SOUTH);

        categoryComboBox.addActionListener(e -> {
        });

        JPanel statsPanel = new JPanel(new BorderLayout());
        statsPanel.add(filteredPanel, BorderLayout.WEST);
        statsPanel.add(categoryPanel, BorderLayout.EAST);
        bottomPanel.add(statsPanel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        addTransactionButton(buttonPanel);

        bottomPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(bottomPanel, BorderLayout.SOUTH);
    }

    private void addTransactionButton(JPanel buttonPanel) {
        JButton addButton = new JButton("Add Transaction");
        addButton.addActionListener(e -> {
            var dialog = new TransactionDialog(transactionTableModel, categoryTableModel, currencyTableModel, templateTableModel, null);
            dialog.show(table, "Add Transaction")
                    .ifPresent(transactionTableModel::addRow);
        });

        buttonPanel.add(addButton);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));

        add(buttonPanel, BorderLayout.SOUTH);
    }

    private JPanel getTopPanel() {
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        balanceLabel = new JLabel("Balance: " + numberFormat.format(getBalance()) + "         Main Currency: " + mainCurrency.getCode());
        balanceLabel.setFont(new Font("Arial", Font.BOLD, 20));
        balanceLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        balanceLabel.setBorder(new EmptyBorder(20, 0, 20, 0));
        topPanel.add(balanceLabel);

        return topPanel;
    }

    private void updateFilterLabel() {
        SwingUtilities.invokeLater(() -> {
            ((AbstractTableModel) table.getModel()).fireTableDataChanged();
            BigDecimal balance = new BigDecimal(0);
            int rowCount = table == null ? 0 : table.getRowCount();
            for (int i = 0; i < rowCount; i++) {
                var type = table.getValueAt(i, 1);
                var amount = table.getValueAt(i, 2);
                var currency = table.getValueAt(i, 3);
                if (type == TransactionType.INCOME)
                    balance = balance.add(convert(new BigDecimal(amount.toString()), (Currency) currency));
                else if (type == TransactionType.OUTCOME)
                    balance = balance.subtract(convert(new BigDecimal(amount.toString()), (Currency) currency));
            }
            filteredLabel.setText("Filtered balance: " + numberFormat.format(balance) + " " + mainCurrency.getCode());
        });
    }

    private void updateCategoryBalanceLabel() {
        BigDecimal categoryBalance = new BigDecimal(0);
        int categoryTransactionCount = 0;
        if (categoryComboBox.getSelectedItem() != null) {
            int rowCount = transactionTableModel.getRowCount();
            for (int i = 0; i < rowCount; i++) {
                var transaction = transactionTableModel.getEntity(i);
                if (transaction.getCategory().equals(categoryComboBox.getSelectedItem())) {
                    if (transaction.getTransactionType() == TransactionType.INCOME)
                        categoryBalance = categoryBalance.add(convert(BigDecimal.valueOf(transaction.getAmount()), transaction.getCurrency()));
                    else if (transaction.getTransactionType() == TransactionType.OUTCOME)
                        categoryBalance = categoryBalance.subtract(convert(BigDecimal.valueOf(transaction.getAmount()), transaction.getCurrency()));
                    categoryTransactionCount += 1;
                }
            }
        }
        int totalTransactionCount = transactionTableModel.getRowCount();
        String percentageOfAll = totalTransactionCount > 0 ? String.valueOf((categoryTransactionCount * 100) / totalTransactionCount) : "N/A";

        categoryBalanceLabel.setText(String.format("%s %s in %s trans. (%s %% of all)",
                numberFormat.format(categoryBalance),
                mainCurrency.getCode(),
                categoryTransactionCount,
                percentageOfAll));
    }

    private BigDecimal getBalance() {
        BigDecimal balance = new BigDecimal(0);
        int rowCount = transactionTableModel.getRowCount();
        for (int i = 0; i < rowCount; i++) {
            var transaction = transactionTableModel.getEntity(i);
            if (transaction.getTransactionType() == TransactionType.INCOME)
                balance = balance.add(convert(BigDecimal.valueOf(transaction.getAmount()), transaction.getCurrency()));
            else if (transaction.getTransactionType() == TransactionType.OUTCOME)
                balance = balance.subtract(convert(BigDecimal.valueOf(transaction.getAmount()), transaction.getCurrency()));
        }
        return balance;
    }

    private BigDecimal convert(BigDecimal amount, Currency from) {
        if (from.getName().equals(mainCurrency.getName())) {
            return amount;
        }
        return amount.multiply(BigDecimal.valueOf(from.getRate()));
    }
}
