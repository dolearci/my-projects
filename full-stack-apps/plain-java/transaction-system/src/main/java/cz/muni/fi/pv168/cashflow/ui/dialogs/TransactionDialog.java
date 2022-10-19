package cz.muni.fi.pv168.cashflow.ui.dialogs;

import cz.muni.fi.pv168.cashflow.business.model.Category;
import cz.muni.fi.pv168.cashflow.business.model.Currency;
import cz.muni.fi.pv168.cashflow.business.model.Template;
import cz.muni.fi.pv168.cashflow.business.model.Transaction;
import cz.muni.fi.pv168.cashflow.business.model.TransactionType;
import cz.muni.fi.pv168.cashflow.ui.model.CategoryTableModel;
import cz.muni.fi.pv168.cashflow.ui.model.CurrencyTableModel;
import cz.muni.fi.pv168.cashflow.ui.model.LocalDateModel;
import cz.muni.fi.pv168.cashflow.ui.model.TemplateTableModel;
import cz.muni.fi.pv168.cashflow.ui.model.TransactionTableModel;
import cz.muni.fi.pv168.cashflow.ui.renderers.CategoryRenderer;
import cz.muni.fi.pv168.cashflow.ui.renderers.CurrencyRenderer;
import cz.muni.fi.pv168.cashflow.ui.renderers.TemplateRenderer;
import cz.muni.fi.pv168.cashflow.ui.renderers.TransactionTypeRenderer;
import cz.muni.fi.pv168.cashflow.utils.PlaceholderTextField;
import org.jdatepicker.DateModel;
import org.jdatepicker.JDatePicker;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.util.UUID;

public class TransactionDialog extends EntityDialog<Transaction> {

    private final PlaceholderTextField receiverField = new PlaceholderTextField("E.g. John Doe");
    private final ComboBoxModel<TransactionType> transactionTypeModel = new DefaultComboBoxModel<>(TransactionType.values());
    private final PlaceholderTextField amountField = new PlaceholderTextField("E.g. 1000");
    private final ComboBoxModel<Currency> currencyModel;
    private final ComboBoxModel<Category> categoryModel;
    private final DateModel<LocalDate> dateModel = new LocalDateModel();
    private final PlaceholderTextField variableSymbolField = new PlaceholderTextField("E.g. 1234567890");
    private final PlaceholderTextField constantSymbolField = new PlaceholderTextField("E.g. 1234");
    private final PlaceholderTextField specificSymbolField = new PlaceholderTextField("E.g. 1234567890");
    private final PlaceholderTextField messageField = new PlaceholderTextField("E.g. Message");
    private ComboBoxModel<Template> templateModel;
    private JComboBox<Template> templateComboBox;
    private Transaction transaction;

    private void setTemplateFieldsColor() {
        receiverField.setTextColor();
        amountField.setTextColor();
        variableSymbolField.setTextColor();
        constantSymbolField.setTextColor();
        specificSymbolField.setTextColor();
        messageField.setTextColor();
    }

    public TransactionDialog(TransactionTableModel transactionTableModel, CategoryTableModel categoryTableModel, CurrencyTableModel currencyTableModel, TemplateTableModel templateTableModel, Transaction transaction) {
        this.categoryModel = new DefaultComboBoxModel<>(categoryTableModel.getCategories().toArray(new Category[0]));
        this.currencyModel = new DefaultComboBoxModel<>(currencyTableModel.getCurrencies().toArray(new Currency[0]));
        this.templateModel = new DefaultComboBoxModel<>(templateTableModel.getTemplates().toArray(new Template[0]));

        buttonPanel.add(resetButton, 0);

        buttonPanel.add(saveAsTemplateButton, 1);
        saveAsTemplateButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                var transactionDialog = new TemplateNameDialog();
                transactionDialog.show(panel, "Save as template")
                        .ifPresent(templateName -> saveAsTemplate(templateTableModel, templateName));
            }
        });

        if (transaction != null) {
            saveAsTemplateButton.setEnabled(true);
            buttonPanel.add(deleteButton);
            deleteButton.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    var deleteDialog = new DeleteDialog("to " + transaction.getReceiver(), "transaction");
                    deleteDialog.show(panel, "Delete Transaction")
                            .ifPresent(cat -> transactionTableModel.deleteRow(transaction));
                }
            });
            this.transaction = transaction;
            setValues();
            templateModel.setSelectedItem(null);
            setTemplateFieldsColor();
        }
        addFields();
    }

    private static boolean isaBoolean(String receiver, TransactionType transactionType, String amount, Currency currency, Category category, LocalDate date) {
        return receiver.isEmpty() || transactionType == null || amount.isEmpty() || currency == null || category == null || date == null;
    }

    private void saveAsTemplate(TemplateTableModel templateTableModel, String templateName) {
        var template = new Template(UUID.randomUUID().toString(), templateName, receiverField.getTextFinal(), (TransactionType) transactionTypeModel.getSelectedItem(),
                Double.parseDouble(amountField.getTextFinal()), (Currency) currencyModel.getSelectedItem(), (Category) categoryModel.getSelectedItem(),
                variableSymbolField.getTextFinal(), constantSymbolField.getTextFinal(), specificSymbolField.getTextFinal(), messageField.getTextFinal());
        templateTableModel.addRow(template);
        this.templateModel = new DefaultComboBoxModel<>(templateTableModel.getTemplates().toArray(new Template[0]));
        templateModel.setSelectedItem(null);
        templateComboBox.setModel(templateModel);
    }

    private void setValues() {
        receiverField.setText(transaction.getReceiver());
        transactionTypeModel.setSelectedItem(transaction.getTransactionType());
        amountField.setText(String.valueOf(transaction.getAmount()));
        currencyModel.setSelectedItem(transaction.getCurrency());
        categoryModel.setSelectedItem(transaction.getCategory());
        dateModel.setValue(transaction.getDate());
        variableSymbolField.setText(transaction.getVariableSymbol());
        constantSymbolField.setText(transaction.getConstantSymbol());
        specificSymbolField.setText(transaction.getSpecificSymbol());
        messageField.setText(transaction.getMessage());
    }

    private void addFields() {
        templateModel.setSelectedItem(null);
        templateComboBox = new JComboBox<>(templateModel);
        templateComboBox.setRenderer(new TemplateRenderer());

        var transactionTypeComboBox = new JComboBox<>(transactionTypeModel);
        transactionTypeComboBox.setRenderer(new TransactionTypeRenderer());

        var currencyComboBox = new JComboBox<>(currencyModel);
        currencyComboBox.setRenderer(new CurrencyRenderer());

        var categoryComboBox = new JComboBox<>(categoryModel);
        categoryComboBox.setRenderer(new CategoryRenderer());

        var datePicker = new JDatePicker(dateModel);
        dateModel.setValue(LocalDate.now());
//        var range = new RangeConstraint(new Date(), new Date(Long.MAX_VALUE));
//        datePicker.addDateSelectionConstraint(range); //omezeni datoveho rozsahu

        amountField.setToolTipText("Enter a valid number");

        templateComboBox.addActionListener(e -> chooseTemplate());
        add("Fill with transaction template:", templateComboBox);
        add("Receiver:*", receiverField);
        add("Transaction type:*", transactionTypeComboBox);
        add("Amount:*", amountField);
        add("Currency:*", currencyComboBox);
        add("Category:*", categoryComboBox);
        add("Date:*", datePicker);
        add("Variable symbol:", variableSymbolField);
        add("Constant symbol:", constantSymbolField);
        add("Specific symbol:", specificSymbolField);
        add("Message:", messageField);
    }

    @Override
    Transaction getEntity() {
        String receiver = receiverField.getTextFinal();
        TransactionType transactionType = (TransactionType) transactionTypeModel.getSelectedItem();
        double amount = Double.parseDouble(amountField.getTextFinal());
        Currency currency = (Currency) currencyModel.getSelectedItem();
        Category category = (Category) categoryModel.getSelectedItem();
        LocalDate date = dateModel.getValue();
        String variableSymbol = variableSymbolField.getTextFinal();
        String constantSymbol = constantSymbolField.getTextFinal();
        String specificSymbol = specificSymbolField.getTextFinal();
        String message = messageField.getTextFinal();

        if (transaction == null) {
            this.transaction = new Transaction(UUID.randomUUID().toString(), receiver, transactionType, amount, currency, category, date,
                    variableSymbol, constantSymbol, specificSymbol, message);
        } else {
            transaction.setReceiver(receiver);
            transaction.setTransactionType(transactionType);
            transaction.setAmount(amount);
            transaction.setCurrency(currency);
            transaction.setCategory(category);
            transaction.setDate(date);
            transaction.setVariableSymbol(variableSymbol);
            transaction.setConstantSymbol(constantSymbol);
            transaction.setSpecificSymbol(specificSymbol);
            transaction.setMessage(message);
        }

        return transaction;
    }

    private void chooseTemplate() {
        Template template = (Template) templateModel.getSelectedItem();

        if (template != null) {
            receiverField.setText(template.getReceiver());
            transactionTypeModel.setSelectedItem(template.getTransactionType());
            amountField.setText(String.valueOf(template.getAmount()));
            currencyModel.setSelectedItem(template.getCurrency());
            categoryModel.setSelectedItem(template.getCategory());
            variableSymbolField.setText(template.getVariableSymbol());
            constantSymbolField.setText(template.getConstantSymbol());
            specificSymbolField.setText(template.getSpecificSymbol());
            messageField.setText(template.getMessage());

            setTemplateFieldsColor();
        }
    }

    @Override
    boolean validateFields() {
        String receiver = receiverField.getText().trim();
        TransactionType transactionType = (TransactionType) transactionTypeModel.getSelectedItem();
        String amount = amountField.getText().trim();
        Currency currency = (Currency) currencyModel.getSelectedItem();
        Category category = (Category) categoryModel.getSelectedItem();
        LocalDate date = dateModel.getValue();

        if (isaBoolean(receiver, transactionType, amount, currency, category, date)) {
            JOptionPane.showMessageDialog(panel, "Please fill in all the required fields.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        } else if (!isValidAmount(amount)) {
            JOptionPane.showMessageDialog(panel, "Amount must be a valid number.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    @Override
    boolean areValid() {
        String receiver = receiverField.getTextFinal().trim();
        TransactionType transactionType = (TransactionType) transactionTypeModel.getSelectedItem();
        String amount = amountField.getTextFinal().trim();
        Currency currency = (Currency) currencyModel.getSelectedItem();
        Category category = (Category) categoryModel.getSelectedItem();
        LocalDate date = dateModel.getValue();
        return isaBoolean1(receiver, transactionType, amount, currency, category, date);
    }

    @Override
    void resetForm() {
        templateModel.setSelectedItem(null);
        receiverField.setText("");
        transactionTypeModel.setSelectedItem(transactionTypeModel.getElementAt(0));
        amountField.setText("");
        currencyModel.setSelectedItem(currencyModel.getElementAt(0));
        categoryModel.setSelectedItem(categoryModel.getElementAt(0));
        dateModel.setValue(LocalDate.now());
        variableSymbolField.setText("");
        constantSymbolField.setText("");
        specificSymbolField.setText("");
        messageField.setText("");
    }

    private boolean isaBoolean1(String receiver, TransactionType transactionType, String amount, Currency currency, Category category, LocalDate date) {
        return !receiver.isEmpty() && transactionType != null && currency != null && category != null && date != null && isValidAmount(amount);
    }

    private boolean isValidAmount(String amount) {
        try {
            var result = Double.parseDouble(amount);
            return result > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
