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
}
