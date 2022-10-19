package cz.muni.fi.pv168.cashflow.ui.dialogs;

import cz.muni.fi.pv168.cashflow.business.model.Currency;
import cz.muni.fi.pv168.cashflow.business.model.Template;
import cz.muni.fi.pv168.cashflow.business.model.Transaction;
import cz.muni.fi.pv168.cashflow.business.service.crud.CrudService;
import cz.muni.fi.pv168.cashflow.ui.model.CurrencyTableModel;
import cz.muni.fi.pv168.cashflow.utils.PlaceholderTextField;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.UUID;

public class CurrencyDialog extends EntityDialog<Currency> {

    private final PlaceholderTextField nameField = new PlaceholderTextField("E.g. Czech crown");
    private final PlaceholderTextField codeField = new PlaceholderTextField("E.g. CZK");
    private final PlaceholderTextField rateField = new PlaceholderTextField("E.g. 1.0");
    private Currency currency;

    public CurrencyDialog(CurrencyTableModel currencyTableModel, CrudService<Transaction> transactionCrudService, CrudService<Template> templateCrudService, Currency currency) {
        if (currency != null) {
            buttonPanel.add(deleteButton);
            if (transactionCrudService.findAll().stream().anyMatch(e -> e.getCurrency().equals(currency)) ||
                    templateCrudService.findAll().stream().anyMatch(e -> e.getCurrency().equals(currency)) ||
                    currency.getName().equals("Czech koruna")) {
                deleteButton.setEnabled(false);
                deleteButton.setToolTipText("Cannot delete currency that is used in transactions or templates.");
                if (currency.getName().equals("Czech koruna")) {
                    deleteButton.setToolTipText("Cannot delete default currency.");
                }
            } else {
                deleteButton.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent e) {
                        var deleteDialog = new DeleteDialog(currency.getName(), "currency");
                        deleteDialog.show(panel, "Delete Currency")
                                .ifPresent(cat -> currencyTableModel.deleteRow(currency));
                    }
                });
            }
            this.currency = currency;
            setValues();
            nameField.setForeground(Color.black);
            codeField.setForeground(Color.black);
            rateField.setForeground(Color.black);
        }
        addFields();
    }

    private void setValues() {
        nameField.setText(currency.getName());
        codeField.setText(currency.getCode());
        rateField.setText(String.valueOf(currency.getRate()));
    }

    private void addFields() {
        add("Name:*", nameField);
        codeField.setToolTipText("Enter a 3-letter currency code");
        add("Code:*", codeField);
        rateField.setToolTipText("Enter a valid number");
        JPanel ratePanel = new JPanel();
        ratePanel.setLayout(new BoxLayout(ratePanel, BoxLayout.X_AXIS));
        ratePanel.add(new JLabel(" 1 NEW CURRENCY = "));
        ratePanel.add(rateField);
        ratePanel.add(new JLabel("CZK"));
        add("Exchange rate:*", ratePanel);
    }

    @Override
    boolean validateFields() {
        boolean nameValid = !nameField.getText().trim().isEmpty();
        boolean codeValid = !codeField.getText().trim().isEmpty();
        boolean rateValid = isValidRate(rateField.getText());
        if (!nameValid || !codeValid) {
            JOptionPane.showMessageDialog(panel, "Currency name is required", "Validation Error", JOptionPane.ERROR_MESSAGE);
        } else if (!rateValid) {
            JOptionPane.showMessageDialog(panel, "Enter a valid Exchange rate value", "Validation Error", JOptionPane.ERROR_MESSAGE);
        }

        return nameValid && codeValid && rateValid;
    }

    @Override
    boolean areValid() {
        boolean nameValid = !nameField.getTextFinal().trim().isEmpty();
        boolean codeValid = !codeField.getTextFinal().trim().isEmpty();
        boolean rateValid = isValidRate(rateField.getTextFinal());
        return nameValid && codeValid && rateValid;
    }

    @Override
    void resetForm() {
        nameField.setText("");
        codeField.setText("");
        rateField.setText("");
    }

    private boolean isValidRate(String text) {
        try {
            double rate = Double.parseDouble(text);
            return rate >= 0; // You can add more rate validation logic here
        } catch (NumberFormatException e) {
            return false;
        }
    }

    @Override
    Currency getEntity() {
        if (currency == null) {
            this.currency = new Currency(UUID.randomUUID().toString(), nameField.getTextFinal(), codeField.getTextFinal().toUpperCase(), Double.parseDouble(rateField.getTextFinal()));
        } else {
            currency.setName(nameField.getTextFinal());
            currency.setCode(codeField.getTextFinal().toUpperCase());
            currency.setRate(Double.parseDouble(rateField.getTextFinal()));
        }
        return currency;
    }
}
