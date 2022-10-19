package cz.muni.fi.pv168.cashflow.ui.filters.matchers.transaction;

import cz.muni.fi.pv168.cashflow.business.model.Transaction;
import cz.muni.fi.pv168.cashflow.ui.filters.matchers.EntityMatcher;

import java.time.LocalDate;

public class TransactionDateFromMatcher extends EntityMatcher<Transaction> {

    private final LocalDate dateFrom;

    public TransactionDateFromMatcher(LocalDate dateFrom) {
        this.dateFrom = dateFrom;
    }

    @Override
    public boolean evaluate(Transaction transaction) {
        LocalDate date = transaction.getDate();
        return dateFrom == null || date.isEqual(dateFrom) || date.isAfter(dateFrom);
    }
}
