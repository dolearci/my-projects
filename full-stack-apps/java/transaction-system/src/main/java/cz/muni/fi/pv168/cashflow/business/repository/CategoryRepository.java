package cz.muni.fi.pv168.cashflow.business.repository;

import cz.muni.fi.pv168.cashflow.business.model.Category;

public interface CategoryRepository extends Repository<Category> {

    boolean existsByName(String name);
}
