package Modelo;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
/**
 *
 * @author danic
 */

public class Conexion {
    Connection con;
    
    public Connection getConnection(){
        try {
            String bd = "jdbc:mysql://localhost:3306/musichouse?serverTimezone=UTC";
            con = DriverManager.getConnection(bd, "root", "");
            
            return con;
        } catch (SQLException e) {
            System.out.println(e.toString());
        }    
        
            return null;
    }
    
}
