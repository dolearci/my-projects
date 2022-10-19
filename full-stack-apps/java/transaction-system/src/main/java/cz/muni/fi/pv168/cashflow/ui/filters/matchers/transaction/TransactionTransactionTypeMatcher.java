package cz.muni.fi.pv168.cashflow.ui.filters.matchers.transaction;

import cz.muni.fi.pv168.cashflow.business.model.Transaction;
import cz.muni.fi.pv168.cashflow.business.model.TransactionType;
import cz.muni.fi.pv168.cashflow.ui.filters.matchers.EntityMatcher;

public class TransactionTransactionTypeMatcher extends EntityMatcher<Transaction> {

    private final TransactionType transactionType;

    public TransactionTransactionTypeMatcher(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    @Override
    public boolean evaluate(Transaction transaction) {
        return transaction.getTransactionType().equals(transactionType);
    }
}
