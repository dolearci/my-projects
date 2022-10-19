package cz.muni.fi.pv168.cashflow;

import cz.muni.fi.pv168.cashflow.business.service.export.GenericExportService;
import cz.muni.fi.pv168.cashflow.business.service.export.GenericImportService;
import cz.muni.fi.pv168.cashflow.export.csv.BatchCsvExporter;
import cz.muni.fi.pv168.cashflow.export.csv.BatchCsvImporter;
import cz.muni.fi.pv168.cashflow.export.json.BatchJsonExporter;
import cz.muni.fi.pv168.cashflow.export.json.BatchJsonImporter;
import cz.muni.fi.pv168.cashflow.export.xml.BatchXmlExporter;
import cz.muni.fi.pv168.cashflow.export.xml.BatchXmlImporter;
import cz.muni.fi.pv168.cashflow.ui.ApplicationErrorHandler;
import cz.muni.fi.pv168.cashflow.ui.action.ExportAction;
import cz.muni.fi.pv168.cashflow.ui.action.ImportAction;
import cz.muni.fi.pv168.cashflow.ui.action.NuclearQuitAction;
import cz.muni.fi.pv168.cashflow.ui.action.QuitAction;
import cz.muni.fi.pv168.cashflow.ui.model.CategoryTableModel;
import cz.muni.fi.pv168.cashflow.ui.model.CurrencyTableModel;
import cz.muni.fi.pv168.cashflow.ui.model.TemplateTableModel;
import cz.muni.fi.pv168.cashflow.ui.model.TransactionTableModel;
import cz.muni.fi.pv168.cashflow.ui.tabs.CategoriesTab;
import cz.muni.fi.pv168.cashflow.ui.tabs.CurrenciesTab;
import cz.muni.fi.pv168.cashflow.ui.tabs.TemplatesTab;
import cz.muni.fi.pv168.cashflow.ui.tabs.TransactionsTab;
import cz.muni.fi.pv168.cashflow.ui.wiring.DependencyProvider;
import cz.muni.fi.pv168.cashflow.ui.wiring.ProductionDependencyProvider;

import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.JToolBar;
import javax.swing.WindowConstants;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.util.List;

public class MainWindow {

    private static final Action quitAction = new QuitAction();

    public static void main(String[] args) {

        EventQueue.invokeLater(() -> {
            try {
                createMainWindow(new ProductionDependencyProvider());
            } catch (Exception ex) {
                showInitializationFailedDialog(ex);
            }
        });

    }

    private static void createMainWindow(DependencyProvider dependencyProvider) {
        var errorHandler = new ApplicationErrorHandler();
        Thread.setDefaultUncaughtExceptionHandler(errorHandler);
        var tabFrame = new JFrame("Cash Flow Records");
        tabFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        tabFrame.setSize(1500, 800);
        tabFrame.setMinimumSize(new Dimension(1500, 800));
        tabFrame.setLocationRelativeTo(null);
        var exportService = new GenericExportService(dependencyProvider.getCurrencyCrudService(), dependencyProvider.getCategoryCrudService(), dependencyProvider.getTransactionCrudService(), List.of(new BatchCsvExporter(), new BatchJsonExporter(), new BatchXmlExporter()));
        var importService = new GenericImportService(dependencyProvider.getCurrencyCrudService(), dependencyProvider.getCategoryCrudService(), dependencyProvider.getTransactionCrudService(), dependencyProvider.getTemplateCrudService(), List.of(new BatchCsvImporter(), new BatchJsonImporter(), new BatchXmlImporter()));

        JTabbedPane tabbedPane = new JTabbedPane();

        CategoryTableModel categoryTableModel = new CategoryTableModel(dependencyProvider.getCategoryCrudService(), dependencyProvider.getTransactionCrudService(), dependencyProvider.getTemplateCrudService());
        CurrencyTableModel currencyTableModel = new CurrencyTableModel(dependencyProvider.getCurrencyCrudService(), dependencyProvider.getTransactionCrudService(), dependencyProvider.getTemplateCrudService());
        TransactionTableModel transactionTableModel = new TransactionTableModel(dependencyProvider.getTransactionCrudService());
        TemplateTableModel templateTableModel = new TemplateTableModel(dependencyProvider.getTemplateCrudService());

        TransactionsTab transactionsTab = new TransactionsTab(transactionTableModel, categoryTableModel, currencyTableModel, templateTableModel, currencyTableModel.getMainCurrency());
        CurrenciesTab currenciesTab = new CurrenciesTab(currencyTableModel, dependencyProvider.getTransactionCrudService(), dependencyProvider.getTemplateCrudService(), currencyTableModel.getMainCurrency());
        TemplatesTab templatesTab = new TemplatesTab(templateTableModel, categoryTableModel, currencyTableModel);
        CategoriesTab categoriesTab = new CategoriesTab(categoryTableModel, dependencyProvider.getTransactionCrudService(), dependencyProvider.getTemplateCrudService());

        var toolbar = new JToolBar();
        toolbar.add(quitAction);
        Action exportAction = new ExportAction(transactionsTab, exportService);
        toolbar.add(exportAction);
        Action importAction = new ImportAction(transactionsTab, importService, () -> {
            currencyTableModel.refresh();
            categoryTableModel.refresh();
            transactionTableModel.refresh();
            templateTableModel.refresh();
        });
        toolbar.add(importAction);
        tabFrame.add(toolbar, BorderLayout.BEFORE_FIRST_LINE);

        tabbedPane.addTab("Transactions", transactionsTab);
        tabbedPane.addTab("Currencies", currenciesTab);
        tabbedPane.addTab("Templates", templatesTab);
        tabbedPane.addTab("Categories", categoriesTab);

        tabFrame.add(tabbedPane);
        tabFrame.setVisible(true);
    }

    private static void showInitializationFailedDialog(Exception ex) {
        EventQueue.invokeLater(() -> {
            ex.printStackTrace();
            Object[] options = {
                    new JButton(new QuitAction()),
                    new JButton(new NuclearQuitAction())
            };
            JOptionPane.showOptionDialog(
                    null,
                    "Application initialization failed.\nWhat do you want to do?",
                    "Initialization Error",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.ERROR_MESSAGE,
                    null,
                    options,
                    options[0]
            );
        });
    }
}
