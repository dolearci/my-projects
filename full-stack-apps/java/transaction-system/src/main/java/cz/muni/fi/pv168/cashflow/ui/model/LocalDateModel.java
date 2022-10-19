package cz.muni.fi.pv168.cashflow.ui.model;

import org.jdatepicker.AbstractDateModel;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class LocalDateModel extends AbstractDateModel<LocalDate> {

    @Override
    protected Calendar toCalendar(LocalDate from) {
        return GregorianCalendar.from(from.atStartOfDay(ZoneId.systemDefault()));
    }

    @Override
    protected LocalDate fromCalendar(Calendar from) {
        return from.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }
}
