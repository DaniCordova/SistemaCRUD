package Modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author danic
 */
public class VentaUse {
    Conexion cn = new Conexion();
    Connection con;
    PreparedStatement statement;
    int resultado;
    ResultSet rs;
    
    public int RegistrarVenta(VentaObject v){ 
        String sql = "INSERT INTO musichouse.ventas VALUES (NULL, ?, ?, ?, ?, NOW())";
        try{
            con = cn.getConnection();
            statement = con.prepareStatement(sql);
            statement.setInt(1, v.getIdCliente());
            statement.setString(2, v.getCliente());
            statement.setString(3, v.getVendedor());
            statement.setDouble(4, v.getTotal());
            statement.execute();
        }catch(SQLException e){
            System.out.println(e.toString());
        }finally{
            try {
                con.close();
            } catch (SQLException e) {
                System.out.println(e.toString());
            }
        }
        return resultado;
    }
    
    public int RegistrarDetalleVenta(DetalleVentaObject dv){
        String sql = "INSERT INTO musichouse.detalleventa VALUES (NULL, ?, ?, ?, ?)";
        try {
            con = cn.getConnection();
            statement = con.prepareStatement(sql);
            statement.setString(1, dv.getSku());
            statement.setInt(2, dv.getCantidad());
            statement.setDouble(3, dv.getPrecio());
            statement.setInt(4, dv.getIdVenta());
            statement.execute();
        } catch (SQLException e) {
            System.out.println(e.toString());
        }finally{
            try {
                con.close();
            } catch (SQLException e) {
                System.out.println(e.toString());
            }
        }
        return resultado;
    }
    
    public int IdVenta(){
        int id = 0;
        String sql = "SELECT MAX(id) FROM musichouse.ventas";
        
        try {
            con = cn.getConnection();
            statement = con.prepareStatement(sql);
            rs = statement.executeQuery();
            if(rs.next()){
                id = rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return id;
    }
}
