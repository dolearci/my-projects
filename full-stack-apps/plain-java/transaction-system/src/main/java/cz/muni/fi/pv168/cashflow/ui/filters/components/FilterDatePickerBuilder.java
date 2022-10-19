package cz.muni.fi.pv168.cashflow.ui.filters.components;

import cz.muni.fi.pv168.cashflow.ui.model.LocalDateModel;
import org.jdatepicker.DateModel;
import org.jdatepicker.JDatePicker;

import javax.swing.JButton;
import java.time.LocalDate;
import java.util.function.Consumer;

public class FilterDatePickerBuilder {

    private LocalDate initialDate;
    private DateModel<LocalDate> dateModel;
    private Consumer<LocalDate> filter;
    private JButton resetButton;

    public static FilterDatePickerBuilder create() {
        return new FilterDatePickerBuilder();
    }

    public JDatePicker build(LocalDateModel dateModel) {
        if (this.dateModel == null) {
            this.initialDate = dateModel.getValue();
        }

        this.dateModel = dateModel;
        var datePicker = new JDatePicker(dateModel);
        resetButton.addActionListener(e -> {
            this.dateModel.setValue(this.initialDate);
            filter.accept(this.dateModel.getValue());
        });
        datePicker.addActionListener(e -> filter.accept(this.dateModel.getValue()));
        return datePicker;
    }

    public FilterDatePickerBuilder setFilter(Consumer<LocalDate> filter) {
        this.filter = filter;
        return this;
    }

    public FilterDatePickerBuilder setResetButton(JButton resetButton) {
        this.resetButton = resetButton;
        return this;
    }
}
