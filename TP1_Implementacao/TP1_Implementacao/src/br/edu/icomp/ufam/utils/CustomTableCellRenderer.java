package br.edu.icomp.ufam.utils;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.Color;
import java.awt.Component;

public class CustomTableCellRenderer extends DefaultTableCellRenderer {
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        if (column == 1) {
            double valor = Double.parseDouble(value.toString());

            if (valor < 0) {
                component.setBackground(new Color(155, 43, 26));
            } else if (valor > 0) {
                component.setBackground(new Color(27, 137, 30));
            } else {
                component.setBackground(Color.WHITE);
            }
        } else {
            component.setBackground(table.getBackground());
        }

        return component;
    }
}
