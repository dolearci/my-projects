package cz.muni.fi.pv168.cashflow.ui.renderers;

import cz.muni.fi.pv168.cashflow.business.model.Currency;

import javax.swing.JLabel;

public class CurrencyRenderer extends AbstractRenderer<Currency> {

    public CurrencyRenderer() {
        super(Currency.class);
    }

    @Override
    protected void updateLabel(JLabel label, Currency value) {
        if (value != null) {
            label.setText(value.getCode());
        }
    }
}
