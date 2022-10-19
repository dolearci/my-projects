package cz.muni.fi.pv168.cashflow.ui.filters;

import cz.muni.fi.pv168.cashflow.business.model.Category;
import cz.muni.fi.pv168.cashflow.business.model.Currency;
import cz.muni.fi.pv168.cashflow.business.model.Transaction;
import cz.muni.fi.pv168.cashflow.business.model.TransactionType;
import cz.muni.fi.pv168.cashflow.ui.filters.matchers.EntityMatcher;
import cz.muni.fi.pv168.cashflow.ui.filters.matchers.EntityMatchers;
import cz.muni.fi.pv168.cashflow.ui.filters.matchers.transaction.TransactionCategoryMatcher;
import cz.muni.fi.pv168.cashflow.ui.filters.matchers.transaction.TransactionCurrencyMatcher;
import cz.muni.fi.pv168.cashflow.ui.filters.matchers.transaction.TransactionDateFromMatcher;
import cz.muni.fi.pv168.cashflow.ui.filters.matchers.transaction.TransactionDateToMatcher;
import cz.muni.fi.pv168.cashflow.ui.filters.matchers.transaction.TransactionTransactionTypeMatcher;
import cz.muni.fi.pv168.cashflow.ui.filters.values.SpecialFilterCategoryValues;
import cz.muni.fi.pv168.cashflow.ui.filters.values.SpecialFilterCurrencyValues;
import cz.muni.fi.pv168.cashflow.ui.filters.values.SpecialFilterTransactionTypeValues;
import cz.muni.fi.pv168.cashflow.ui.model.TransactionTableModel;
import cz.muni.fi.pv168.cashflow.utils.Either;

import javax.swing.table.TableRowSorter;
import java.time.LocalDate;
import java.util.stream.Stream;

/**
 * Class holding all filters for the EmployeeTable.
 */
public final class TransactionTableFilter {

    private final TransactionCompoundMatcher transactionCompoundMatcher;

    public TransactionTableFilter(TableRowSorter<TransactionTableModel> rowSorter) {
        transactionCompoundMatcher = new TransactionCompoundMatcher(rowSorter);
        rowSorter.setRowFilter(transactionCompoundMatcher);
    }

    public void filterTransactionType(Either<SpecialFilterTransactionTypeValues, TransactionType> selectedItem) {
        selectedItem.apply(
                l -> transactionCompoundMatcher.setTransactionTypeMatcher(l.getMatcher()),
                r -> transactionCompoundMatcher.setTransactionTypeMatcher(new TransactionTransactionTypeMatcher(r))
        );
    }

    public void filterCategory(Either<SpecialFilterCategoryValues, Category> selectedItem) {
        selectedItem.apply(
                l -> transactionCompoundMatcher.setCategoryMatcher(l.getMatcher()),
                r -> transactionCompoundMatcher.setCategoryMatcher(new TransactionCategoryMatcher(r))
        );
    }

    public void filterCurrency(Either<SpecialFilterCurrencyValues, Currency> selectedItem) {
        selectedItem.apply(
                l -> transactionCompoundMatcher.setCurrencyMatcher(l.getMatcher()),
                r -> transactionCompoundMatcher.setCurrencyMatcher(new TransactionCurrencyMatcher(r))
        );
    }

    public void filterDateFrom(LocalDate dateFrom) {
        transactionCompoundMatcher.setDateFromMatcher(new TransactionDateFromMatcher(dateFrom));
    }

    public void filterDateTo(LocalDate dateTo) {
        transactionCompoundMatcher.setDateToMatcher(new TransactionDateToMatcher(dateTo));
    }

    /**
     * Container class for all matchers for the EmployeeTable.
     * <p>
     * This Matcher evaluates to true, if all contained {@link EntityMatcher} instances
     * evaluate to true.
     */
    private static class TransactionCompoundMatcher extends EntityMatcher<Transaction> {

        private final TableRowSorter<TransactionTableModel> rowSorter;
        private EntityMatcher<Transaction> transactionTypeMatcher = EntityMatchers.all();
        private EntityMatcher<Transaction> categoryMatcher = EntityMatchers.all();
        private EntityMatcher<Transaction> currencyMatcher = EntityMatchers.all();
        private EntityMatcher<Transaction> dateFromMatcher = EntityMatchers.all();
        private EntityMatcher<Transaction> dateToMatcher = EntityMatchers.all();

        private TransactionCompoundMatcher(TableRowSorter<TransactionTableModel> rowSorter) {
            this.rowSorter = rowSorter;
            rowSorter.sort();
        }

        private void setTransactionTypeMatcher(EntityMatcher<Transaction> transactionTypeMatcher) {
            this.transactionTypeMatcher = transactionTypeMatcher;
            rowSorter.sort();
        }

        private void setCategoryMatcher(EntityMatcher<Transaction> categoryMatcher) {
            this.categoryMatcher = categoryMatcher;
            rowSorter.sort();
        }

        private void setCurrencyMatcher(EntityMatcher<Transaction> currencyMatcher) {
            this.currencyMatcher = currencyMatcher;
            rowSorter.sort();
        }

        private void setDateFromMatcher(EntityMatcher<Transaction> dateFromMatcher) {
            this.dateFromMatcher = dateFromMatcher;
            rowSorter.sort();
        }

        private void setDateToMatcher(EntityMatcher<Transaction> dateToMatcher) {
            this.dateToMatcher = dateToMatcher;
            rowSorter.sort();
        }

        @Override
        public boolean evaluate(Transaction transaction) {
            return Stream.of(transactionTypeMatcher, categoryMatcher, currencyMatcher, dateFromMatcher, dateToMatcher)
                    .allMatch(m -> m.evaluate(transaction));
        }
    }
}
