package cz.muni.fi.pv168.cashflow.ui.renderers;

import javax.swing.JLabel;

public class BasicCellRenderer extends AbstractRenderer<Object> {

    public BasicCellRenderer() {
        super(Object.class);
    }

    @Override
    protected void updateLabel(JLabel label, Object value) {
        label.setText(value.toString());
    }
}
