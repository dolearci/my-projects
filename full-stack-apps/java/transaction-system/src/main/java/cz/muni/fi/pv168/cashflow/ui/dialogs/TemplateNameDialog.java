package cz.muni.fi.pv168.cashflow.ui.dialogs;

import cz.muni.fi.pv168.cashflow.utils.PlaceholderTextField;

import javax.swing.JOptionPane;

public class TemplateNameDialog extends EntityDialog<String> {

    private final PlaceholderTextField templateNameField = new PlaceholderTextField("E.g. ExampleTemplate");

    public TemplateNameDialog() {
        addFields();
    }

    private void addFields() {
        add("Template name*:", templateNameField);
    }

    @Override
    String getEntity() {
        return templateNameField.getText();
    }

    @Override
    boolean validateFields() {
        String templateName = templateNameField.getText().trim();
        if (templateName.isEmpty()) {
            JOptionPane.showMessageDialog(panel, "Template name cannot be empty.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    @Override
    boolean areValid() {
        String templateName = templateNameField.getTextFinal().trim();
        return !templateName.isEmpty();
    }

    @Override
    void resetForm() {
        templateNameField.setText("");
    }
}
