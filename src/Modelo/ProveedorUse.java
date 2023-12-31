package Modelo;

import com.mysql.cj.jdbc.PreparedStatementWrapper;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author danic
 */
public class ProveedorUse {
    
    Conexion cn = new Conexion();
    Connection con;
    PreparedStatement statement;
    ResultSet resultado;
    
    public boolean RegistroDeProveedor(ProveedorObject prov){
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
    
    public List ListarProveedor(){
        List<ProveedorObject> ListaProveedor = new ArrayList();
        String sql = "SELECT * FROM musichouse.proveedores";
        try {
            con = cn.getConnection();
            statement = con.prepareStatement(sql);
            resultado = statement.executeQuery();
            while(resultado.next()){
                ProveedorObject pr = new ProveedorObject();
                pr.setId(resultado.getInt("id"));
                pr.setRfc(resultado.getString("rfc"));
                pr.setNombre(resultado.getString("nombre"));
                pr.setTelefono(resultado.getString("telefono"));
                pr.setDireccion(resultado.getString("direccion"));
                pr.setRazonSocial(resultado.getString("razonsocial"));
                pr.setFechaAlta(resultado.getString("fecha"));
                ListaProveedor.add(pr);
            }
        } catch (SQLException e) {
            System.out.print(e.toString());
        }
        return ListaProveedor;
    }
    
    public boolean EliminarProveedor(int idProveedor){
        String sql = "DELETE FROM musichouse.proveedores where id = ?";
        
        try {
            statement = con.prepareStatement(sql);
            statement.setInt(1, idProveedor);
            statement.execute();
            return true;
        } catch (SQLException e) {
            System.out.println(e.toString());
            return false;
        }finally{
            try {
                con.close();
            } catch (Exception e2) {
                System.out.println(e2.toString());
            }
        }
    }
    
    public boolean ModificarProveedor(ProveedorObject pr){
        String sql = "UPDATE musichouse.proveedores SET rfc = ?, nombre = ?, telefono = ?, direccion = ?, razonsocial = ? WHERE id = ?";
        try {
            statement = con.prepareStatement(sql);
            statement.setString(1, pr.getRfc());
            statement.setString(2, pr.getNombre());
            statement.setString(3, pr.getTelefono());
            statement.setString(4, pr.getDireccion());
            statement.setString(5, pr.getRazonSocial());
            statement.setInt(6, pr.getId());
            statement.execute();
            return true;
        } catch (SQLException e) {
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
