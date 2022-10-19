package cz.muni.fi.pv168.cashflow.ui.action;

import cz.muni.fi.pv168.cashflow.business.service.export.ExportService;
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
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public final class ExportAction extends AbstractAction {

    private final TransactionsTab transactionsTab;
    private final ExportService exportService;
    private static final Executor executor = Executors.newCachedThreadPool();

    public ExportAction(TransactionsTab transactionsTab, ExportService exportService) {
        super("Export", Icons.EXPORT_ICON);
        this.transactionsTab = transactionsTab;
        this.exportService = exportService;

        putValue(SHORT_DESCRIPTION, "Exports transactions to a file");
        putValue(MNEMONIC_KEY, KeyEvent.VK_X);
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl X"));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        var fileChooser = new javax.swing.JFileChooser();
        fileChooser.setCurrentDirectory(new java.io.File(System.getProperty("user.home")));
        exportService.getFormats().forEach(f -> fileChooser.addChoosableFileFilter(new Filter(f)));

        int dialogResult = fileChooser.showSaveDialog(transactionsTab);

        if (dialogResult == JFileChooser.APPROVE_OPTION) {
            String exportCreateFile = fileChooser.getSelectedFile().getAbsolutePath();
            var filter = fileChooser.getFileFilter();
            if (filter instanceof Filter) {
                exportCreateFile = ((Filter) filter).decorate(exportCreateFile);
            }

            final String exportFile = exportCreateFile;

            CompletableFuture.runAsync(() -> {
                try {
                    exportService.exportData(exportFile);
                    SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(transactionsTab, "Export has successfully finished."));
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }, executor);
        }
    }
}
