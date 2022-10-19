package cz.muni.fi.pv168.cashflow.storage.sql.entity.mapper;

import cz.muni.fi.pv168.cashflow.business.model.Currency;
import cz.muni.fi.pv168.cashflow.storage.sql.entity.CurrencyEntity;

public class CurrencyMapper implements EntityMapper<CurrencyEntity, Currency> {

    @Override
    public Currency mapToBusiness(CurrencyEntity entity) {
        return new Currency(
                entity.guid(),
                entity.name(),
                entity.code(),
                entity.rate()
        );
    }

    @Override
    public CurrencyEntity mapNewEntityToDatabase(Currency entity) {
        return new CurrencyEntity(
                entity.getGuid(),
                entity.getName(),
                entity.getCode(),
                entity.getRate()
        );
    }

    @Override
    public CurrencyEntity mapExistingEntityToDatabase(Currency entity, Long dbId) {
        return new CurrencyEntity(
                dbId,
                entity.getGuid(),
                entity.getName(),
                entity.getCode(),
                entity.getRate()
        );
    }
}
