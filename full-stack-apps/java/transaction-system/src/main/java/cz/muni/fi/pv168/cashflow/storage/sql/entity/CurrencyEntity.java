package cz.muni.fi.pv168.cashflow.storage.sql.entity;

import java.util.Objects;

public record CurrencyEntity(
        Long id,
        String guid,
        String name,
        String code,
        Double rate) {

    public CurrencyEntity(
            Long id,
            String guid,
            String name,
            String code,
            Double rate) {
        this.id = id;
        this.guid = Objects.requireNonNull(guid, "guid must not be null");
        this.name = Objects.requireNonNull(name, "name must not be null");
        this.code = Objects.requireNonNull(code, "code must not be null");
        this.rate = Objects.requireNonNull(rate, "rate must not be null");
    }

    public CurrencyEntity(
            String guid,
            String name,
            String code,
            Double rate) {
        this(null, guid, name, code, rate);
    }
}
