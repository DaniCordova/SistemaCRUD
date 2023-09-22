package Modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JComboBox;

/**
 *
 * @author danic
 */
public class ProductoUse {
    
    Connection con;
    Conexion cn = new Conexion();
    PreparedStatement statement;
    ResultSet resultado;
    
    public boolean RegistrarProductos(ProductoObject prod){
        String sql = "INSERT INTO musichouse.productos VALUES (?, ?, ?, ?, ?, NOW())";
        try{
            con = cn.getConnection();
            statement = con.prepareStatement(sql);
            statement.setString(1, prod.getSku());
            statement.setString(2, prod.getNombre());
            statement.setString(3, prod.getProveedor());
            statement.setInt(4, prod.getStock());
            statement.setDouble(5, prod.getPrecio());
            
            statement.execute();
            return true;
        }catch(SQLException e){
            System.out.println(e.toString());
            return false;
        }
    }
    
    public void LlenarProveedorCBox(JComboBox proveedor){
        String sql = "SELECT nombre FROM musichouse.proveedores";
        try{
            con = cn.getConnection();
            statement = con.prepareStatement(sql);
            resultado = statement.executeQuery();
            
            while(resultado.next()){
                proveedor.addItem(resultado.getString("nombre"));
            }
            
        }catch (SQLException e){
            System.out.println(e.toString());
        }
    }
}
