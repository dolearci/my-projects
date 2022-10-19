package cz.muni.fi.pv168.cashflow.storage.sql.entity;

import java.util.Objects;

public record CategoryEntity(
        Long id,
        String guid,
        String category) {

    public CategoryEntity(
            Long id,
            String guid,
            String category) {
        this.id = id;
        this.guid = Objects.requireNonNull(guid, "guid must not be null");
        this.category = Objects.requireNonNull(category, "category must not be null");
    }

    public CategoryEntity(
            String guid,
            String category) {
        this(null, guid, category);
    }
}
