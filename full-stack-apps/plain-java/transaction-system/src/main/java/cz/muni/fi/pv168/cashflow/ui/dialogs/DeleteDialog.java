package cz.muni.fi.pv168.cashflow.ui.dialogs;

import javax.swing.JLabel;

public class DeleteDialog extends EntityDialog<String> {

    private final String entity;
    private final String entityName;

    public DeleteDialog(String entityName, String entity) {
        this.entityName = entityName;
        this.entity = entity;
        addFields();
    }

    private void addFields() {
        add("Are you sure you want to delete " + entity + ":  ", new JLabel(entityName + "?"));
    }

    @Override
    String getEntity() {
        return entityName;
    }

    @Override
    boolean validateFields() {
        return true;
    }

    @Override
    boolean areValid() {
        return true;
    }

    @Override
    void resetForm() {

    }
}
