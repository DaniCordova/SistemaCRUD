/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

/**
 *
 * @author danic
 */
public class ClienteObject {
    private int id;
    private String nombre;
    private String telefono;
    private String direccion;
    private String razonS;

    public ClienteObject() {
    }

    public ClienteObject(int id, String nombre, String telefono, String direccion, String razonS) {
        this.id = id;
        this.nombre = nombre;
        this.telefono = telefono;
        this.direccion = direccion;
        this.razonS = razonS;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getRazonS() {
        return razonS;
    }

    public void setRazonS(String razonS) {
        this.razonS = razonS;
    }
    
    
    
}
