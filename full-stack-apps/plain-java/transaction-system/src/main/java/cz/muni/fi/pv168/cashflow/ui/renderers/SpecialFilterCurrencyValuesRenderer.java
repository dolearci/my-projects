package cz.muni.fi.pv168.cashflow.ui.renderers;

import cz.muni.fi.pv168.cashflow.ui.filters.values.SpecialFilterCurrencyValues;

import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Font;
import java.util.Objects;

public class SpecialFilterCurrencyValuesRenderer extends AbstractRenderer<SpecialFilterCurrencyValues> {

    public SpecialFilterCurrencyValuesRenderer() {
        super(SpecialFilterCurrencyValues.class);
    }

    private static void renderAll(JLabel label) {
        label.setText("Currency filter");
        label.setFont(label.getFont().deriveFont(Font.ITALIC));
        label.setForeground(Color.GRAY);
    }

    protected void updateLabel(JLabel label, SpecialFilterCurrencyValues value) {
        if (Objects.requireNonNull(value) == SpecialFilterCurrencyValues.ALL) {
            renderAll(label);
        }
    }
}
