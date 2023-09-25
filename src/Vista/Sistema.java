/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Vista;

import Modelo.ClienteObject;
import Modelo.ClienteUse;
import Modelo.DetalleVentaObject;
import Modelo.ProductoObject;
import Modelo.ProductoUse;
import Modelo.ProveedorObject;
import Modelo.ProveedorUse;
import Modelo.VentaObject;
import Modelo.VentaUse;
import Reportes.ReporteSKU;
import Reportes.ReporteVentas;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

/**
 *
 * @author danic
 */
public class Sistema extends javax.swing.JFrame {

    ClienteObject cl = new ClienteObject();
    ProveedorObject pr = new ProveedorObject();
    ProductoObject prod = new ProductoObject();
    VentaObject venta = new VentaObject();
    DetalleVentaObject detalleVenta = new DetalleVentaObject();
    
    ClienteUse usaCliente = new ClienteUse();
    ProveedorUse usaProveedor = new ProveedorUse();
    ProductoUse usaProducto = new ProductoUse();
    VentaUse usaVenta = new VentaUse();
    
    
    DefaultTableModel tablaModelo = new DefaultTableModel();
    DefaultTableModel tablaTmp = new DefaultTableModel();
    
    
    int item;
    double total = 0.00;
    
    public Sistema() {
        initComponents();
        this.setLocationRelativeTo(null);
        AutoCompleteDecorator.decorate(cbxProveedorProducto);
        usaProducto.LlenarProveedorCBox(cbxProveedorProducto);
    }
    
    public void ListadoDeClientes(){
        List<ClienteObject> ListarCl = usaCliente.ListarCliente();
        tablaModelo = (DefaultTableModel) tableCliente.getModel();
        LimpiarTabla();
        Object[] obj = new Object[6];
        
        for(int i = 0; i < ListarCl.size(); i++){
            obj[0] = ListarCl.get(i).getId();
            obj[1] = ListarCl.get(i).getNombre();
            obj[2] = ListarCl.get(i).getTelefono();
            obj[3] = ListarCl.get(i).getDireccion();
            obj[4] = ListarCl.get(i).getRazonS();
            obj[5] = ListarCl.get(i).getFechaAlta();
            
            tablaModelo.addRow(obj);
        }
        tableCliente.setModel(tablaModelo);
    }
    
    public void ListadoDeProveedores(){
        List<ProveedorObject> ListarPr = usaProveedor.ListarProveedor();
        tablaModelo = (DefaultTableModel) tableProveedor.getModel();
        Object[] obj = new Object[7];
        
        for(int i = 0; i < ListarPr.size(); i++){
            obj[0] = ListarPr.get(i).getId();
            obj[1] = ListarPr.get(i).getRfc();
            obj[2] = ListarPr.get(i).getNombre();
            obj[3] = ListarPr.get(i).getTelefono();
            obj[4] = ListarPr.get(i).getDireccion();
            obj[5] = ListarPr.get(i).getRazonSocial();
            obj[6] = ListarPr.get(i).getFechaAlta();
            
            tablaModelo.addRow(obj);
        }
        tableProveedor.setModel(tablaModelo);
    }
    
    public void ListadoDeProductos(){
        List<ProductoObject> ListarProd = usaProducto.ListarProducto();
        tablaModelo = (DefaultTableModel) tableProducto.getModel();
        Object[] obj = new Object[5];
        
        for(int i = 0; i < ListarProd.size(); i++){
            obj[0] = ListarProd.get(i).getSku();
            obj[1] = ListarProd.get(i).getNombre();
            obj[2] = ListarProd.get(i).getProveedor();
            obj[3] = ListarProd.get(i).getStock();
            obj[4] = ListarProd.get(i).getPrecio();
            
            tablaModelo.addRow(obj);
        }
        tableProducto.setModel(tablaModelo);
    }
    
    public void ListadoDeVentas(){
        List<VentaObject> ListarVenta = usaVenta.ListarVenta();
        tablaModelo = (DefaultTableModel) tableVentas.getModel();
        Object[] obj = new Object[4];
        
        for(int i = 0; i < ListarVenta.size(); i++){
            obj[0] = ListarVenta.get(i).getId();
            obj[1] = ListarVenta.get(i).getIdCliente();
            obj[2] = ListarVenta.get(i).getVendedor();
            obj[3] = ListarVenta.get(i).getTotal();
            
            tablaModelo.addRow(obj);
        }
        tableVentas.setModel(tablaModelo);
    }
    
    public void LimpiarTabla(){
        for (int i = 0; i < tablaModelo.getRowCount(); i++){
            tablaModelo.removeRow(i);
            i = i-1;
        }
    }
    
    public void RegistrarVenta(){
        int idCliente = Integer.parseInt(txtIDClienteCobrar.getText());
        String cliente = txtNombreClienteCobrar.getText();
        int vendedor = Integer.parseInt(lblVendedor.getText());
        double monto = total;
        
        venta.setIdCliente(idCliente);
        venta.setCliente(cliente);
        venta.setVendedor(vendedor);
        venta.setTotal(monto);
        usaVenta.RegistrarVenta(venta);
    }
    
    public void RegistrarDetalleVenta(){
        int idVenta = usaVenta.IdVenta();
        for(int i = 0; i < tableCobrar.getRowCount(); i++){
            String sku = tableCobrar.getValueAt(i, 1).toString();
            double precio = Double.parseDouble(tableCobrar.getValueAt(i, 3).toString());
            int cantidad = Integer.parseInt(tableCobrar.getValueAt(i, 4).toString());            
            detalleVenta.setSku(sku);
            detalleVenta.setCantidad(cantidad);
            detalleVenta.setPrecio(precio);
            detalleVenta.setIdVenta(idVenta);
            usaVenta.RegistrarDetalleVenta(detalleVenta);
        }
    }
    
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        btnCobrar = new javax.swing.JButton();
        btnCliente = new javax.swing.JButton();
        btnProveedor = new javax.swing.JButton();
        btnProductos = new javax.swing.JButton();
        btnVentas = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        lblReferenciaVendedor = new javax.swing.JLabel();
        lblVendedor = new javax.swing.JLabel();
        jTabbedGlobal = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtSKUCobrar = new javax.swing.JTextField();
        txtProductoCobrar = new javax.swing.JTextField();
        txtCantidadCobrar = new javax.swing.JTextField();
        txtPrecioCobrar = new javax.swing.JTextField();
        txtStockCobrar = new javax.swing.JTextField();
        btnEliminarCobrar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableCobrar = new javax.swing.JTable();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        lblTotal = new javax.swing.JLabel();
        txtIDClienteCobrar = new javax.swing.JTextField();
        txtNombreClienteCobrar = new javax.swing.JTextField();
        btnAceptarCobro = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        lblIDCliente = new javax.swing.JLabel();
        lblNombreCliente = new javax.swing.JLabel();
        lblTelefonoCliente = new javax.swing.JLabel();
        lblDireccionCliente = new javax.swing.JLabel();
        lblRazonSCliente = new javax.swing.JLabel();
        txtIDCliente = new javax.swing.JTextField();
        txtNombreCliente = new javax.swing.JTextField();
        txtTelefonoCliente = new javax.swing.JTextField();
        txtDireccionCliente = new javax.swing.JTextField();
        txtRazonSCliente = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        tableCliente = new javax.swing.JTable();
        btnGuardarCliente = new javax.swing.JButton();
        btnActualizarCliente = new javax.swing.JButton();
        btnEliminarCliente = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        lblSKU = new javax.swing.JLabel();
        txtSKUProducto = new javax.swing.JTextField();
        lblNombreProducto = new javax.swing.JLabel();
        txtNombreProducto = new javax.swing.JTextField();
        lblProveedorProducto = new javax.swing.JLabel();
        lblPrecioProducto = new javax.swing.JLabel();
        txtPrecioProducto = new javax.swing.JTextField();
        lblStockProducto = new javax.swing.JLabel();
        txtStockProducto = new javax.swing.JTextField();
        btnGuardarProducto = new javax.swing.JButton();
        btnActualizarProducto = new javax.swing.JButton();
        btnEliminarProducto = new javax.swing.JButton();
        btnExcelProducto = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        tableProducto = new javax.swing.JTable();
        cbxProveedorProducto = new javax.swing.JComboBox<>();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        tableVentas = new javax.swing.JTable();
        btnReporteDeVentas = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        txtRFCProveedor = new javax.swing.JTextField();
        txtNombreProveedor = new javax.swing.JTextField();
        txtTelefonoProveedor = new javax.swing.JTextField();
        txtDireccionProveedor = new javax.swing.JTextField();
        txtRazonSProveedor = new javax.swing.JTextField();
        btnGuardarProveedor = new javax.swing.JButton();
        btnActualizarProveedor = new javax.swing.JButton();
        btnEliminarProveedor = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        tableProveedor = new javax.swing.JTable();
        jLabel11 = new javax.swing.JLabel();
        txtIDProveedor = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setBackground(new java.awt.Color(255, 255, 255));
        jLabel1.setFont(new java.awt.Font("Calibri", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 0));
        jLabel1.setText("Music House");

        btnCobrar.setBackground(new java.awt.Color(204, 204, 204));
        btnCobrar.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        btnCobrar.setForeground(new java.awt.Color(0, 0, 0));
        btnCobrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resources/Nueva venta.png"))); // NOI18N
        btnCobrar.setText("Cobrar");
        btnCobrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCobrarActionPerformed(evt);
            }
        });

        btnCliente.setBackground(new java.awt.Color(204, 204, 204));
        btnCliente.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        btnCliente.setForeground(new java.awt.Color(0, 0, 0));
        btnCliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resources/Nuevo Cliente.png"))); // NOI18N
        btnCliente.setText("Clientes");
        btnCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClienteActionPerformed(evt);
            }
        });

        btnProveedor.setBackground(new java.awt.Color(204, 204, 204));
        btnProveedor.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        btnProveedor.setForeground(new java.awt.Color(0, 0, 0));
        btnProveedor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resources/Proveedor.png"))); // NOI18N
        btnProveedor.setText("Proveedor");
        btnProveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnProveedorActionPerformed(evt);
            }
        });

        btnProductos.setBackground(new java.awt.Color(204, 204, 204));
        btnProductos.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        btnProductos.setForeground(new java.awt.Color(0, 0, 0));
        btnProductos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resources/Productos.png"))); // NOI18N
        btnProductos.setText("Productos");
        btnProductos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnProductosActionPerformed(evt);
            }
        });

        btnVentas.setBackground(new java.awt.Color(204, 204, 204));
        btnVentas.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        btnVentas.setForeground(new java.awt.Color(0, 0, 0));
        btnVentas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resources/Registro Ventas.png"))); // NOI18N
        btnVentas.setText("Ventas");
        btnVentas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVentasActionPerformed(evt);
            }
        });

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resources/Logo musichouse.png"))); // NOI18N

        lblReferenciaVendedor.setFont(new java.awt.Font("Calibri", 1, 12)); // NOI18N
        lblReferenciaVendedor.setForeground(new java.awt.Color(0, 0, 0));
        lblReferenciaVendedor.setText("Vendedor:");

        lblVendedor.setFont(new java.awt.Font("Calibri", 1, 12)); // NOI18N
        lblVendedor.setForeground(new java.awt.Color(0, 0, 0));
        lblVendedor.setText("1");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnCobrar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 136, Short.MAX_VALUE)
                .addComponent(btnCliente)
                .addGap(111, 111, 111)
                .addComponent(btnProductos)
                .addGap(126, 126, 126)
                .addComponent(btnVentas)
                .addGap(120, 120, 120)
                .addComponent(btnProveedor)
                .addGap(21, 21, 21))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel2)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(lblReferenciaVendedor)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblVendedor))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel1)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblReferenciaVendedor)
                            .addComponent(lblVendedor))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, 22, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCliente)
                    .addComponent(btnProductos)
                    .addComponent(btnCobrar)
                    .addComponent(btnProveedor)
                    .addComponent(btnVentas))
                .addGap(16, 16, 16))
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1100, 140));

        jTabbedGlobal.setInheritsPopupMenu(true);

        jLabel3.setText("SKU");

        jLabel4.setText("Producto");

        jLabel5.setText("Cantidad");

        jLabel6.setText("Precio");

        jLabel7.setText("Stock");

        txtSKUCobrar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtSKUCobrarKeyPressed(evt);
            }
        });

        txtCantidadCobrar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCantidadCobrarKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCantidadCobrarKeyTyped(evt);
            }
        });

        txtStockCobrar.setEditable(false);
        txtStockCobrar.setBackground(new java.awt.Color(204, 204, 204));
        txtStockCobrar.setFocusable(false);

        btnEliminarCobrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resources/cancelar.png"))); // NOI18N
        btnEliminarCobrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarCobrarActionPerformed(evt);
            }
        });

        tableCobrar.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "#", "SKU", "PRODUCTO", "PRECIO", "CANTIDAD", "TOTAL"
            }
        ));
        jScrollPane1.setViewportView(tableCobrar);
        if (tableCobrar.getColumnModel().getColumnCount() > 0) {
            tableCobrar.getColumnModel().getColumn(0).setPreferredWidth(10);
            tableCobrar.getColumnModel().getColumn(1).setPreferredWidth(30);
            tableCobrar.getColumnModel().getColumn(2).setPreferredWidth(100);
            tableCobrar.getColumnModel().getColumn(3).setPreferredWidth(30);
            tableCobrar.getColumnModel().getColumn(4).setPreferredWidth(30);
        }

        jLabel8.setText("ID Cliente:");

        jLabel9.setText("Nombre:");

        jLabel10.setText("Total:");

        lblTotal.setText("---");

        txtIDClienteCobrar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtIDClienteCobrarKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtIDClienteCobrarKeyTyped(evt);
            }
        });

        txtNombreClienteCobrar.setEditable(false);
        txtNombreClienteCobrar.setBackground(new java.awt.Color(204, 204, 204));
        txtNombreClienteCobrar.setFocusable(false);

        btnAceptarCobro.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resources/aceptar.png"))); // NOI18N
        btnAceptarCobro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAceptarCobroActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel7)
                            .addComponent(jLabel6)
                            .addComponent(jLabel5)
                            .addComponent(jLabel4)
                            .addComponent(jLabel3)
                            .addComponent(txtSKUCobrar, javax.swing.GroupLayout.DEFAULT_SIZE, 143, Short.MAX_VALUE)
                            .addComponent(txtProductoCobrar)
                            .addComponent(txtCantidadCobrar)
                            .addComponent(txtPrecioCobrar)
                            .addComponent(txtStockCobrar)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(82, 82, 82)
                        .addComponent(btnEliminarCobrar)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 31, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 863, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(14, 14, 14))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtIDClienteCobrar, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtNombreClienteCobrar, javax.swing.GroupLayout.PREFERRED_SIZE, 246, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnAceptarCobro)
                        .addGap(49, 49, 49)
                        .addComponent(jLabel10)
                        .addGap(18, 18, 18)
                        .addComponent(lblTotal)
                        .addGap(127, 127, 127))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addComponent(jLabel3)
                        .addGap(5, 5, 5)
                        .addComponent(txtSKUCobrar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtProductoCobrar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel5)
                        .addGap(1, 1, 1)
                        .addComponent(txtCantidadCobrar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel6)
                        .addGap(1, 1, 1)
                        .addComponent(txtPrecioCobrar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel7)
                        .addGap(1, 1, 1)
                        .addComponent(txtStockCobrar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnEliminarCobrar))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 355, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(jLabel9)
                            .addComponent(jLabel10)
                            .addComponent(lblTotal)
                            .addComponent(txtIDClienteCobrar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtNombreClienteCobrar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(9, 9, 9)
                        .addComponent(btnAceptarCobro)))
                .addContainerGap(144, Short.MAX_VALUE))
        );

        jTabbedGlobal.addTab("tab1", jPanel2);

        lblIDCliente.setFont(new java.awt.Font("Calibri Light", 1, 12)); // NOI18N
        lblIDCliente.setText("ID:");

        lblNombreCliente.setFont(new java.awt.Font("Calibri Light", 1, 12)); // NOI18N
        lblNombreCliente.setText("Nombre:");

        lblTelefonoCliente.setFont(new java.awt.Font("Calibri Light", 1, 12)); // NOI18N
        lblTelefonoCliente.setText("Teléfono:");

        lblDireccionCliente.setFont(new java.awt.Font("Calibri Light", 1, 12)); // NOI18N
        lblDireccionCliente.setText("Dirección:");

        lblRazonSCliente.setFont(new java.awt.Font("Calibri Light", 1, 12)); // NOI18N
        lblRazonSCliente.setText("Razón Social:");

        txtIDCliente.setEditable(false);
        txtIDCliente.setBackground(new java.awt.Color(204, 204, 204));
        txtIDCliente.setFocusable(false);
        txtIDCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtIDClienteActionPerformed(evt);
            }
        });

        txtTelefonoCliente.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        txtTelefonoCliente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtTelefonoClienteKeyTyped(evt);
            }
        });

        tableCliente.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "NOMBRE", "TELÉFONO", "DIRECCIÓN", "RAZÓN S", "FECHA DE ALTA"
            }
        ));
        tableCliente.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableClienteMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tableCliente);
        if (tableCliente.getColumnModel().getColumnCount() > 0) {
            tableCliente.getColumnModel().getColumn(0).setPreferredWidth(50);
            tableCliente.getColumnModel().getColumn(1).setPreferredWidth(100);
            tableCliente.getColumnModel().getColumn(2).setPreferredWidth(50);
            tableCliente.getColumnModel().getColumn(3).setPreferredWidth(80);
            tableCliente.getColumnModel().getColumn(4).setPreferredWidth(80);
            tableCliente.getColumnModel().getColumn(5).setPreferredWidth(150);
        }

        btnGuardarCliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resources/Guardar.png"))); // NOI18N
        btnGuardarCliente.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnGuardarCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarClienteActionPerformed(evt);
            }
        });

        btnActualizarCliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resources/actualizar-pagina.png"))); // NOI18N
        btnActualizarCliente.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnActualizarCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActualizarClienteActionPerformed(evt);
            }
        });

        btnEliminarCliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resources/basura.png"))); // NOI18N
        btnEliminarCliente.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnEliminarCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarClienteActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(lblIDCliente)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtIDCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblNombreCliente)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtNombreCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblTelefonoCliente)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtTelefonoCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblDireccionCliente)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtDireccionCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblRazonSCliente)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtRazonSCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(jPanel3Layout.createSequentialGroup()
                            .addComponent(btnGuardarCliente)
                            .addGap(18, 18, 18)
                            .addComponent(btnActualizarCliente)
                            .addGap(18, 18, 18)
                            .addComponent(btnEliminarCliente))
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 1067, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblIDCliente)
                    .addComponent(lblNombreCliente)
                    .addComponent(lblTelefonoCliente)
                    .addComponent(lblDireccionCliente)
                    .addComponent(lblRazonSCliente)
                    .addComponent(txtIDCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNombreCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTelefonoCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtDireccionCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtRazonSCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnGuardarCliente)
                    .addComponent(btnActualizarCliente)
                    .addComponent(btnEliminarCliente))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 307, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(135, Short.MAX_VALUE))
        );

        jTabbedGlobal.addTab("tab2", jPanel3);

        lblSKU.setText("SKU");

        lblNombreProducto.setText("Nombre:");

        lblProveedorProducto.setText("Proveedor:");

        lblPrecioProducto.setText("Precio:");

        lblStockProducto.setText("Stock");

        txtStockProducto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtStockProductoKeyTyped(evt);
            }
        });

        btnGuardarProducto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resources/Guardar.png"))); // NOI18N
        btnGuardarProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarProductoActionPerformed(evt);
            }
        });

        btnActualizarProducto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resources/actualizar-pagina.png"))); // NOI18N
        btnActualizarProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActualizarProductoActionPerformed(evt);
            }
        });

        btnEliminarProducto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resources/basura.png"))); // NOI18N
        btnEliminarProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarProductoActionPerformed(evt);
            }
        });

        btnExcelProducto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resources/excel.png"))); // NOI18N
        btnExcelProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExcelProductoActionPerformed(evt);
            }
        });

        tableProducto.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "SKU", "NOMBRE", "PROVEEDOR", "STOCK", "PRECIO"
            }
        ));
        tableProducto.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableProductoMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(tableProducto);
        if (tableProducto.getColumnModel().getColumnCount() > 0) {
            tableProducto.getColumnModel().getColumn(0).setPreferredWidth(50);
            tableProducto.getColumnModel().getColumn(1).setPreferredWidth(100);
            tableProducto.getColumnModel().getColumn(2).setPreferredWidth(60);
            tableProducto.getColumnModel().getColumn(3).setPreferredWidth(40);
            tableProducto.getColumnModel().getColumn(4).setPreferredWidth(50);
        }

        cbxProveedorProducto.setEditable(true);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(lblSKU)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtSKUProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(lblNombreProducto)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtNombreProducto)
                        .addGap(18, 18, 18)
                        .addComponent(lblProveedorProducto)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbxProveedorProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(lblStockProducto)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtStockProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(lblPrecioProducto)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtPrecioProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(btnGuardarProducto)
                                .addGap(18, 18, 18)
                                .addComponent(btnActualizarProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnEliminarProducto)
                                .addGap(18, 18, 18)
                                .addComponent(btnExcelProducto))
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 1059, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 9, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lblStockProducto)
                        .addComponent(txtStockProducto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lblSKU)
                        .addComponent(txtSKUProducto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblNombreProducto)
                        .addComponent(txtNombreProducto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblProveedorProducto)
                        .addComponent(lblPrecioProducto)
                        .addComponent(txtPrecioProducto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(cbxProveedorProducto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnGuardarProducto)
                        .addComponent(btnEliminarProducto)
                        .addComponent(btnExcelProducto))
                    .addComponent(btnActualizarProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(136, Short.MAX_VALUE))
        );

        jTabbedGlobal.addTab("tab4", jPanel5);

        tableVentas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "CLIENTE", "VENDEDOR", "TOTAL"
            }
        ));
        tableVentas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableVentasMouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(tableVentas);
        if (tableVentas.getColumnModel().getColumnCount() > 0) {
            tableVentas.getColumnModel().getColumn(0).setPreferredWidth(20);
            tableVentas.getColumnModel().getColumn(1).setPreferredWidth(60);
            tableVentas.getColumnModel().getColumn(2).setPreferredWidth(60);
            tableVentas.getColumnModel().getColumn(3).setPreferredWidth(60);
        }

        btnReporteDeVentas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resources/excel.png"))); // NOI18N
        btnReporteDeVentas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReporteDeVentasActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 1074, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnReporteDeVentas)))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(btnReporteDeVentas)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 349, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(131, Short.MAX_VALUE))
        );

        jTabbedGlobal.addTab("tab5", jPanel6);

        jLabel17.setFont(new java.awt.Font("Calibri", 1, 12)); // NOI18N
        jLabel17.setText("RFC:");

        jLabel18.setFont(new java.awt.Font("Calibri", 1, 12)); // NOI18N
        jLabel18.setText("Nombre:");

        jLabel19.setFont(new java.awt.Font("Calibri", 1, 12)); // NOI18N
        jLabel19.setText("Teléfono:");

        jLabel20.setFont(new java.awt.Font("Calibri", 1, 12)); // NOI18N
        jLabel20.setText("Dirección:");

        jLabel21.setFont(new java.awt.Font("Calibri", 1, 12)); // NOI18N
        jLabel21.setText("Razón S:");

        txtNombreProveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNombreProveedorActionPerformed(evt);
            }
        });

        txtTelefonoProveedor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtTelefonoProveedorKeyTyped(evt);
            }
        });

        btnGuardarProveedor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resources/Guardar.png"))); // NOI18N
        btnGuardarProveedor.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnGuardarProveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarProveedorActionPerformed(evt);
            }
        });

        btnActualizarProveedor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resources/actualizar-pagina.png"))); // NOI18N
        btnActualizarProveedor.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnActualizarProveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActualizarProveedorActionPerformed(evt);
            }
        });

        btnEliminarProveedor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resources/basura.png"))); // NOI18N
        btnEliminarProveedor.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnEliminarProveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarProveedorActionPerformed(evt);
            }
        });

        tableProveedor.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "RFC", "NOMBRE", "TELÉFONO", "DIRECCIÓN", "RAZÓN S.", "FECHA DE REGISTRO"
            }
        ));
        tableProveedor.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableProveedorMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tableProveedor);
        if (tableProveedor.getColumnModel().getColumnCount() > 0) {
            tableProveedor.getColumnModel().getColumn(0).setPreferredWidth(20);
            tableProveedor.getColumnModel().getColumn(1).setPreferredWidth(100);
            tableProveedor.getColumnModel().getColumn(2).setPreferredWidth(100);
            tableProveedor.getColumnModel().getColumn(3).setPreferredWidth(50);
            tableProveedor.getColumnModel().getColumn(4).setPreferredWidth(80);
            tableProveedor.getColumnModel().getColumn(5).setPreferredWidth(70);
            tableProveedor.getColumnModel().getColumn(6).setPreferredWidth(130);
        }

        jLabel11.setFont(new java.awt.Font("Calibri", 1, 12)); // NOI18N
        jLabel11.setText("ID: ");

        txtIDProveedor.setEditable(false);
        txtIDProveedor.setBackground(new java.awt.Color(204, 204, 204));
        txtIDProveedor.setFocusable(false);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel17)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(jLabel11)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtIDProveedor)
                            .addComponent(txtRFCProveedor, javax.swing.GroupLayout.DEFAULT_SIZE, 123, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel18)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtNombreProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel19)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtTelefonoProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel20)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtDireccionProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel21)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtRazonSProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(btnGuardarProveedor)
                                .addGap(18, 18, 18)
                                .addComponent(btnActualizarProveedor)
                                .addGap(18, 18, 18)
                                .addComponent(btnEliminarProveedor)))))
                .addGap(18, 18, 18))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17)
                    .addComponent(jLabel18)
                    .addComponent(jLabel19)
                    .addComponent(jLabel20)
                    .addComponent(jLabel21)
                    .addComponent(txtRFCProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNombreProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTelefonoProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtDireccionProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtRazonSProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnGuardarProveedor)
                        .addComponent(btnActualizarProveedor)
                        .addComponent(btnEliminarProveedor))
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel11)
                        .addComponent(txtIDProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 334, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(124, Short.MAX_VALUE))
        );

        jTabbedGlobal.addTab("tab3", jPanel4);

        getContentPane().add(jTabbedGlobal, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 100, 1080, 590));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtIDClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtIDClienteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIDClienteActionPerformed

    private void txtNombreProveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNombreProveedorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombreProveedorActionPerformed

    private void btnGuardarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarClienteActionPerformed
        if (!"".equals(txtNombreCliente.getText()) || !"".equals(txtTelefonoCliente.getText()) || !"".equals(txtDireccionCliente.getText()))
        {
            cl.setNombre(txtNombreCliente.getText());
            cl.setTelefono(txtTelefonoCliente.getText());
            cl.setDireccion(txtDireccionCliente.getText());
            cl.setRazonS(txtRazonSCliente.getText());
            
            usaCliente.RegistrarCliente(cl);
            
            JOptionPane.showMessageDialog(null, "Se registró el cliente con éxito.");
            LimpiarTabla();
            ListadoDeClientes();
            LimpiarTxtsCliente();
        }else{
            JOptionPane.showMessageDialog(null, "Cliente NO registrado. Revisar los campos.");
        }
    }//GEN-LAST:event_btnGuardarClienteActionPerformed

    private void btnClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClienteActionPerformed
        LimpiarTabla();
        ListadoDeClientes();
        jTabbedGlobal.setSelectedIndex(1);
    }//GEN-LAST:event_btnClienteActionPerformed

    private void tableClienteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableClienteMouseClicked
        int fila = tableCliente.rowAtPoint(evt.getPoint());
        txtIDCliente.setText(tableCliente.getValueAt(fila, 0).toString());
        txtNombreCliente.setText(tableCliente.getValueAt(fila, 1).toString());
        txtTelefonoCliente.setText(tableCliente.getValueAt(fila, 2).toString());
        txtDireccionCliente.setText(tableCliente.getValueAt(fila, 3).toString());
        txtRazonSCliente.setText(tableCliente.getValueAt(fila, 4).toString());
    }//GEN-LAST:event_tableClienteMouseClicked

    private void btnEliminarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarClienteActionPerformed
        if(!"".equals(txtIDCliente.getText())){
            int q = JOptionPane.showConfirmDialog(null, "¿Quieres eliminar al cliente?");
            if(q == 0){
                int id = Integer.parseInt(txtIDCliente.getText());
                usaCliente.EliminarCliente(id);
                LimpiarTabla();
                ListadoDeClientes();
                LimpiarTxtsCliente();
            }
        }
    }//GEN-LAST:event_btnEliminarClienteActionPerformed

    private void btnActualizarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarClienteActionPerformed
        if("".equals(txtIDCliente.getText())){
            JOptionPane.showMessageDialog(null, "Seleccione un cliente.");
        }else{
            if(!"".equals(txtNombreCliente.getText()) && !"".equals(txtTelefonoCliente.getText()) && !"".equals(txtDireccionCliente.getText()) && !"".equals(txtRazonSCliente.getText())){
                cl.setId(Integer.parseInt(txtIDCliente.getText()));
                cl.setNombre(txtNombreCliente.getText());
                cl.setTelefono(txtTelefonoCliente.getText());
                cl.setDireccion(txtDireccionCliente.getText());
                cl.setRazonS(txtRazonSCliente.getText());
                
                usaCliente.ModificarCliente(cl);
                
                LimpiarTabla();
                LimpiarTxtsCliente();
                ListadoDeClientes();
            }else{
                JOptionPane.showMessageDialog(null, "El cliente NO se actualizó. Favor de revisar los campos.");
            }
        }
    }//GEN-LAST:event_btnActualizarClienteActionPerformed

    private void btnGuardarProveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarProveedorActionPerformed
        if (!"".equals(txtRFCProveedor.getText()) && !"".equals(txtNombreProveedor.getText()) && !"".equals(txtTelefonoProveedor.getText()) && 
                !"".equals(txtDireccionProveedor.getText()) && !"".equals(txtRazonSProveedor.getText())){
            pr.setRfc(txtRFCProveedor.getText());
            pr.setNombre(txtNombreProveedor.getText());
            pr.setTelefono(txtTelefonoProveedor.getText());
            pr.setDireccion(txtDireccionProveedor.getText());
            pr.setRazonSocial(txtRazonSProveedor.getText());
            usaProveedor.RegistroDeProveedor(pr);
            LimpiarTabla();
            ListadoDeProveedores();
            LimpiarTxtsProveedor();
        }else{
            JOptionPane.showMessageDialog(null, "El proveedor NO se registró. Favor de revisar los campos.");
        }
    }//GEN-LAST:event_btnGuardarProveedorActionPerformed

    private void btnProveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProveedorActionPerformed
        LimpiarTabla();
        ListadoDeProveedores();
        jTabbedGlobal.setSelectedIndex(4);
    }//GEN-LAST:event_btnProveedorActionPerformed

    private void tableProveedorMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableProveedorMouseClicked
        int fila = tableProveedor.rowAtPoint(evt.getPoint());
        txtIDProveedor.setText(tableProveedor.getValueAt(fila, 0).toString());
        txtRFCProveedor.setText(tableProveedor.getValueAt(fila, 1).toString());
        txtNombreProveedor.setText(tableProveedor.getValueAt(fila, 2).toString());
        txtTelefonoProveedor.setText(tableProveedor.getValueAt(fila, 3).toString());
        txtDireccionProveedor.setText(tableProveedor.getValueAt(fila, 4).toString());
        txtRazonSProveedor.setText(tableProveedor.getValueAt(fila, 5).toString());
    }//GEN-LAST:event_tableProveedorMouseClicked

    private void btnEliminarProveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarProveedorActionPerformed
        if(!"".equals(txtIDProveedor.getText())){
            int q = JOptionPane.showConfirmDialog(null, "¿Quieres eliminar al proveedor?");
            if(q == 0){
                int id = Integer.parseInt(txtIDProveedor.getText());
                usaProveedor.EliminarProveedor(id);
                LimpiarTabla();
                ListadoDeProveedores();
                LimpiarTxtsProveedor();
            }
        }
    }//GEN-LAST:event_btnEliminarProveedorActionPerformed

    private void btnActualizarProveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarProveedorActionPerformed
        if("".equals(txtIDProveedor.getText())){
            JOptionPane.showMessageDialog(null, "Seleccione un proveedor.");
        }else{
            if(!"".equals(txtRFCProveedor.getText()) && !"".equals(txtNombreProveedor.getText()) && !"".equals(txtTelefonoProveedor.getText()) && !"".equals(txtDireccionProveedor.getText()) && !"".equals(txtRazonSProveedor.getText())){
                pr.setId(Integer.parseInt(txtIDProveedor.getText()));
                pr.setRfc(txtRFCProveedor.getText());
                pr.setNombre(txtNombreProveedor.getText());
                pr.setTelefono(txtTelefonoProveedor.getText());
                pr.setDireccion(txtDireccionProveedor.getText());
                pr.setRazonSocial(txtRazonSProveedor.getText());

                usaProveedor.ModificarProveedor(pr);
                
                LimpiarTabla();
                LimpiarTxtsProveedor();
                ListadoDeProveedores();
            }else{
                JOptionPane.showMessageDialog(null, "El proveedor NO se actualizó. Favor de revisar los campos.");
            }
        }
    }//GEN-LAST:event_btnActualizarProveedorActionPerformed

    private void btnGuardarProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarProductoActionPerformed
        
            if(!"".equals(txtSKUProducto.getText()) && !"".equals(txtNombreProducto) && !"".equals(cbxProveedorProducto.getSelectedItem().toString()) && !"".equals(txtStockProducto.getText()) && !"".equals(txtPrecioProducto) ){
                prod.setSku(txtSKUProducto.getText());
                prod.setNombre(txtNombreProducto.getText());
                prod.setProveedor(cbxProveedorProducto.getSelectedItem().toString());
                prod.setStock(Integer.parseInt(txtStockProducto.getText()));
                prod.setPrecio(Double.parseDouble(txtPrecioProducto.getText()));

                usaProducto.RegistrarProductos(prod);
                JOptionPane.showMessageDialog(null, "Producto registrado con éxito.");

            }else{
                JOptionPane.showMessageDialog(null, "El producto NO se agregó. Favor de revisar los campos vacíos.");
            }
    }//GEN-LAST:event_btnGuardarProductoActionPerformed

    private void btnProductosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProductosActionPerformed
        LimpiarTabla();
        ListadoDeProductos();
        jTabbedGlobal.setSelectedIndex(2);
    }//GEN-LAST:event_btnProductosActionPerformed

    private void tableVentasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableVentasMouseClicked
      
    }//GEN-LAST:event_tableVentasMouseClicked

    private void tableProductoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableProductoMouseClicked
        int fila = tableProducto.rowAtPoint(evt.getPoint());
        txtSKUProducto.setText(tableProducto.getValueAt(fila, 0).toString());
        txtNombreProducto.setText(tableProducto.getValueAt(fila, 1).toString());
        cbxProveedorProducto.setSelectedItem(tableProducto.getValueAt(fila, 2).toString());
        txtStockProducto.setText(tableProducto.getValueAt(fila, 3).toString());
        txtPrecioProducto.setText(tableProducto.getValueAt(fila, 4).toString());
    }//GEN-LAST:event_tableProductoMouseClicked

    private void btnEliminarProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarProductoActionPerformed
        if(!"".equals(txtSKUProducto.getText())){
            int q = JOptionPane.showConfirmDialog(null, "¿Quieres eliminar al producto?");
            if(q == 0){
                String sku = txtSKUProducto.getText();
                usaProducto.EliminarProducto(sku);
                LimpiarTabla();
                ListadoDeProductos();
                LimpiarTxtsProductos();
            }
        }
    }//GEN-LAST:event_btnEliminarProductoActionPerformed

    private void btnActualizarProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarProductoActionPerformed
        if("".equals(txtSKUProducto.getText())){
            JOptionPane.showMessageDialog(null, "Seleccione un producto.");
        }else{
            if(!"".equals(txtNombreProducto.getText()) && !"".equals(cbxProveedorProducto.getSelectedItem().toString()) && !"".equals(txtStockProducto.getText()) && !"".equals(txtPrecioProducto.getText())){
                prod.setSku(txtSKUProducto.getText());
                prod.setNombre(txtNombreProducto.getText());
                prod.setProveedor(cbxProveedorProducto.getSelectedItem().toString());
                prod.setStock(Integer.parseInt(txtStockProducto.getText()));
                prod.setPrecio(Double.parseDouble(txtPrecioProducto.getText()));

                usaProducto.ModificarProductos(prod);
                
                LimpiarTabla();
                LimpiarTxtsProductos();
                ListadoDeProductos();
            }else{
                JOptionPane.showMessageDialog(null, "El producto NO se actualizó. Favor de revisar los campos.");
            }
        }
    }//GEN-LAST:event_btnActualizarProductoActionPerformed

    private void btnExcelProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcelProductoActionPerformed
        ReporteSKU.InventarioDeProductos();
    }//GEN-LAST:event_btnExcelProductoActionPerformed

    private void txtSKUCobrarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSKUCobrarKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            if(!"".equals(txtSKUCobrar.getText())){
                String sku = txtSKUCobrar.getText();
                prod = usaProducto.BuscarProducto(sku);
                if(prod.getNombre() != null){
                    txtProductoCobrar.setText(""+prod.getNombre());
                    txtStockCobrar.setText(""+prod.getStock());
                    txtPrecioCobrar.setText(""+prod.getPrecio());
                    txtCantidadCobrar.requestFocus();
                }else{
                    LimpiarTxtsCobrar();
                    txtSKUCobrar.requestFocus();
                }
            }else{
                JOptionPane.showMessageDialog(null, "Favor de capturar el SKU. Campo vacío.");
                txtSKUCobrar.requestFocus();
            }
        }
    }//GEN-LAST:event_txtSKUCobrarKeyPressed

    private void txtCantidadCobrarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCantidadCobrarKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            if(!"".equals(txtCantidadCobrar.getText())){
                String sku = txtSKUCobrar.getText();
                String producto = txtProductoCobrar.getText();
                int cantidad = Integer.parseInt(txtCantidadCobrar.getText());
                double precio = Double.parseDouble(txtPrecioCobrar.getText());
                double total = cantidad * precio;
                int stock = Integer.parseInt(txtStockCobrar.getText());
                
                if(stock >= cantidad){
                    item = item + 1;
                    tablaTmp = (DefaultTableModel) tableCobrar.getModel();
                    
                    for(int i = 0; i < tableCobrar.getRowCount(); i++){
                        if(tableCobrar.getValueAt(i, 1).equals(txtProductoCobrar.getText())){
                            JOptionPane.showMessageDialog(null, "El producto ya está agregado.");
                            return;
                        }
                    }
                    
                    ArrayList lista = new ArrayList();
                    lista.add(item);
                    lista.add(sku);
                    lista.add(producto);
                    lista.add(precio);
                    lista.add(cantidad);
                    lista.add(total);
                    
                    Object[] obj = new Object[6];
                    obj[0] = lista.get(0);
                    obj[1] = lista.get(1);
                    obj[2] = lista.get(2);
                    obj[3] = lista.get(3);
                    obj[4] = lista.get(4);
                    obj[5] = lista.get(5);
                    
                    tablaTmp.addRow(obj);
                    tableCobrar.setModel(tablaTmp);
                    TotalAPagar();
                    LimpiarTxtsCobrar();
                    txtSKUCobrar.requestFocus();
                }else{
                    JOptionPane.showMessageDialog(null, "No hay stock para este producto.");
                }
            }else{
                JOptionPane.showConfirmDialog(null, "Ingrese la cantidad a vender.");
            }
        }
    }//GEN-LAST:event_txtCantidadCobrarKeyPressed

    private void btnEliminarCobrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarCobrarActionPerformed
        tablaModelo = (DefaultTableModel) tableCobrar.getModel();
        tablaModelo.removeRow(tableCobrar.getSelectedRow());
        TotalAPagar();
        txtSKUCobrar.requestFocus();
    }//GEN-LAST:event_btnEliminarCobrarActionPerformed

    private void txtIDClienteCobrarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtIDClienteCobrarKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            if(!"".equals(txtIDClienteCobrar.getText())){
                int id = Integer.parseInt(txtIDClienteCobrar.getText());
                cl = usaCliente.BuscarClienteCobro(id);                
                if(cl.getNombre() != null){
                    txtNombreClienteCobrar.setText(cl.getNombre());
                }else{
                    txtIDClienteCobrar.setText("");
                    JOptionPane.showMessageDialog(null, "El cliente no existe. Favor de verificarlo.");
                }
            }
        }
    }//GEN-LAST:event_txtIDClienteCobrarKeyPressed

    private void btnAceptarCobroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAceptarCobroActionPerformed
        RegistrarVenta();
        RegistrarDetalleVenta();
        ActualizarStock();
        LimpiarTablaCobro();
        txtIDClienteCobrar.setText("");
        txtNombreClienteCobrar.setText("");
    }//GEN-LAST:event_btnAceptarCobroActionPerformed

    private void btnCobrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCobrarActionPerformed
        jTabbedGlobal.setSelectedIndex(0);
    }//GEN-LAST:event_btnCobrarActionPerformed

    private void btnReporteDeVentasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReporteDeVentasActionPerformed
        ReporteVentas.ReporteDeVentas();
    }//GEN-LAST:event_btnReporteDeVentasActionPerformed

    private void btnVentasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVentasActionPerformed
        jTabbedGlobal.setSelectedIndex(3);
        LimpiarTabla();
        ListadoDeVentas();
    }//GEN-LAST:event_btnVentasActionPerformed

    private void txtCantidadCobrarKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCantidadCobrarKeyTyped
        char c = evt.getKeyChar();
        
        if(!Character.isDigit(c)){
            evt.consume();
        }
    }//GEN-LAST:event_txtCantidadCobrarKeyTyped

    private void txtIDClienteCobrarKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtIDClienteCobrarKeyTyped
        char c = evt.getKeyChar();
        
        if(!Character.isDigit(c)){
            evt.consume();
        }
    }//GEN-LAST:event_txtIDClienteCobrarKeyTyped

    private void txtTelefonoClienteKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTelefonoClienteKeyTyped
       char c = evt.getKeyChar();
        
        if(!Character.isDigit(c)){
            evt.consume();
        }
    }//GEN-LAST:event_txtTelefonoClienteKeyTyped

    private void txtStockProductoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtStockProductoKeyTyped
        char c = evt.getKeyChar();
        
        if(!Character.isDigit(c)){
            evt.consume();
        }
    }//GEN-LAST:event_txtStockProductoKeyTyped

    private void txtTelefonoProveedorKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTelefonoProveedorKeyTyped
        char c = evt.getKeyChar();
        
        if(!Character.isDigit(c)){
            evt.consume();
        }
    }//GEN-LAST:event_txtTelefonoProveedorKeyTyped

    private void LimpiarTxtsCliente(){
        txtIDCliente.setText("");
        txtNombreCliente.setText("");
        txtTelefonoCliente.setText("");
        txtDireccionCliente.setText("");
        txtRazonSCliente.setText("");
    }
    
    private void LimpiarTxtsProveedor(){
        txtIDProveedor.setText("");
        txtRFCProveedor.setText("");
        txtNombreProveedor.setText("");
        txtTelefonoProveedor.setText("");
        txtDireccionProveedor.setText("");
        txtRazonSProveedor.setText("");
    }
    
    private void LimpiarTxtsProductos(){
        txtSKUProducto.setText("");
        txtNombreProducto.setText("");
        cbxProveedorProducto.setSelectedItem(null);
        txtStockProducto.setText("");
        txtPrecioProducto.setText("");
    }
    
    private void LimpiarTxtsCobrar(){
        txtSKUCobrar.setText("");
        txtProductoCobrar.setText("");
        txtCantidadCobrar.setText("");
        txtPrecioCobrar.setText("");
        txtStockCobrar.setText("");
    }
    
    private void TotalAPagar(){
        total = 0.00;
        int fila = tableCobrar.getRowCount();
        for(int i = 0; i < fila; i++){
            //Se parsea a tipo decimal, y posteriormente convertimos el object a string con valueOf
            double calc = Double.parseDouble(String.valueOf(tableCobrar.getModel().getValueAt(i, 5)));
            total = total + calc;
        }
        //Se le da el formato a 2 decimales.
        lblTotal.setText(String.format("%.2f", total));
    }
    
    private void ActualizarStock(){
        for(int i = 0; i < tableCobrar.getRowCount(); i++){
            String sku = tableCobrar.getValueAt(i, 1).toString();
            int cantidad = Integer.parseInt(tableCobrar.getValueAt(i, 4).toString());
            prod = usaProducto.BuscarProducto(sku);
            int StockActual = prod.getStock() - cantidad;
            usaVenta.ActualizarStock(StockActual, sku);
        }
    }
    
    private void LimpiarTablaCobro(){
        tablaTmp = (DefaultTableModel) tableCobrar.getModel();        
        int fila = tableCobrar.getRowCount();
        
        for(int i = 0; i < fila; i++){
            tablaTmp.removeRow(0);
        }
    }
    
   
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Sistema.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Sistema.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Sistema.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Sistema.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Sistema().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAceptarCobro;
    private javax.swing.JButton btnActualizarCliente;
    private javax.swing.JButton btnActualizarProducto;
    private javax.swing.JButton btnActualizarProveedor;
    private javax.swing.JButton btnCliente;
    private javax.swing.JButton btnCobrar;
    private javax.swing.JButton btnEliminarCliente;
    private javax.swing.JButton btnEliminarCobrar;
    private javax.swing.JButton btnEliminarProducto;
    private javax.swing.JButton btnEliminarProveedor;
    private javax.swing.JButton btnExcelProducto;
    private javax.swing.JButton btnGuardarCliente;
    private javax.swing.JButton btnGuardarProducto;
    private javax.swing.JButton btnGuardarProveedor;
    private javax.swing.JButton btnProductos;
    private javax.swing.JButton btnProveedor;
    private javax.swing.JButton btnReporteDeVentas;
    private javax.swing.JButton btnVentas;
    private javax.swing.JComboBox<String> cbxProveedorProducto;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTabbedPane jTabbedGlobal;
    private javax.swing.JLabel lblDireccionCliente;
    private javax.swing.JLabel lblIDCliente;
    private javax.swing.JLabel lblNombreCliente;
    private javax.swing.JLabel lblNombreProducto;
    private javax.swing.JLabel lblPrecioProducto;
    private javax.swing.JLabel lblProveedorProducto;
    private javax.swing.JLabel lblRazonSCliente;
    private javax.swing.JLabel lblReferenciaVendedor;
    private javax.swing.JLabel lblSKU;
    private javax.swing.JLabel lblStockProducto;
    private javax.swing.JLabel lblTelefonoCliente;
    private javax.swing.JLabel lblTotal;
    private javax.swing.JLabel lblVendedor;
    private javax.swing.JTable tableCliente;
    private javax.swing.JTable tableCobrar;
    private javax.swing.JTable tableProducto;
    private javax.swing.JTable tableProveedor;
    private javax.swing.JTable tableVentas;
    private javax.swing.JTextField txtCantidadCobrar;
    private javax.swing.JTextField txtDireccionCliente;
    private javax.swing.JTextField txtDireccionProveedor;
    private javax.swing.JTextField txtIDCliente;
    private javax.swing.JTextField txtIDClienteCobrar;
    private javax.swing.JTextField txtIDProveedor;
    private javax.swing.JTextField txtNombreCliente;
    private javax.swing.JTextField txtNombreClienteCobrar;
    private javax.swing.JTextField txtNombreProducto;
    private javax.swing.JTextField txtNombreProveedor;
    private javax.swing.JTextField txtPrecioCobrar;
    private javax.swing.JTextField txtPrecioProducto;
    private javax.swing.JTextField txtProductoCobrar;
    private javax.swing.JTextField txtRFCProveedor;
    private javax.swing.JTextField txtRazonSCliente;
    private javax.swing.JTextField txtRazonSProveedor;
    private javax.swing.JTextField txtSKUCobrar;
    private javax.swing.JTextField txtSKUProducto;
    private javax.swing.JTextField txtStockCobrar;
    private javax.swing.JTextField txtStockProducto;
    private javax.swing.JTextField txtTelefonoCliente;
    private javax.swing.JTextField txtTelefonoProveedor;
    // End of variables declaration//GEN-END:variables
}
