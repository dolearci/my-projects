package cz.muni.fi.pv168.cashflow.business.repository;

import cz.muni.fi.pv168.cashflow.business.model.Currency;

public interface CurrencyRepository extends Repository<Currency> {

    boolean existsByName(String name);

    boolean existsByCode(String code);
}
