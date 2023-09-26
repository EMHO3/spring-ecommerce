package com.curso.ecommerce.model;

public class DetalleOrden {
    private Integer id;
    private String nombre;
    private double total;
    private double precio;
    private int cantidad;

    public DetalleOrden(){

    }

    public DetalleOrden(Integer id, String nombre, double total,
                        double precio, int cantidad) {
        super();
        this.id = id;
        this.nombre = nombre;
        this.total = total;
        this.precio = precio;
        this.cantidad = cantidad;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    @Override
    public String toString() {
        return "DetalleOrden{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", total=" + total +
                ", precio=" + precio +
                ", cantidad=" + cantidad +
                '}';
    }
}
