package cep;

import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.Toolkit;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.net.URL;
import java.util.Iterator;

import Atxy2k.CustomTextField.RestrictedTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

@SuppressWarnings("serial")
public class Cep extends JFrame {

	private JPanel contentPane;
	private JTextField txtCep;
	private JTextField txtEndereco;
	private JTextField txtBairro;
	private JTextField txtCidade;
	private JComboBox cboUf;
	private JLabel lblStatus;

	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Cep frame = new Cep();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	
	public Cep() {
		setFont(new Font("Dialog", Font.PLAIN, 14));
		setBackground(SystemColor.activeCaptionText);
		setResizable(false);
		setTitle("Busca CEP");
		setIconImage(Toolkit.getDefaultToolkit().getImage(Cep.class.getResource("/img/home.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("CEP");
		lblNewLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel.setBounds(27, 38, 61, 16);
		contentPane.add(lblNewLabel);
		
		
		
		txtCep = new JTextField();
		txtCep.setBounds(93, 33, 111, 26);
		contentPane.add(txtCep);
		txtCep.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("Endereço");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_1.setBounds(27, 80, 61, 16);
		contentPane.add(lblNewLabel_1);
		
		txtEndereco = new JTextField();
		txtEndereco.setText("");
		txtEndereco.setBounds(93, 75, 259, 26);
		contentPane.add(txtEndereco);
		txtEndereco.setColumns(10);
		
		JLabel lblNewLabel_2 = new JLabel("Bairro");
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_2.setBounds(27, 120, 61, 16);
		contentPane.add(lblNewLabel_2);
		
		txtBairro = new JTextField();
		txtBairro.setText("");
		txtBairro.setBounds(93, 115, 259, 26);
		contentPane.add(txtBairro);
		txtBairro.setColumns(10);
		
		JLabel lblNewLabel_3 = new JLabel("Cidade");
		lblNewLabel_3.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_3.setBounds(27, 160, 61, 16);
		contentPane.add(lblNewLabel_3);
		
		txtCidade = new JTextField();
		txtCidade.setBounds(93, 155, 130, 26);
		contentPane.add(txtCidade);
		txtCidade.setColumns(10);
		
		JLabel lblNewLabel_4 = new JLabel("UF");
		lblNewLabel_4.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_4.setBounds(235, 160, 35, 16);
		contentPane.add(lblNewLabel_4);
		
		cboUf = new JComboBox();
		cboUf.setModel(new DefaultComboBoxModel(new String[] {"AC", "AL", "AP", "AM", "BA", "CE", "DF", "ES", "GO", "MA", "MT", "MS", "MG", "PA", "PB", "PR", "PE", "PI", "RJ", "RN", "RS", "RO", "RR", "SC", "SP", "SE", "TO"}));
		cboUf.setBounds(276, 156, 76, 27);
		contentPane.add(cboUf);
		
		JButton btnLimpar = new JButton("Limpar");
		btnLimpar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limpar();
				
			}
		});
		btnLimpar.setBounds(27, 206, 117, 29);
		contentPane.add(btnLimpar);
		
		JButton btnCep = new JButton("Buscar");
		btnCep.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (txtCep.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "Informe o cep");
					txtCep.requestFocus();
				} else {
					buscarCep();
				}
			}
		});
		btnCep.setBounds(235, 33, 117, 29);
		contentPane.add(btnCep);
		
		JButton btnSobre = new JButton("");
		btnSobre.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Sobre sobre = new Sobre();
				sobre.setVisible(true);
			}
		});
		btnSobre.setToolTipText("Sobre");
		btnSobre.setIcon(new ImageIcon(Cep.class.getResource("/img/about.png")));
		btnSobre.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnSobre.setBorder(null);
		btnSobre.setBackground(SystemColor.control);
		btnSobre.setBounds(375, 23, 48, 48);
		contentPane.add(btnSobre);
		
		RestrictedTextField validar = new RestrictedTextField (txtCep);
		
		lblStatus = new JLabel("");
		lblStatus.setBounds(210, 34, 25, 25);
		contentPane.add(lblStatus);
		validar.setOnlyNums(true);
		validar.setLimit(8);
		
	}
	
	private void buscarCep() {
		
		String logradouro = "";
		String tipoLogradouro = "";
		String resultado = null;
		String cep =txtCep.getText();
		
		try {
			URL url = new URL("http://cep.republicavirtual.com.br/web_cep.php?cep=" + cep + "&formato=xml");
			SAXReader xml = new SAXReader();
			Document documento = xml.read(url);
			Element root = documento.getRootElement();
			for (Iterator<Element> it = root.elementIterator(); it.hasNext();) {
		        Element element = it.next();
		        if (element.getQualifiedName().equals("cidade")) {
		        	txtCidade.setText(element.getText());
		        	}
		        if (element.getQualifiedName().equals("bairro")) {
		        	txtBairro.setText(element.getText());
		        	}
		        if (element.getQualifiedName().equals("uf")) {
		        	cboUf.setSelectedItem(element.getText());
		        	}
		        if (element.getQualifiedName().equals("tipo_logradouro")) {
		        	tipoLogradouro = element.getText();
		        	}
		        if (element.getQualifiedName().equals("logradouro")) {
		        	logradouro = element.getText();
			        }
		        if (element.getQualifiedName().equals("resultado")) {
		        	resultado = element.getText();
		        	if (resultado.equals("1")) {
		        		lblStatus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/check.png")));
		        		
		        	} else {
		        		JOptionPane.showMessageDialog(null,"Cep nao encontrado!");
		        	}
			        }
		        
		        
		        txtEndereco.setText(tipoLogradouro + "" + logradouro );
		        
			}
		} catch (Exception e) {
			System.out.println(e);
			
		}
		
			
		}
		
		private void limpar() {
			txtCep.setText(null);
			txtEndereco.setText(null);
			txtBairro.setText(null);
			txtCidade.setText(null);
			cboUf.setSelectedItem(null);
			txtCep.requestFocus();
			lblStatus.setIcon(null);
	}
	
}
