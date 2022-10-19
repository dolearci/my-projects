package cz.muni.fi.pv168.cashflow.business.service.export.batch;

import cz.muni.fi.pv168.cashflow.business.model.Category;
import cz.muni.fi.pv168.cashflow.business.model.Currency;
import cz.muni.fi.pv168.cashflow.business.model.Transaction;

import java.util.Collection;

public record Batch(Collection<Transaction> transactions, Collection<Category> categories,
                    Collection<Currency> currencies) {

}
