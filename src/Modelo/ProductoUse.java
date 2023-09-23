package Modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
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
    
    public List ListarProducto(){
        List<ProductoObject> ListaProducto = new ArrayList();
        String sql = "SELECT * FROM musichouse.productos";
        try {
            con = cn.getConnection();
            statement = con.prepareStatement(sql);
            resultado = statement.executeQuery();
            while(resultado.next()){
                ProductoObject prod = new ProductoObject();
                prod.setSku(resultado.getString("sku"));
                prod.setNombre(resultado.getString("nombre"));
                prod.setProveedor(resultado.getString("proveedor"));
                prod.setStock(resultado.getInt("stock"));
                prod.setPrecio(resultado.getDouble("precio"));
                ListaProducto.add(prod);
            }
        } catch (SQLException e) {
            System.out.print(e.toString());
        }
        return ListaProducto;
    }
    
    public boolean EliminarProducto(String skuProducto){
        String sql = "DELETE FROM musichouse.productos where sku = ?";
        
        try {
            statement = con.prepareStatement(sql);
            statement.setString(1, skuProducto);
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
    
    public boolean ModificarProductos(ProductoObject prod){
        String sql = "UPDATE musichouse.productos SET sku = ?, nombre = ?, proveedor = ?, stock = ?, precio = ? WHERE sku = ?";
        try {
            statement = con.prepareStatement(sql);
            statement.setString(1, prod.getSku());
            statement.setString(2, prod.getNombre());
            statement.setString(3, prod.getProveedor());
            statement.setInt(4, prod.getStock());
            statement.setDouble(5, prod.getPrecio());
            statement.setString(6, prod.getSku());
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
    
    public ProductoObject BuscarProducto(String sku){
        ProductoObject prod = new ProductoObject();
        String sql = "SELECT * FROM musichouse.productos WHERE sku = ?";
        try {
            con = cn.getConnection();
            statement = con.prepareStatement(sql);
            statement.setString(1, sku);
            
            resultado = statement.executeQuery();
            
            if (resultado.next()) {
                prod.setNombre(resultado.getString("nombre"));
                prod.setPrecio(resultado.getDouble("precio"));
                prod.setStock(resultado.getInt("stock"));
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return prod;
    }
}
