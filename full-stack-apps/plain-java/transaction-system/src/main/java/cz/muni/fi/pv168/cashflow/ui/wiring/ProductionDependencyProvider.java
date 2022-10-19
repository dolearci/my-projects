package cz.muni.fi.pv168.cashflow.ui.wiring;

import cz.muni.fi.pv168.cashflow.storage.sql.db.DatabaseManager;

public class ProductionDependencyProvider extends CommonDependencyProvider {

    public ProductionDependencyProvider() {
        super(createDatabaseManager());
    }

    private static DatabaseManager createDatabaseManager() {
        DatabaseManager databaseManager = DatabaseManager.createProductionInstance();
        //databaseManager.initSchema();
        //databaseManager.initData("prod");

        return databaseManager;
    }
}

