package cz.muni.fi.pv168.cashflow.utils;

import javax.swing.JTextField;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class PlaceholderTextField extends JTextField {

    private final String placeholder;

    public PlaceholderTextField(String placeholder) {
        this.placeholder = placeholder;
        setForeground(Color.GRAY);
        setText(placeholder);
        addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (getText().equals(placeholder)) {
                    setText("");
                    setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (getText().isEmpty()) {
                    setText(placeholder);
                    setForeground(Color.GRAY);
                }
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (getText().isEmpty() && !hasFocus()) {
            int padding = (getHeight() - getFont().getSize()) / 2;
            g.setColor(getForeground());
            g.drawString(placeholder, getInsets().left, getHeight() - padding - 1);
        }
    }

    public String getTextFinal() {
        if (super.getText().equals(placeholder)) {
            return "";
        } else {
            return super.getText();
        }
    }

    public void setTextColor() {
        if (!getTextFinal().isEmpty()) {
            setForeground(Color.black);
        }
    }
}
