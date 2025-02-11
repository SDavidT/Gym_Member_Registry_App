package com.example.registropegasusgym.CardView;

import java.io.Serializable;

public class Usuarios implements Serializable {


    private String nombre;
    private String cedula;
    private String estado;
    private String mensualidad;
    private String fechavencimiento;
    private String fechaingreso;


    public Usuarios(String nombre, String cedula, String estado, String mensualidad, String fechavencimiento, String fechaingreso) {
        this.nombre = nombre;
        this.cedula = cedula;
        this.estado = estado;
        this.mensualidad=mensualidad;
        this.fechavencimiento=fechavencimiento;
        this.fechaingreso=fechaingreso;
    }


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getMensualidad() {
        return mensualidad;
    }

    public void setMensualidad(String mensualidad) {
        this.mensualidad = mensualidad;
    }

    public String getFechavencimiento() {
        return fechavencimiento;
    }

    public void setFechavencimiento(String fechavencimiento) { this.fechavencimiento = fechavencimiento;}

    public String getFechaingreso() {
        return fechaingreso;
    }

    public void setFechaingreso(String fechaingreso) { this.fechaingreso = fechaingreso;}

    @Override
    public String toString() {
        return "Usuarios{" +
                "nombre='" + nombre + '\'' +
                ", cedula='" + cedula + '\'' +
                ", estado='" + estado + '\'' +
                ", mensualidad='" + mensualidad + '\'' +
                ", fechavencimiento='" + fechavencimiento + '\'' +
                ", fechaingreso='" + fechaingreso + '\'' +
                '}';
    }
}
