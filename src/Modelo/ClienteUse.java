package Modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
/**
 *
 * @author danic
 */
public class ClienteUse {
    Conexion cn = new Conexion();
    Connection con;
    PreparedStatement statement;
    ResultSet resultado;
    
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
    
    public List ListarCliente(){
        List<ClienteObject> ListaCliente = new ArrayList();
        String sql = "SELECT * FROM musichouse.clientes";
        try {
            con = cn.getConnection();
            statement = con.prepareStatement(sql);
            resultado = statement.executeQuery();
            while(resultado.next()){
                ClienteObject cl = new ClienteObject();
                cl.setId(resultado.getInt("id"));
                cl.setNombre(resultado.getString("nombre"));
                cl.setTelefono(resultado.getString("telefono"));
                cl.setDireccion(resultado.getString("direccion"));
                cl.setRazonS(resultado.getString("razonsocial"));
                cl.setFechaAlta(resultado.getString("fecha"));
                ListaCliente.add(cl);
            }
        } catch (SQLException e) {
            System.out.print(e.toString());
        }
        return ListaCliente;
    }
    
    public boolean EliminarCliente(int idCliente){
        String sql = "DELETE FROM musichouse.clientes where id = ?";
        
        try {
            statement = con.prepareStatement(sql);
            statement.setInt(1, idCliente);
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
    
    public boolean ModificarCliente(ClienteObject cl){
        String sql = "UPDATE musichouse.clientes SET nombre = ?, telefono = ?, direccion = ?, razonsocial = ? WHERE id = ?";
        try {
            statement = con.prepareStatement(sql);
            statement.setString(1, cl.getNombre());
            statement.setString(2, cl.getTelefono());
            statement.setString(3, cl.getDireccion());
            statement.setString(4, cl.getRazonS());
            statement.setInt(5, cl.getId());
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
    
    public ClienteObject BuscarClienteCobro(int id){
        ClienteObject cl = new ClienteObject();
        String sql = "SELECT * FROM musichouse.clientes WHERE id = ?";
        
        try {
            con = cn.getConnection();
            statement = con.prepareStatement(sql);
            statement.setInt(1, id);
            resultado = statement.executeQuery();
            
            if(resultado.next()){
                cl.setNombre(resultado.getString("nombre"));
                cl.setTelefono(resultado.getString("telefono"));
                cl.setDireccion(resultado.getString("direccion"));
                cl.setRazonS(resultado.getString("razonsocial"));
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return cl;
    }
}
