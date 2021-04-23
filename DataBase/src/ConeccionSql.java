import java.sql.Connection;
import java.sql.DriverManager;

import javax.swing.JOptionPane;

public class ConeccionSql {
	
	Connection conec;
	
	public Connection conectar() {
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");    // creo la conneccion a la base en estta linea y la de abajo
		    conec = DriverManager.getConnection("jdbc:mysql://localhost:3306/almacen","root","");
			
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "la coneccion fallo! revisar que el servidor no este caido");
			e.printStackTrace();
		}
		return conec;
	}

}
