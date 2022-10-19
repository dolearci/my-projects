package cz.muni.fi.pv168.cashflow.ui.renderers;

import cz.muni.fi.pv168.cashflow.business.model.TransactionType;

import javax.swing.JLabel;

public final class TransactionTypeRenderer extends AbstractRenderer<TransactionType> {

    public TransactionTypeRenderer() {
        super(TransactionType.class);
    }

    @Override
    protected void updateLabel(JLabel label, TransactionType transactionType) {
        if (transactionType != null) {
            label.setText(transactionType.toString());
        }
    }
}
