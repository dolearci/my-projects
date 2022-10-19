package cz.muni.fi.pv168.cashflow.ui.action;

import cz.muni.fi.pv168.cashflow.business.service.export.ImportService;
import cz.muni.fi.pv168.cashflow.ui.resources.Icons;
import cz.muni.fi.pv168.cashflow.ui.tabs.TransactionsTab;
import cz.muni.fi.pv168.cashflow.utils.Filter;

import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public final class ImportAction extends AbstractAction {

    private final TransactionsTab transactionsTab;
    private final ImportService importService;
    private final Runnable callback;
    private static final Executor executor = Executors.newCachedThreadPool();

    public ImportAction(TransactionsTab transactionsTab, ImportService importService, Runnable callback) {
        super("Import", Icons.IMPORT_ICON);
        this.transactionsTab = transactionsTab;
        this.importService = importService;
        this.callback = callback;

        putValue(SHORT_DESCRIPTION, "Imports transactions from a file");
        putValue(MNEMONIC_KEY, KeyEvent.VK_I);
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl I"));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        var fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
        importService.getFormats().forEach(f -> fileChooser.addChoosableFileFilter(new Filter(f)));

        int dialogResult = fileChooser.showOpenDialog(transactionsTab);

        if (dialogResult == JFileChooser.APPROVE_OPTION) {
            File importFile = fileChooser.getSelectedFile();
            Object[] options = {"Yes", "Delete all current data", "Cancel import"};
            int optionNum = JOptionPane.showOptionDialog(transactionsTab,
                    "Do you want to merge imported data with current data?", "Import",
                    JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
            if (optionNum == 2 || optionNum == JOptionPane.CLOSED_OPTION) {
                return;
            }
            importService.importData(importFile.getAbsolutePath(), optionNum == 1);

            callback.run();
            JOptionPane.showMessageDialog(transactionsTab, "Import was done");
        }
    }
}
