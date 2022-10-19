package cz.muni.fi.pv168.cashflow.ui.dialogs;

import net.miginfocom.swing.MigLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dialog;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

public abstract class EntityDialog<E> {

    final JPanel panel = new JPanel();
    final JPanel buttonPanel = new JPanel();
    final JButton okButton;
    final JButton deleteButton;
    final JButton resetButton;
    final JButton saveAsTemplateButton;
    final CustomDialog dialog;

    EntityDialog() {
        panel.setLayout(new MigLayout("wrap 2"));

        // Create the OK button but keep it initially disabled
        okButton = new JButton("OK");
        okButton.setEnabled(false);
        deleteButton = new JButton("Delete");
        resetButton = new JButton("Reset");
        saveAsTemplateButton = new JButton("Save as template");
        saveAsTemplateButton.setEnabled(false);

        // Create a Cancel button
        JButton cancelButton = new JButton("Cancel");

        // Add the OK and Cancel buttons to the button panel
        buttonPanel.add(okButton);
        buttonPanel.add(cancelButton);

        JPanel contentPanel = new JPanel();

        contentPanel.setLayout(new BorderLayout());
        contentPanel.add(panel, BorderLayout.CENTER);
        contentPanel.add(buttonPanel, BorderLayout.PAGE_END);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        dialog = createDialog(contentPanel);

        resetButton.addActionListener(e -> resetForm());

        // Add an action listener to the OK button
        okButton.addActionListener(e -> {
            if (validateFields()) {
                dialog.setResult(JOptionPane.OK_OPTION);
                dialog.dispose();
            }
        });

        deleteButton.addActionListener(e -> dialog.dispose());

        cancelButton.addActionListener(e -> {
            dialog.setResult(JOptionPane.CANCEL_OPTION);
            dialog.dispose();
        });
    }

    void enableOkButton(boolean enable) {
        okButton.setEnabled(enable);
    }

    void enableSetAsTemplateButton(boolean enable) {
        saveAsTemplateButton.setEnabled(enable);
    }

    void add(String labelText, JComponent component) {
        var label = new JLabel(labelText);
        panel.add(label);
        ArrayList<Component> components = new ArrayList<>(Arrays.stream(component.getComponents()).toList());
        components.add(component);
        for (Component comp : components) {
            if (comp instanceof JTextField) {
                ((JTextField) comp).getDocument().addDocumentListener(new DocumentListener() {
                    @Override
                    public void insertUpdate(DocumentEvent e) {
                        handleTextChange();
                    }

                    @Override
                    public void removeUpdate(DocumentEvent e) {
                        handleTextChange();
                    }

                    @Override
                    public void changedUpdate(DocumentEvent e) {
                        handleTextChange();
                    }

                    private void handleTextChange() {
                        boolean valid = areValid();
                        enableOkButton(valid);
                        enableSetAsTemplateButton(valid);
                    }
                });
            } else if (comp instanceof JComboBox) {
                ((JComboBox<?>) comp).addActionListener(e -> {
                    boolean valid = areValid();
                    enableOkButton(valid);
                });
            } else if (comp instanceof JLabel) {
                boolean valid = areValid();
                enableOkButton(valid);
            }
        }
        panel.add(component, "wmin 250lp, grow");
    }

    abstract E getEntity();

    abstract boolean validateFields();

    abstract boolean areValid();

    public Optional<E> show(JComponent parentComponent, String title) {
        dialog.pack();
        dialog.setTitle(title);
        dialog.setLocationRelativeTo(parentComponent);
        dialog.setVisible(true);

        if (dialog.getResult() == JOptionPane.OK_OPTION) {
            var entity = getEntity();
            if (entity == null) {
                return Optional.empty();
            }
            return Optional.of(entity);
        } else {
            return Optional.empty();
        }
    }

    private CustomDialog createDialog(JComponent contentPanel) {
        CustomDialog customDialog = new CustomDialog(SwingUtilities.getWindowAncestor(contentPanel), "title", Dialog.ModalityType.APPLICATION_MODAL);
        customDialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        customDialog.add(contentPanel);
        return customDialog;
    }

    // Custom dialog class to store the result
    private static class CustomDialog extends JDialog {

        private int result = JOptionPane.CANCEL_OPTION;

        private CustomDialog(java.awt.Window owner, String title, ModalityType modalityType) {
            super(owner, title, modalityType);
        }

        public int getResult() {
            return result;
        }

        public void setResult(int result) {
            this.result = result;
        }
    }

    abstract void resetForm();
}
