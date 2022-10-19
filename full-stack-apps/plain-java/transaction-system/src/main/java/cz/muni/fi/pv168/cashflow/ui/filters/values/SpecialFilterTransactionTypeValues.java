package cz.muni.fi.pv168.cashflow.ui.filters.values;

import cz.muni.fi.pv168.cashflow.business.model.Transaction;
import cz.muni.fi.pv168.cashflow.ui.filters.matchers.EntityMatcher;
import cz.muni.fi.pv168.cashflow.ui.filters.matchers.EntityMatchers;

import java.util.Objects;

public enum SpecialFilterTransactionTypeValues {

    ALL(EntityMatchers.all());

    private final EntityMatcher<Transaction> matcher;

    SpecialFilterTransactionTypeValues(EntityMatcher<Transaction> matcher) {
        this.matcher = Objects.requireNonNull(matcher, "matcher cannot be null");
    }

    public EntityMatcher<Transaction> getMatcher() {
        return matcher;
    }
}
