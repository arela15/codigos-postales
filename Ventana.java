package window;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;

import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Ventana {

	private JFrame frame;
	private JTextField textField;
	private JTextArea textArea;
	private JButton btnBuscar;
	private JScrollPane scroll;
	private JButton btnBorrar;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Ventana window = new Ventana();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Ventana() {
		initialize();
	}
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame("Buscador de CP");
		frame.setResizable(false);
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblCdigoPostal = new JLabel("C\u00F3digo Postal");
		lblCdigoPostal.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		lblCdigoPostal.setHorizontalAlignment(SwingConstants.LEFT);
		lblCdigoPostal.setBounds(10, 11, 84, 20);
		frame.getContentPane().add(lblCdigoPostal);
		
		textField = new JTextField();
		textField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				if (arg0.getKeyCode() == KeyEvent.VK_ENTER){
					Buscar();
				}
			}
		});
		textField.setToolTipText("Ingresa el c\u00F3digo postal que quieres buscar");
		textField.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		textField.setBounds(104, 11, 86, 20);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		textArea = new JTextArea();
		textArea.setEditable(false);
		textArea.setBounds(20, 43, 414, 208);
		frame.getContentPane().add(textArea);
		
		scroll = new JScrollPane(textArea);
	    scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
	    scroll.setBounds(20, 42, 400, 208);
	    frame.getContentPane().add(scroll);
		
		btnBuscar = new JButton("Buscar");
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Buscar();
			}
		});
		btnBuscar.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		btnBuscar.setBounds(200, 10, 89, 23);
		frame.getContentPane().add(btnBuscar);
		
		btnBorrar = new JButton("Borrar");
		btnBorrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				textArea.setText(null);
			}
		});
		btnBorrar.setBounds(331, 10, 89, 23);
		frame.getContentPane().add(btnBorrar);
	}
	/**
	 * Lee el archivo en busca del codigo postal
	 * @throws IOException 
	 */
	private void lee_archivo(String cp) throws IOException, Exception{
		String linea, lugar="", tempcp;
		
		BufferedReader archivo = new BufferedReader(new FileReader("cp.txt"));
		
		while((linea = archivo.readLine()) != null)
		{
			tempcp = linea.split("\\|")[0];
			if (cp.equals(tempcp)){
				lugar = linea.split("\\|")[1] + ", " + linea.split("\\|")[3] + ", " + linea.split("\\|")[4];
				break;
			}
		    
		}
		archivo.close();
		if (lugar.isEmpty()){
			throw new Exception("No se encontró el código postal");
		}
		else
			textArea.append(cp + ": " + lugar + "\n");
		
	}
	/**
	 * Funcion que se hace cargo del trabajo desde que el boton es presionado
	 */
	private void Buscar(){
		String texto = textField.getText();
		try {
			lee_archivo(texto);
			
		}catch (IOException e) {
			JOptionPane.showMessageDialog(null, "No se pudo abrir la base de datos, asegurate que cp.txt este en el mismo directorio que yo", "Error :(", JOptionPane.ERROR_MESSAGE);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "No encontrado", JOptionPane.INFORMATION_MESSAGE);
		}
		finally {
			textField.setText(null);
		}
	}
}
