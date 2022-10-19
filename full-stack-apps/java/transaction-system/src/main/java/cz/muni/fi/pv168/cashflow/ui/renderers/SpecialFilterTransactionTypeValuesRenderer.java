package cz.muni.fi.pv168.cashflow.ui.renderers;

import cz.muni.fi.pv168.cashflow.ui.filters.values.SpecialFilterTransactionTypeValues;

import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Font;
import java.util.Objects;

public class SpecialFilterTransactionTypeValuesRenderer extends AbstractRenderer<SpecialFilterTransactionTypeValues> {

    public SpecialFilterTransactionTypeValuesRenderer() {
        super(SpecialFilterTransactionTypeValues.class);
    }

    private static void renderAll(JLabel label) {
        label.setText("Transaction Type filter");
        label.setFont(label.getFont().deriveFont(Font.ITALIC));
        label.setForeground(Color.GRAY);
    }

    protected void updateLabel(JLabel label, SpecialFilterTransactionTypeValues value) {
        if (Objects.requireNonNull(value) == SpecialFilterTransactionTypeValues.ALL) {
            renderAll(label);
        }
    }
}
