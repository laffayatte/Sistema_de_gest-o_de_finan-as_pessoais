package br.edu.icomp.ufam.utils;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.plaf.basic.BasicScrollBarUI;

public class MyScrollBarUI extends BasicScrollBarUI {
    @Override
    protected void configureScrollBarColors() {
        trackColor = new Color(240, 240, 240); // Cor do fundo da barra de rolagem
        thumbColor = new Color(120, 120, 120); // Cor do botão de arrasto da barra de rolagem
        thumbDarkShadowColor = new Color(80, 80, 80); // Cor da sombra escura do botão de arrasto
        thumbHighlightColor = new Color(160, 160, 160); // Cor do realce do botão de arrasto
    }

    @Override
    protected JButton createDecreaseButton(int orientation) {
        return createZeroButton();
    }

    @Override
    protected JButton createIncreaseButton(int orientation) {
        return createZeroButton();
    }

    private JButton createZeroButton() {
        JButton button = new JButton();
        Dimension zeroDim = new Dimension(0, 0);
        button.setPreferredSize(zeroDim);
        button.setMinimumSize(zeroDim);
        button.setMaximumSize(zeroDim);
        return button;
    }
}
