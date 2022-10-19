package cz.muni.fi.pv168.cashflow.ui.renderers;

import cz.muni.fi.pv168.cashflow.ui.filters.values.SpecialFilterCategoryValues;

import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Font;
import java.util.Objects;

public class SpecialFilterCategoryValuesRenderer extends AbstractRenderer<SpecialFilterCategoryValues> {

    public SpecialFilterCategoryValuesRenderer() {
        super(SpecialFilterCategoryValues.class);
    }

    private static void renderAll(JLabel label) {
        label.setText("Category filter");
        label.setFont(label.getFont().deriveFont(Font.ITALIC));
        label.setForeground(Color.GRAY);
    }

    protected void updateLabel(JLabel label, SpecialFilterCategoryValues value) {
        if (Objects.requireNonNull(value) == SpecialFilterCategoryValues.ALL) {
            renderAll(label);
        }
    }
}
