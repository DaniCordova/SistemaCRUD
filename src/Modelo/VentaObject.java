package Modelo;

/**
 *
 * @author danic
 */
public class VentaObject {
    private int id;
    private int idCliente;
    private String cliente;
    private int vendedor;
    private double total;

    public VentaObject() {
    }

    public VentaObject(int id, int idCliente, String cliente, int vendedor, double total) {
        this.id = id;
        this.idCliente = idCliente;
        this.cliente = cliente;
        this.vendedor = vendedor;
        this.total = total;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public int getVendedor() {
        return vendedor;
    }

    public void setVendedor(int vendedor) {
        this.vendedor = vendedor;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
    
    
}
