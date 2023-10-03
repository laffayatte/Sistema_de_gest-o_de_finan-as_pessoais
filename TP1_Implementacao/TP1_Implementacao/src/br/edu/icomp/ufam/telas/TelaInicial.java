package br.edu.icomp.ufam.telas;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TelaInicial extends JFrame {

    private static final long serialVersionUID = 1L;
    private JProgressBar progressBar;
    private int animationDuration = 2000;
    private int animationInterval = 100;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    TelaInicial frame = new TelaInicial();
                    frame.setVisible(true);
                    frame.setLocationRelativeTo(null);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public TelaInicial() {
        setIconImage(Toolkit.getDefaultToolkit().getImage(TelaInicial.class.getResource("/br/edu/icomp/ufam/icons/logo200.png")));
        setFont(new Font("Segoe UI", Font.BOLD, 12));
        setTitle("Programa de Gestão de Finanças pessoais");
        setForeground(new Color(0, 0, 0));
        setBackground(new Color(240, 240, 240));
        setBounds(100, 100, 864, 464);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(null);

        JPanel background = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Color startColor = new Color(79, 88, 209);
                Color endColor = new Color(46, 149, 56);
                GradientPaint gradientPaint = new GradientPaint(0, 0, startColor, 0, getHeight(), endColor);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setPaint(gradientPaint);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        background.setBounds(0, 0, 853, 428);
        getContentPane().add(background);
        background.setLayout(null);

        progressBar = new JProgressBar();
        progressBar.setBounds(10, 370, 833, 11);
        progressBar.setMaximum(1000);
        progressBar.setForeground(new Color(209, 228, 114));
        background.add(progressBar);

        Timer timer = new Timer(animationInterval, new ActionListener() {
            private int elapsedTime = 0;

            @Override
            public void actionPerformed(ActionEvent e) {
                if (elapsedTime <= animationDuration) {
                    int progressValue = (int) ((double) elapsedTime / animationDuration * 1000);
                    progressBar.setValue(progressValue);
                    elapsedTime += animationInterval;
                } else {
                    ((Timer) e.getSource()).stop();
                    if (progressBar.getValue() == progressBar.getMaximum()) {
                    	CadastroScreen telaCadastro = new CadastroScreen();
                        dispose();
                        telaCadastro.setVisible(true);
                        telaCadastro.setLocationRelativeTo(null);
                    }
                }
            }
        });

        timer.start();

        JLabel welcomeLabel = new JLabel("Bem vindo ao seu programa de Gestão Financeira!");
        welcomeLabel.setFont(new Font("Yu Mincho", Font.BOLD, 20));
        welcomeLabel.setForeground(new Color(0, 0, 0));
        welcomeLabel.setBounds(181, 264, 477, 41);
        background.add(welcomeLabel);

        JLabel byLabel = new JLabel("Desenvolvido por João Marques - 21952894");
        byLabel.setFont(new Font("Yu Mincho", Font.BOLD, 12));
        byLabel.setForeground(new Color(0, 0, 0));
        byLabel.setBounds(286, 303, 269, 20);
        background.add(byLabel);
        
        JLabel lblNewLabel = new JLabel();
        lblNewLabel.setIcon(new ImageIcon(TelaInicial.class.getResource("/br/edu/icomp/ufam/icons/logo200.png")));
        lblNewLabel.setBounds(315, 26, 200, 202);
        background.add(lblNewLabel);
    }
}

