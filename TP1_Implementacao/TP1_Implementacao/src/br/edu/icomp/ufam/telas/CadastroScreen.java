package br.edu.icomp.ufam.telas;

import javax.swing.JFormattedTextField;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.swing.SwingConstants;
import javax.swing.Timer;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.Dimension;

import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.text.MaskFormatter;
import br.edu.icomp.ufam.tp1_implementacao.Cadastro;
import br.edu.icomp.ufam.utils.CustomTableCellRenderer;
import br.edu.icomp.ufam.utils.MyScrollBarUI;

import javax.swing.JButton;
import javax.swing.UIManager;
import javax.swing.JDialog;
import javax.swing.JTable;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.awt.Rectangle;
import javax.swing.JTree;
import javax.swing.JComboBox;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JRadioButton;
import br.edu.icomp.ufam.utils.CustomTableModel;

public class CadastroScreen extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField rendEdes;
	private JTextField occurrence_date;
	private JTextField category;
	private JTable table;
	private JLabel lblNewLabel_4_1;
	private DefaultTableModel tableModel;
	private JRadioButton TodayButton;
	private List<Cadastro> dadosDoBanco;
	private Cadastro registro = new Cadastro();
	private String rendimentosEdespesas;
    private String dataDoOcorrido;
    private String categoria;
    private double saldoInicial;

	private double getSaldoDoBanco() {
        double saldo = 0.0;
        double valor_no_banco = registro.saldoUsuario();
        if(valor_no_banco != 0.0) {
        	saldo = valor_no_banco;
        }
        return saldo;
    }
	
	private void atualizarTabela() {
        tableModel.setRowCount(0);

        dadosDoBanco = registro.listarRendimentos();

        for (Cadastro dado : dadosDoBanco) {
            Object[] rowData = {dado.getID(), dado.getRendimentos(), dado.getData(), dado.getCategoria()};
            tableModel.addRow(rowData);
        }
    }
	
	private void compararRegistros() {
	    int rowCount = table.getRowCount();
	    List<Cadastro> registrosModificados = new ArrayList<Cadastro>();
        for (int i = 0; i < rowCount; i++) {
            int id = (int) table.getValueAt(i, 0);
            

            Object value = table.getValueAt(i, 1);
            Double rendimentos = 0.0;
            if (value instanceof Number) {
                rendimentos = ((Number) value).doubleValue();
            } else if (value instanceof String) {
                try {
                    rendimentos = Double.parseDouble((String) value);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
            String data = (String) table.getValueAt(i, 2);
            String categoria = (String) table.getValueAt(i, 3);

            Cadastro registroOriginal = dadosDoBanco.get(i);
            Cadastro registroModificado = new Cadastro(id, rendimentos, data, categoria);

            if (!registroModificado.equals(registroOriginal)) {
                registrosModificados.add(registroModificado);
            }
        }
	    boolean sucesso = registro.atualizarRegistroNoBanco(registrosModificados);

	    if (sucesso) {
            showCustomToast("Atualizado com Sucesso!",new Color(71, 178, 75), Color.BLACK);
        } else {
        	showCustomToast("Falha na atualização!", new Color(230, 72, 72), Color.BLACK);
        }
	    
	    atualizarTabela();
	    saldoInicial = getSaldoDoBanco();
	    if(saldoInicial >= 0) {
			lblNewLabel_4_1.setForeground(new Color(0, 0, 0));			
		}else {
			lblNewLabel_4_1.setForeground(new Color(255, 51, 51));
		}
		DecimalFormat saldoFormato = new DecimalFormat("0.00");
		String saldoInicialString ="R$ " + saldoFormato.format(saldoInicial);
		System.out.println(saldoInicialString);
		lblNewLabel_4_1.setText(saldoInicialString);
	}
	
	private void atualizarTabelaFiltrada(String filtro) {
	    tableModel.setRowCount(0);

	    List<Cadastro> dadosDoBanco;

	    if ("Rendimentos".equals(filtro)) {
	        dadosDoBanco = registro.listarRendimentosPositivos();
	    } else if ("Despesas".equals(filtro)) {
	        dadosDoBanco = registro.listarRendimentosNegativos();
	    } else {
	        dadosDoBanco = registro.listarRendimentos();
	    }

	    for (Cadastro dado : dadosDoBanco) {
	        Object[] rowData = {dado.getID(), dado.getRendimentos(), dado.getData(), dado.getCategoria()};
	        tableModel.addRow(rowData);
	    }
	}

	
	private void limparCampos() {
	    rendEdes.setText("");
	    occurrence_date.setText("");
	    category.setText("");
	}
	
	private void showCustomToast(String message, Color corFundo, Color corLetra) {
        JDialog toast = new JDialog(this, Dialog.ModalityType.MODELESS);
        toast.setUndecorated(true);
        toast.setSize(200, 50);
        toast.setLocationRelativeTo(this);

        JPanel panel = new JPanel();
        panel.setBackground(corFundo);
        panel.setBorder(new LineBorder(Color.BLACK));
        toast.getContentPane().add(panel);
        panel.setLayout(null);

        JLabel lblToastMessage = new JLabel(message);
        lblToastMessage.setForeground(corLetra);
        lblToastMessage.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblToastMessage.setHorizontalAlignment(SwingConstants.CENTER);
        lblToastMessage.setBounds(0, 0, 200, 50);
        panel.add(lblToastMessage);

        Timer timer = new Timer(2000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                toast.dispose();
            }
        });
        timer.setRepeats(false);
        timer.start();

        toast.setVisible(true);
    }
	
	public CadastroScreen() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(CadastroScreen.class.getResource("/br/edu/icomp/ufam/icons/logo200.png")));
		setTitle("Programa de Gestão de Finanças pessoais\r\n");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 776, 525);
		contentPane = new JPanel() {
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
		contentPane.setBackground(new Color(221, 221, 221));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel_de_registro = new JPanel(){
		    @Override
		    protected void paintComponent(Graphics g) {
		        super.paintComponent(g);

		        Color startColor = new Color(221, 221, 221);
		        Color endColor = new Color(156, 198, 157);

		        GradientPaint gradientPaint = new GradientPaint(0, 0, startColor, 0, getHeight(), endColor);

		        Graphics2D g2d = (Graphics2D) g;
		        g2d.setPaint(gradientPaint);
		        g2d.fillRect(0, 0, getWidth(), getHeight());
		    }
		};
		panel_de_registro.setBackground(new Color(72, 121, 185));
		panel_de_registro.setBounds(10, 10, 306, 468);
		contentPane.add(panel_de_registro);
		panel_de_registro.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Novo Fluxo Financeiro ");
		lblNewLabel.setBackground(new Color(192, 192, 192));
		lblNewLabel.setForeground(new Color(0, 0, 0));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
		lblNewLabel.setBounds(0, 0, 297, 35);
		panel_de_registro.add(lblNewLabel);
		
		JSeparator separator = new JSeparator();
		separator.setForeground(new Color(0, 0, 0));
		separator.setBounds(0, 45, 306, 19);
		panel_de_registro.add(separator);
		
		JLabel lblNewLabel_1 = new JLabel("Valor do Rendimento ou Despesa:");
		lblNewLabel_1.setFont(new Font("Segoe UI", Font.BOLD, 12));
		lblNewLabel_1.setBounds(10, 68, 277, 19);
		panel_de_registro.add(lblNewLabel_1);
		
		rendEdes = new JTextField();
		rendEdes.setColumns(10);
		rendEdes.setBounds(10, 97, 277, 19);
		panel_de_registro.add(rendEdes);
		
		
		JLabel lblNewLabel_2 = new JLabel("Data do Ocorrido:");
		lblNewLabel_2.setFont(new Font("Segoe UI", Font.BOLD, 12));
		lblNewLabel_2.setBounds(10, 196, 277, 19);
		panel_de_registro.add(lblNewLabel_2);
		
		try {
	         occurrence_date = new JFormattedTextField(
	            new MaskFormatter("##/##/####")); 
	      } catch (ParseException e) {
	         e.printStackTrace();
	      }
		occurrence_date.setColumns(10);
		occurrence_date.setBounds(10, 225, 277, 19);
		panel_de_registro.add(occurrence_date);
		
		JLabel lblNewLabel_3 = new JLabel("Categoria:");
		lblNewLabel_3.setFont(new Font("Segoe UI", Font.BOLD, 12));
		lblNewLabel_3.setBounds(10, 254, 277, 19);
		panel_de_registro.add(lblNewLabel_3);
		
		category = new JTextField();
		category.setBounds(10, 283, 277, 19);
		panel_de_registro.add(category);
		category.setColumns(10);
		
		JPanel panel_de_dinheiro_em_conta = new JPanel();
		panel_de_dinheiro_em_conta.setBounds(317, 10, 435, 47);
		Border border = BorderFactory.createLineBorder(Color.BLACK, 2);
		panel_de_dinheiro_em_conta.setBorder(border);
		contentPane.add(panel_de_dinheiro_em_conta);
		panel_de_dinheiro_em_conta.setLayout(null);
		
		JLabel lblNewLabel_4 = new JLabel("Saldo disponivel");
		lblNewLabel_4.setFont(new Font("Segoe UI", Font.BOLD, 12));
		lblNewLabel_4.setBorder(null);
		lblNewLabel_4.setBackground(new Color(221, 221, 221));
		lblNewLabel_4.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_4.setBounds(0, 0, 138, 47);
		panel_de_dinheiro_em_conta.add(lblNewLabel_4);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setOrientation(SwingConstants.VERTICAL);
		separator_1.setBackground(new Color(0, 0, 0));
		separator_1.setBounds(137, 0, 16, 47);
		panel_de_dinheiro_em_conta.add(separator_1);
		
		lblNewLabel_4_1 = new JLabel();
		saldoInicial = getSaldoDoBanco();
		DecimalFormat saldoFormato = new DecimalFormat("0.00");
		String saldoInicialString ="R$ " + saldoFormato.format(saldoInicial);
		lblNewLabel_4_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_4_1.setFont(new Font("Segoe UI", Font.BOLD, 12));
		lblNewLabel_4_1.setBorder(UIManager.getBorder("Menu.border"));
		if(saldoInicial >= 0) {
			lblNewLabel_4_1.setForeground(new Color(0, 0, 0));			
		}else {
			lblNewLabel_4_1.setForeground(new Color(255, 51, 51));
		}
		lblNewLabel_4_1.setText(saldoInicialString);
		lblNewLabel_4_1.setBounds(205, 10, 138, 27);
		panel_de_dinheiro_em_conta.add(lblNewLabel_4_1);
		
		JButton SaveButton = new JButton("Salvar");
		SaveButton.setForeground(new Color(0, 0, 0));
		SaveButton.setBackground(new Color(221, 221, 221));
		Border SaveButtonBorder = BorderFactory.createLineBorder(new Color(115, 115, 113), 2);
		SaveButton.setBorder(SaveButtonBorder);
		SaveButton.setFont(new Font("Segoe UI", Font.BOLD, 12));
		SaveButton.setBounds(90, 346, 116, 21);
		SaveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                rendimentosEdespesas = rendEdes.getText();
                dataDoOcorrido = occurrence_date.getText();
                categoria = category.getText();
                
                if (rendimentosEdespesas.isEmpty() || dataDoOcorrido.isEmpty() || categoria.isEmpty()) {
                	showCustomToast("Campo(s) está vazio!", new Color(230, 72, 72), Color.BLACK);
                }else {
                	double renEdesDouble = Double.parseDouble(rendimentosEdespesas);
                    String dataFormatada = dataDoOcorrido.replace("/", "-");
                    
                    registro.setRendimentos(renEdesDouble);
                    registro.setData(dataFormatada);
                    registro.setCategoria(categoria);
                	boolean registro_salvo = registro.adicionarRegistro(registro);
	                if (registro_salvo) {
	                	showCustomToast("Registrado com sucesso!", new Color(71, 178, 75), Color.BLACK);
	                	double novoSaldo = registro.saldoUsuario();
	                	String saldoUsuario = "R$ " + String.format("%.2f", novoSaldo);
	                    if(novoSaldo > 0) {
	            			lblNewLabel_4_1.setForeground(new Color(0, 0, 0));			
	            		}else {
	            			lblNewLabel_4_1.setForeground(new Color(255, 51, 51));
	            		}
	                    lblNewLabel_4_1.setText(saldoUsuario);
	                    TodayButton.setSelected(false);
	                    atualizarTabela();
	                    limparCampos();
	                }
                }
            }
        });
		panel_de_registro.add(SaveButton);
		
		
		JButton ClearButton = new JButton("Limpar tabela");
		ClearButton.setFont(new Font("Segoe UI", Font.BOLD, 12));
		ClearButton.setBorder(UIManager.getBorder("MenuItem.border"));
		ClearButton.setBackground(new Color(221, 221, 221));
		ClearButton.setBounds(90, 379, 116, 21);
		Border ClearButtonBorder = BorderFactory.createLineBorder(new Color(115, 115, 113), 2);
		ClearButton.setBorder(ClearButtonBorder);
		ClearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(registro.limparRegistros()) {
                	showCustomToast("Registros apagados com sucesso!", new Color(71, 178, 75), Color.BLACK);
                	atualizarTabela();
                	double novoSaldo = registro.saldoUsuario();
                    DecimalFormat saldoFormato = new DecimalFormat("0.00");
            		String saldoInicialString ="R$ " + saldoFormato.format(novoSaldo);
            		lblNewLabel_4_1.setText(saldoInicialString);
                }
            }
        });
		panel_de_registro.add(ClearButton);
		
		JLabel lblNewLabel_5 = new JLabel("*O campo deve ser numérico");
		lblNewLabel_5.setForeground(new Color(128, 0, 0));
		lblNewLabel_5.setFont(new Font("Segoe UI", Font.BOLD, 10));
		lblNewLabel_5.setBounds(10, 126, 277, 13);
		panel_de_registro.add(lblNewLabel_5);
		
		JLabel lblNewLabel_5_1 = new JLabel("*Números positivos serão rendimentos");
		lblNewLabel_5_1.setHorizontalTextPosition(SwingConstants.LEFT);
		lblNewLabel_5_1.setBounds(new Rectangle(3, 0, 0, 0));
		lblNewLabel_5_1.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_5_1.setForeground(new Color(128, 0, 0));
		lblNewLabel_5_1.setFont(new Font("Segoe UI", Font.BOLD, 10));
		lblNewLabel_5_1.setBounds(10, 144, 277, 13);
		panel_de_registro.add(lblNewLabel_5_1);
		
		JLabel lblNewLabel_5_1_1 = new JLabel("*Números negativos serão despesas");
		lblNewLabel_5_1_1.setHorizontalTextPosition(SwingConstants.LEFT);
		lblNewLabel_5_1_1.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_5_1_1.setForeground(new Color(128, 0, 0));
		lblNewLabel_5_1_1.setFont(new Font("Segoe UI", Font.BOLD, 10));
		lblNewLabel_5_1_1.setBounds(new Rectangle(3, 0, 0, 0));
		lblNewLabel_5_1_1.setBounds(10, 162, 277, 13);
		panel_de_registro.add(lblNewLabel_5_1_1);
		
		TodayButton = new JRadioButton("Hoje");
		TodayButton.setFont(new Font("Segoe UI", Font.BOLD, 10));
		TodayButton.setBounds(124, 196, 55, 21);
		TodayButton.setOpaque(false);
		TodayButton.setContentAreaFilled(false);
		TodayButton.setBorderPainted(false);
		TodayButton.addActionListener(new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent e) {
		        if (TodayButton.isSelected()) {
		            Date today = new Date();
		            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		            String formattedDate = sdf.format(today);
		            occurrence_date.setText(formattedDate);
		        } else {
		            occurrence_date.setText("");
		        }
		    }
		});

		panel_de_registro.add(TodayButton);
		
		tableModel = new CustomTableModel(new Object[]{"ID", "Finanças", "Data", "Categoria"}, 0, new boolean[]{false, true, true, true});

		atualizarTabela();
		
		table = new JTable();
		table.setModel(tableModel);
		table.getColumnModel().getColumn(1).setCellRenderer(new CustomTableCellRenderer());
		table.setBounds(317, 98, 435, 380);
		Border blackBorder = BorderFactory.createLineBorder(Color.BLACK);
		table.setBorder(blackBorder);
		JScrollPane scrollPane = new JScrollPane(table);
		JScrollBar verticalScrollBar = scrollPane.getVerticalScrollBar();
		verticalScrollBar.setUI(new MyScrollBarUI());
		scrollPane.setBounds(326, 96, 426, 356);
		contentPane.add(scrollPane);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setBackground(new Color(255, 255, 255));
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"Todos", "Rendimentos", "Despesas"}));
		comboBox.setToolTipText("");
		comboBox.setBounds(662, 67, 90, 21);
		comboBox.addActionListener(new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent e) {
		        atualizarTabelaFiltrada((String) comboBox.getSelectedItem());
		    }
		});
		contentPane.add(comboBox);
		
		JLabel lblNewLabel_6 = new JLabel("Filtrar por:");
		lblNewLabel_6.setFont(new Font("Segoe UI", Font.BOLD, 10));
		lblNewLabel_6.setForeground(Color.BLACK);
		lblNewLabel_6.setBounds(606, 67, 53, 21);
		contentPane.add(lblNewLabel_6);
		
		JButton btnSalvarAlteracoes = new JButton("Salvar alterações");
		btnSalvarAlteracoes.setForeground(Color.BLACK);
		btnSalvarAlteracoes.setFont(new Font("Segoe UI", Font.BOLD, 12));
		btnSalvarAlteracoes.setBackground(new Color(221, 221, 221));
		btnSalvarAlteracoes.setBounds(485, 457, 137, 21);
		btnSalvarAlteracoes.addActionListener(new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent e) {
		        compararRegistros();
		    }
		});

		contentPane.add(btnSalvarAlteracoes);
	}
}
