package cz.muni.fi.pv168.cashflow.ui;

import cz.muni.fi.pv168.cashflow.business.error.ApplicationException;
import cz.muni.fi.pv168.cashflow.business.error.FatalError;
import cz.muni.fi.pv168.cashflow.business.service.crud.EntityAlreadyExistsException;
import cz.muni.fi.pv168.cashflow.business.service.export.DataManipulationException;
import cz.muni.fi.pv168.cashflow.business.service.validation.ValidationException;
import cz.muni.fi.pv168.cashflow.ui.action.QuitAction;
import org.tinylog.Logger;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import java.awt.EventQueue;

public class ApplicationErrorHandler implements Thread.UncaughtExceptionHandler {

    private boolean handlingException = false;

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        if (handlingException) {
            Logger.error("Recursive error handling detected. Aborting further handling.");
            return;
        }

        handlingException = true;

        try {
            Logger.error(e, "Uncaught exception in thread " + t.getName());

            if (e instanceof FatalError ex) {
                showGeneralError(ex.getUserMessage(), true);
            } else if (e instanceof ApplicationException ex) {
                showGeneralError(ex.getUserMessage(), false);
            } else if (e instanceof DataManipulationException ex) {
                showGeneralError(ex.getMessage(), false);
            } else if (e instanceof EntityAlreadyExistsException ex) {
                showGeneralError(ex.getMessage(), false);
            } else if (e instanceof ValidationException ex) {
                showGeneralError(ex.getMessage(), false);
            } else {
                showGeneralError("Oops something went wrong!", true);
            }
        } finally {
            handlingException = false;
        }
    }

    private static void showGeneralError(String message, boolean isFatal) {
        final String title = isFatal ? "Fatal Application Error" : "Application Error";
        final Object[] options = getOptionsForDialog(isFatal);

        Logger.info("Preparing to show error dialog: " + title);

        EventQueue.invokeLater(() -> {
            try {
                JOptionPane.showOptionDialog(
                        null,
                        message,
                        title,
                        JOptionPane.DEFAULT_OPTION,
                        JOptionPane.ERROR_MESSAGE,
                        null,
                        options,
                        null
                );
                Logger.info("Error dialog displayed: " + title);
            } catch (Exception ex) {
                Logger.error("Exception while showing error dialog: ", ex);
            }
        });

        Logger.info("Error dialog invocation complete: " + title);
    }

    private static Object[] getOptionsForDialog(boolean isFatal) {
        if (!isFatal) {
            return null; // use default
        }

        return new Object[]{new JButton(new QuitAction())};
    }
}
