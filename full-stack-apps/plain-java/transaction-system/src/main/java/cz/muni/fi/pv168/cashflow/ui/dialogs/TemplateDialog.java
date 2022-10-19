package cz.muni.fi.pv168.cashflow.ui.dialogs;

import cz.muni.fi.pv168.cashflow.business.model.Category;
import cz.muni.fi.pv168.cashflow.business.model.Currency;
import cz.muni.fi.pv168.cashflow.business.model.Template;
import cz.muni.fi.pv168.cashflow.business.model.TransactionType;
import cz.muni.fi.pv168.cashflow.ui.model.CategoryTableModel;
import cz.muni.fi.pv168.cashflow.ui.model.CurrencyTableModel;
import cz.muni.fi.pv168.cashflow.ui.model.TemplateTableModel;
import cz.muni.fi.pv168.cashflow.ui.renderers.CategoryRenderer;
import cz.muni.fi.pv168.cashflow.ui.renderers.CurrencyRenderer;
import cz.muni.fi.pv168.cashflow.ui.renderers.TransactionTypeRenderer;
import cz.muni.fi.pv168.cashflow.utils.PlaceholderTextField;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.UUID;

public class TemplateDialog extends EntityDialog<Template> {

    private final PlaceholderTextField templateNameField = new PlaceholderTextField("E.g. ExampleTemplate");
    private final PlaceholderTextField receiverField = new PlaceholderTextField("E.g. John Doe");
    private final ComboBoxModel<TransactionType> transactionTypeModel = new DefaultComboBoxModel<>(TransactionType.values());
    private final PlaceholderTextField amountField = new PlaceholderTextField("E.g. 1000");
    private final ComboBoxModel<Currency> currencyModel;
    private final ComboBoxModel<Category> categoryModel;
    private final PlaceholderTextField variableSymbolField = new PlaceholderTextField("E.g. 1234567890");
    private final PlaceholderTextField constantSymbolField = new PlaceholderTextField("E.g. 1234");
    private final PlaceholderTextField specificSymbolField = new PlaceholderTextField("E.g. 1234567890");
    private final PlaceholderTextField messageField = new PlaceholderTextField("E.g. Message");
    private Template template;

    public TemplateDialog(TemplateTableModel templateTableModel, CategoryTableModel categoryTableModel, CurrencyTableModel currencyTableModel, Template template) {
        this.categoryModel = new DefaultComboBoxModel<>(categoryTableModel.getCategories().toArray(new Category[0]));
        this.currencyModel = new DefaultComboBoxModel<>(currencyTableModel.getCurrencies().toArray(new Currency[0]));
        if (template != null) {
            buttonPanel.add(deleteButton);
            deleteButton.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    var deleteDialog = new DeleteDialog(template.getTemplateName(), "template");
                    deleteDialog.show(panel, "Delete Template")
                            .ifPresent(cat -> templateTableModel.deleteRow(template));
                }
            });
            this.template = template;
            setValues();
            templateNameField.setForeground(Color.black);
            receiverField.setForeground(Color.black);
            amountField.setForeground(Color.black);
            variableSymbolField.setForeground(Color.black);
            constantSymbolField.setForeground(Color.black);
            specificSymbolField.setForeground(Color.black);
            messageField.setForeground(Color.black);
        }
        addFields();
    }

    private static boolean isaBoolean(String templateName, String receiver, TransactionType transactionType, String amount, Currency currency, Category category) {
        return templateName.isEmpty() || receiver.isEmpty() || transactionType == null || amount.isEmpty() || currency == null || category == null;
    }

    private void setValues() {
        templateNameField.setText(template.getTemplateName());
        receiverField.setText(template.getReceiver());
        transactionTypeModel.setSelectedItem(template.getTransactionType());
        amountField.setText(String.valueOf(template.getAmount()));
        currencyModel.setSelectedItem(template.getCurrency());
        categoryModel.setSelectedItem(template.getCategory());
        variableSymbolField.setText(template.getVariableSymbol());
        constantSymbolField.setText(template.getConstantSymbol());
        specificSymbolField.setText(template.getSpecificSymbol());
        messageField.setText(template.getMessage());
    }

    private void addFields() {
        var transactionTypeComboBox = new JComboBox<>(transactionTypeModel);
        transactionTypeComboBox.setRenderer(new TransactionTypeRenderer());

        var currencyComboBox = new JComboBox<>(currencyModel);
        currencyComboBox.setRenderer(new CurrencyRenderer());

        var categoryComboBox = new JComboBox<>(categoryModel);
        categoryComboBox.setRenderer(new CategoryRenderer());

        amountField.setToolTipText("Enter a valid number");

        add("Template name*:", templateNameField);
        add("Receiver:*", receiverField);
        add("Transaction type:*", transactionTypeComboBox);
        add("Amount:*", amountField);
        add("Currency:*", currencyComboBox);
        add("Category:*", categoryComboBox);
        add("Variable symbol:", variableSymbolField);
        add("Constant symbol:", constantSymbolField);
        add("Specific symbol:", specificSymbolField);
        add("Message:", messageField);
    }

    @Override
    Template getEntity() {
        String templateName = templateNameField.getTextFinal();
        String receiver = receiverField.getTextFinal();
        TransactionType transactionType = (TransactionType) transactionTypeModel.getSelectedItem();
        double amount = Double.parseDouble(amountField.getTextFinal());
        Currency currency = (Currency) currencyModel.getSelectedItem();
        Category category = (Category) categoryModel.getSelectedItem();
        String variableSymbol = variableSymbolField.getTextFinal();
        String constantSymbol = constantSymbolField.getTextFinal();
        String specificSymbol = specificSymbolField.getTextFinal();
        String message = messageField.getTextFinal();

        if (template == null) {
            this.template = new Template(UUID.randomUUID().toString(), templateName, receiver, transactionType, amount, currency, category,
                    variableSymbol, constantSymbol, specificSymbol, message);
        } else {
            template.setTemplateName(templateName);
            template.setReceiver(receiver);
            template.setTransactionType(transactionType);
            template.setAmount(amount);
            template.setCurrency(currency);
            template.setCategory(category);
            template.setVariableSymbol(variableSymbol);
            template.setConstantSymbol(constantSymbol);
            template.setSpecificSymbol(specificSymbol);
            template.setMessage(message);
        }
        return template;
    }

    @Override
    boolean validateFields() {
        String templateName = templateNameField.getText().trim();
        String receiver = receiverField.getText().trim();
        TransactionType transactionType = (TransactionType) transactionTypeModel.getSelectedItem();
        String amount = amountField.getText().trim();
        Currency currency = (Currency) currencyModel.getSelectedItem();
        Category category = (Category) categoryModel.getSelectedItem();

        if (isaBoolean(templateName, receiver, transactionType, amount, currency, category)) {
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
        String templateName = templateNameField.getTextFinal().trim();
        String receiver = receiverField.getTextFinal().trim();
        TransactionType transactionType = (TransactionType) transactionTypeModel.getSelectedItem();
        String amount = amountField.getTextFinal().trim();
        Currency currency = (Currency) currencyModel.getSelectedItem();
        Category category = (Category) categoryModel.getSelectedItem();
        return isaBoolean1(templateName, receiver, transactionType, amount, currency, category);
    }

    @Override
    void resetForm() {
        templateNameField.setText("");
        receiverField.setText("");
        transactionTypeModel.setSelectedItem(transactionTypeModel.getElementAt(0));
        amountField.setText("");
        currencyModel.setSelectedItem(currencyModel.getElementAt(0));
        categoryModel.setSelectedItem(categoryModel.getElementAt(0));
        variableSymbolField.setText("");
        constantSymbolField.setText("");
        specificSymbolField.setText("");
        messageField.setText("");
    }

    private boolean isaBoolean1(String templateName, String receiver, TransactionType transactionType, String amount, Currency currency, Category category) {
        return !templateName.isEmpty() && !receiver.isEmpty() && transactionType != null && isValidAmount(amount) && currency != null && category != null && isValidAmount(amount);
    }

    private boolean isValidAmount(String amount) {
        try {
            var result = Double.parseDouble(amount);
            return result != 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
