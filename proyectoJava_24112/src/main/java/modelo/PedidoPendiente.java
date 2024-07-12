/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author zumar
 */
public class PedidoPendiente {
    
    private int id;
    private int numeroDeMesa;
    private String nombre;
    private String descripcion;
    private double precioTotal;
  

    public PedidoPendiente(int id, int numeroDeMesa, String nombre, String descripcion, double precioTotal) {
        this.id = id;
        this.numeroDeMesa = numeroDeMesa;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precioTotal = precioTotal;
    }

    public PedidoPendiente() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumeroDeMesa() {
        return numeroDeMesa;
    }

    public void setNumeroDeMesa(int numeroDeMesa) {
        this.numeroDeMesa = numeroDeMesa;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getPrecioTotal() {
        return precioTotal;
    }

    public void setPrecioTotal(double precioTotal) {
        this.precioTotal = precioTotal;
    }


}
