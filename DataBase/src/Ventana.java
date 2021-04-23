import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import com.mysql.cj.protocol.Resultset;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;
import javax.swing.JTabbedPane;
import javax.swing.JPanel;
import javax.swing.JTable;

public class Ventana {

	private JFrame frame;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTable table;
	private JTextField textField;

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
		frame = new JFrame();
		frame.setResizable(false);
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBorder(null);
		tabbedPane.setBounds(0, 0, 434, 261);
		frame.getContentPane().add(tabbedPane);
		
		JPanel panel = new JPanel();
		panel.setBorder(null);
		tabbedPane.addTab("Registro", null, panel, null);
		panel.setLayout(null);
		
		JLabel lblNewLabel_3 = new JLabel("Descripcion");
		lblNewLabel_3.setBounds(293, 53, 94, 14);
		panel.add(lblNewLabel_3);
		
		textField_1 = new JTextField();
		textField_1.setBounds(101, 76, 124, 20);
		panel.add(textField_1);
		textField_1.setColumns(10);
		
		textField_2 = new JTextField();
		textField_2.setBounds(101, 128, 124, 20);
		panel.add(textField_2);
		textField_2.setColumns(10);
		
		JTextArea textArea = new JTextArea();
		textArea.setBounds(249, 78, 153, 70);
		panel.add(textArea);
		
		JLabel lblNewLabel_2 = new JLabel("Precio");
		lblNewLabel_2.setBounds(29, 131, 62, 14);
		panel.add(lblNewLabel_2);
		
		JLabel lblNewLabel_1 = new JLabel("Producto");
		lblNewLabel_1.setBounds(29, 79, 62, 14);
		panel.add(lblNewLabel_1);
		
		//********************** pestaña para agregar productos ***********************************************
		
		JButton btnNewButton = new JButton("Agregar");
		btnNewButton.setBounds(101, 175, 86, 23);
		panel.add(btnNewButton);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					
					ConeccionSql conec1 = new ConeccionSql(); // creo la coneccion de la clase 
					Connection ConeccionSql = conec1.conectar();
										
					
					String sql = "INSERT INTO productos (Nombre,Precio,Descripcion) VALUE (?,?,?)"; // codigo sql para agregar
					PreparedStatement state = ConeccionSql.prepareStatement(sql); // preparo el statement
					
					state.setString(1, textField_1.getText());
					state.setInt(2, Integer.parseInt(textField_2.getText())); //tomo los datos ingresados en cada campo
					state.setString(3, textArea.getText());
					
					state.executeUpdate(); //ejecuto el statement que hace que el codigo desde el inset hasta aca se ejecute
					
				    ConeccionSql.close();
					
					JOptionPane.showMessageDialog(null, "Producto agregado exitosamente!"); // muestro ventana si se agrego
							
					} catch (Exception e2) {
						
						JOptionPane.showMessageDialog(null, "la coneccion fallo!!! chekea que el servidor no este caido");
						e2.printStackTrace();
						
					} 
					
				}
		});
								
						
		JPanel panel_1 = new JPanel();
		tabbedPane.addTab("Productos", null, panel_1, null);
		//panel_1.setLayout(null);
		
		table = new JTable();
		table.setBounds(0, 0, 1, 1);
		panel_1.add(table);				
		
		//*********************************** pestaña para mostrar tabla de productos ****************************
		
		ConeccionSql conectar = new ConeccionSql();
		Connection ConeccionSql = conectar.conectar();//creo coneccion
		
		String sql = "SELECT * FROM productos";//para traer datos de la tabla
		
		Statement state ;
		
		DefaultTableModel model = new DefaultTableModel(); //creo tabla interna
		model.addColumn("id");
		model.addColumn("nombre");
		model.addColumn("precio");
		model.addColumn("descripcion");
		table.setModel(model);
				
		
	    String[] dato = new String[4]; //creo array para realizar la carga
	    
	    try {
	    	
			state = ConeccionSql.createStatement();
			ResultSet result = state.executeQuery(sql); //creo statement y ejecuto el select
			
			while (result.next()) {             // itero result y mientras tenga datos 
				dato[0] = result.getString(1);
				dato[1] = result.getString(2);  // voy cargando datos en sus posiciones del array
				dato[2] = result.getString(3);
				dato[3] = result.getString(4);
				model.addRow(dato);             //los cargo en el model de nuestra tabla interna para mostrarlos
				
				
			}
			
			//ConeccionSql.close();
			
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		 
	    JPanel panel_2 = new JPanel();
		tabbedPane.addTab("eliminar", null, panel_2, null);
		panel_2.setLayout(null);
		
		textField = new JTextField();
		textField.setBounds(192, 100, 152, 22);
		panel_2.add(textField);
		textField.setColumns(10);
		
		//************************** pestaña eliminar productos **************************
		
		JButton btnNewButton_1 = new JButton("eliminar producto");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					
					ConeccionSql conect = new ConeccionSql();
					 Connection ConeccionSql = conect.conectar();
					 
					
					 String sql1 = "DELETE FROM productos WHERE productos.Id = ?";
					 PreparedStatement stat = ConeccionSql.prepareStatement(sql1);
					 
					 stat.setString(1,textField.getText());
					 
					if (stat.executeUpdate() <= 0) {
						 JOptionPane.showMessageDialog(null, "no ingreso nada para la accion requerida");
					}else {
						
						JOptionPane.showMessageDialog(null, "el registro fue eliminado exitosamente");
					}
					
					  stat.executeUpdate();
					 					 
					 ConeccionSql.close();
					
				} catch (Exception e2) {
					 JOptionPane.showMessageDialog(null, "el registro fallo");

				}
			}
		});
		
		btnNewButton_1.setBounds(40, 99, 142, 23);
		panel_2.add(btnNewButton_1);
		
		
		JLabel lblNewLabel = new JLabel("ingrese id de producto");
		lblNewLabel.setBounds(208, 75, 152, 14);
		panel_2.add(lblNewLabel);
		
		
		
	}
}
