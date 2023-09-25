package Modelo;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.filechooser.FileSystemView;

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
            statement.setInt(3, v.getVendedor());
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
    
    public boolean ActualizarStock(int cantidad, String sku){
        String sql = "UPDATE musichouse.productos SET stock = ? WHERE sku = ?";
        try {
            con = cn.getConnection();
            statement = con.prepareStatement(sql);
            statement.setInt(1, cantidad);
            statement.setString(2, sku);
            statement.execute();
            return true;
        } catch (SQLException e) {
            System.out.println(e.toString());
            return false;
        }
    }
    
    public VentaObject BuscarVenta(int id){
        VentaObject cl = new VentaObject();
        String sql = "SELECT * FROM ventas WHERE id = ?";
        try {
            con = cn.getConnection();
            statement = con.prepareStatement(sql);
            statement.setInt(1, id);
            rs = statement.executeQuery();
            if (rs.next()) {
                cl.setId(rs.getInt("id"));
                cl.setIdCliente(rs.getInt("idcliente"));
                cl.setCliente(rs.getString("nombre"));
                cl.setVendedor(rs.getInt("vendedor"));
                cl.setTotal(rs.getDouble("total"));
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return cl;
    }

    public List ListarVenta(){
        List<VentaObject> ListaVenta = new ArrayList();
        String sql = "SELECT * FROM musichouse.ventas";
        try {
            con = cn.getConnection();
            statement = con.prepareStatement(sql);
            rs = statement.executeQuery();
            while(rs.next()){
                VentaObject venta = new VentaObject();
                venta.setId(rs.getInt("id"));
                venta.setIdCliente(rs.getInt("idCliente"));
                venta.setCliente(rs.getString("nombreCliente"));
                venta.setVendedor(rs.getInt("vendedor"));
                venta.setTotal(rs.getDouble("totalventa"));
                ListaVenta.add(venta);
            }
        } catch (SQLException e) {
            System.out.print(e.toString());
        }
        return ListaVenta;
    }    
}
