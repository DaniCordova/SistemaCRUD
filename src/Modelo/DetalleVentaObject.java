package Modelo;

/**
 *
 * @author danic
 */
public class DetalleVentaObject {
    private int id;
    private String sku;
    private int cantidad;
    private double precio;
    private  int idVenta;

    public DetalleVentaObject() {
    }

    public DetalleVentaObject(int id, String sku, int cantidad, double precio, int idVenta) {
        this.id = id;
        this.sku = sku;
        this.cantidad = cantidad;
        this.precio = precio;
        this.idVenta = idVenta;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getIdVenta() {
        return idVenta;
    }

    public void setIdVenta(int idVenta) {
        this.idVenta = idVenta;
    }
    
    
}
