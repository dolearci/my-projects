package cz.muni.fi.pv168.cashflow.ui.renderers;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.Color;
import java.awt.Component;

public class AlternatingRowColorRenderer extends DefaultTableCellRenderer {

    private final Color oddRowColor = Color.white;
    private final Color evenRowColor = new Color(197, 227, 236);

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        if (!isSelected) {
            if (row % 2 == 0) {
                c.setBackground(evenRowColor);
            } else {
                c.setBackground(oddRowColor);
            }
        }

        return c;
    }
}
