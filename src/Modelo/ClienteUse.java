package Modelo;

import com.mysql.cj.xdevapi.PreparableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.JOptionPane;
/**
 *
 * @author danic
 */
public class ClienteUse {
    Conexion cn = new Conexion();
    Connection con;
    PreparedStatement statement;
    
    public boolean RegistrarCliente(ClienteObject cl){
        String sql = "INSERT INTO musichouse.clientes VALUES (NULL, ?, ?, ?, ?, now())";
                
        try {
            con = cn.getConnection();
            statement = con.prepareStatement(sql);
            statement.setString(1, cl.getNombre());
            statement.setString(2, cl.getTelefono());
            statement.setString(3, cl.getDireccion());
            statement.setString(4, cl.getRazonS());
            statement.execute();
        } catch (SQLException e) {
            JOptionPane.showInternalMessageDialog(null, e.toString());
            return false;
        }finally{
            try {
                con.close();
            } catch (SQLException e) {
                System.out.println(e.toString());
            }
        }
     return true;
    }
}
