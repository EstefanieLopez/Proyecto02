package Proyect02;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.Font;
import javax.swing.JTable;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.RandomAccessFile;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Scanner;
import java.awt.event.ActionEvent;
import javax.swing.table.DefaultTableModel;
public class GUI extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable txtTable;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI frame = new GUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public GUI() {
		ArchivoDirecto ad = new ArchivoDirecto(); 
		Scanner sc = new Scanner(System.in);
		RandomAccessFile fichero = null, entidades = null, Atributo = null;
		final String rutaBase = "C:\\Users\\usuario\\eclipse-workspace\\proyectoo\\src";
		final String rutaEntidades = "C:\\Users\\usuario\\eclipse-workspace\\proyectoo\\src\\entidades.dat";
		final int totalBytes = 83, bytesEntidad = 47, bytesAtributo = 43;
		 final String formatoFecha = "dd/MM/yyyy";
		DateFormat format = new SimpleDateFormat(formatoFecha);
		
		Atributo at = new Atributo();
		Entidad  et = new Entidad();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 680, 524);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Tabla de Datos");
		lblNewLabel.setFont(new Font("Times New Roman", Font.BOLD, 25));
		lblNewLabel.setBounds(232, 37, 170, 32);
		contentPane.add(lblNewLabel);
		
		txtTable = new JTable();
		txtTable.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Nombre", "Fecha de Nacimiento", "Carne", "Telefono", "Peso"
			}
		) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			Class[] columnTypes = new Class[] {
				String.class, Long.class, Object.class, Object.class, Object.class
			};
			@SuppressWarnings("unchecked")
			public Class<?> getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
		});
		txtTable.getColumnModel().getColumn(1).setPreferredWidth(76);
		txtTable.getColumnModel().getColumn(1).setMinWidth(18);
		txtTable.getColumnModel().getColumn(1).setMaxWidth(100);
		txtTable.setBounds(43, 173, 563, 207);
		contentPane.add(txtTable);
		
		
		JButton btnNewButton_1 = new JButton("Quitar");
		btnNewButton_1.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnNewButton_1.setBounds(142, 411, 97, 25);
		contentPane.add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("Modificar");
		btnNewButton_2.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnNewButton_2.setBounds(251, 411, 97, 25);
		contentPane.add(btnNewButton_2);
		
		JButton btnNewButton_3 = new JButton("Mostrar");
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			String temp ;
			temp = at.getNombre() + "" + at.getIndice() + " " + at.getLongitud();
			txtTable.setToolTipText(temp);
			
			}
		});
		btnNewButton_3.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnNewButton_3.setBounds(360, 411, 97, 25);
		contentPane.add(btnNewButton_3);
	}
}
