package cz.muni.fi.pv168.cashflow.ui.filters.matchers.transaction;

import cz.muni.fi.pv168.cashflow.business.model.Currency;
import cz.muni.fi.pv168.cashflow.business.model.Transaction;
import cz.muni.fi.pv168.cashflow.ui.filters.matchers.EntityMatcher;

public class TransactionCurrencyMatcher extends EntityMatcher<Transaction> {

    private final Currency currency;

    public TransactionCurrencyMatcher(Currency currency) {
        this.currency = currency;
    }

    @Override
    public boolean evaluate(Transaction transaction) {
        return transaction.getCurrency().equals(currency);
    }
}
