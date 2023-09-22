package Modelo;

import com.mysql.cj.jdbc.PreparedStatementWrapper;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
/**
 *
 * @author danic
 */
public class ProveedorUse {
    public boolean RegistroDeProveedor(ProveedorObject prov){
        Connection con = null;
        Conexion cn = new Conexion();
        PreparedStatement statement;
        String sql = "INSERT INTO musichouse.proveedores VALUES(NULL, ?, ?, ?, ?, ?, NOW())";
        
        try{
            con = cn.getConnection();
            statement = con.prepareStatement(sql);
            
            statement.setString(1, prov.getRfc());
            statement.setString(2, prov.getNombre());
            statement.setString(3, prov.getTelefono());
            statement.setString(4, prov.getDireccion());
            statement.setString(5, prov.getRazonSocial());
            statement.execute();
            return true;
        }catch (SQLException e){
            System.out.println(e.toString());
            return false;
        }finally{
            try {
                con.close();
            } catch (SQLException e) {
                System.out.println(e.toString());
            }
        }
    }
}
