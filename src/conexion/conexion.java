/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package conexion;

import com.mysql.jdbc.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Bmarc
 */
public class conexion {
    private static Connection conect=null;
    public Connection conectar(){
        try {//porque en tycatch porque es conexion y me lanse error si no conecta
            Class.forName("com.mysql.jdbc.Driver");
            conect = (Connection)DriverManager.getConnection("jdbc:mysql://localhost/viajes","root","");
            
            //JOptionPane.showMessageDialog(null, "Conectado");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, 
                    "Sistema fuera de linea");
        }
        return conect;
    }
}
