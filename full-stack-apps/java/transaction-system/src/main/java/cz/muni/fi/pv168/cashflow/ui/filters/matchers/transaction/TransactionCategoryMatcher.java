package cz.muni.fi.pv168.cashflow.ui.filters.matchers.transaction;

import cz.muni.fi.pv168.cashflow.business.model.Category;
import cz.muni.fi.pv168.cashflow.business.model.Transaction;
import cz.muni.fi.pv168.cashflow.ui.filters.matchers.EntityMatcher;

public class TransactionCategoryMatcher extends EntityMatcher<Transaction> {

    private final Category category;

    public TransactionCategoryMatcher(Category category) {
        this.category = category;
    }

    @Override
    public boolean evaluate(Transaction transaction) {
        return transaction.getCategory().equals(category);
    }
}
