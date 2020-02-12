package com.desarrollo.kuky.aguasriojanas.objeto.unidadfacturacion;

public class Unidad {
    private int unidad;
    private int nis;
    private int usuario;
    private String nombre;
    private String calle;
    private int numeroCasa;
    private int localidad; //(porque asi esta representado en la bd)
    private String datoComplementario;

    public int getUnidad() {
        return unidad;
    }

    public void setUnidad(int unidad) {
        this.unidad = unidad;
    }

    public int getNis() {
        return nis;
    }

    public void setNis(int nis) {
        this.nis = nis;
    }

    public int getUsuario() {
        return usuario;
    }

    public void setUsuario(int usuario) {
        this.usuario = usuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public int getNumeroCasa() {
        return numeroCasa;
    }

    public void setNumeroCasa(int numeroCasa) {
        this.numeroCasa = numeroCasa;
    }

    public int getLocalidad() {
        return localidad;
    }

    public void setLocalidad(int localidad) {
        this.localidad = localidad;
    }

    public String getDatoComplementario() {
        return datoComplementario;
    }

    public void setDatoComplementario(String datoComplementario) {
        this.datoComplementario = datoComplementario;
    }
}
