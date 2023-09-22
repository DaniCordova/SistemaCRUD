package Modelo;

/**
 *
 * @author danic
 */
public class ProductoObject {
    private String sku;
    private String nombre;
    private String proveedor;
    private int stock;
    private double precio;
    private String fechaAlta;

    public ProductoObject() {
    }

    public ProductoObject(String sku, String nombre, String proveedor, int stock, double precio, String fechaAlta) {
        this.sku = sku;
        this.nombre = nombre;
        this.proveedor = proveedor;
        this.stock = stock;
        this.precio = precio;
        this.fechaAlta = fechaAlta;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getProveedor() {
        return proveedor;
    }

    public void setProveedor(String proveedor) {
        this.proveedor = proveedor;
    }
    
    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getFechaAlta() {
        return fechaAlta;
    }

    public void setFechaAlta(String fechaAlta) {
        this.fechaAlta = fechaAlta;
    }
    
    
}
