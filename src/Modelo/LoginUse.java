package Modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author danic
 */
public class LoginUse {
    Connection con;
    PreparedStatement statement;
    ResultSet result;
   
    //Se instancía la conexión
    Conexion conn = new Conexion();
    
    public LoginObject log(String correo, String password){
        LoginObject loggeo = new LoginObject();
        String consulta = "SELECT * FROM usuarios WHERE correo = ? AND password = ?";
        try {
            con = conn.getConnection();
            statement = con.prepareStatement(consulta);
            statement.setString(1, correo);
            statement.setString(2, password);
            result = statement.executeQuery();
           
            if(result.next())
            {
                loggeo.setId(result.getInt("id"));
                loggeo.setNombre(result.getString("nombre"));
                loggeo.setCorreo(result.getString("correo"));
                loggeo.setPassword(result.getString("password"));
            }
            
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return loggeo;
    }
}
