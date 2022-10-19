package cz.muni.fi.pv168.cashflow.ui.filters.matchers.transaction;

import cz.muni.fi.pv168.cashflow.business.model.Transaction;
import cz.muni.fi.pv168.cashflow.ui.filters.matchers.EntityMatcher;

import java.time.LocalDate;

public class TransactionDateToMatcher extends EntityMatcher<Transaction> {

    private final LocalDate dateTo;

    public TransactionDateToMatcher(LocalDate dateTo) {
        this.dateTo = dateTo;
    }

    @Override
    public boolean evaluate(Transaction transaction) {
        LocalDate date = transaction.getDate();
        return dateTo == null || date.isEqual(dateTo) || date.isBefore(dateTo);
    }
}
