package com.desarrollo.kuky.aguasriojanas.objeto.reclamo;

import java.util.Date;

public class Reclamo {
    private int id;
    private int idTipoReclamo;
    private int idUbicacionReclamo;
    private String direccion;
    private String descripcion;
    private String foto;
    private Date fecha;
    private boolean finalizado;
    private String unidad;
    private String idUsuario;

    public Reclamo() {
    }

    public Reclamo(int idTipoReclamo, int idUbicacionReclamo, String direccion, String descripcion, /*Blob foto, */String unidad, String idUsuario) {
        this.idTipoReclamo = idTipoReclamo;
        this.idUbicacionReclamo = idUbicacionReclamo;
        this.direccion = direccion;
        this.descripcion = descripcion;
        //this.foto = foto;
        this.unidad = unidad;
        this.idUsuario = idUsuario;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdTipoReclamo() {
        return idTipoReclamo;
    }

    public void setIdTipoReclamo(int idTipoReclamo) {
        this.idTipoReclamo = idTipoReclamo;
    }

    public int getIdUbicacionReclamo() {
        return idUbicacionReclamo;
    }

    public void setIdUbicacionReclamo(int idUbicacionReclamo) {
        this.idUbicacionReclamo = idUbicacionReclamo;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getUnidad() {
        return unidad;
    }

    public void setUnidad(String unidad) {
        this.unidad = unidad;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public boolean isFinalizado() {
        return finalizado;
    }

    public void setFinalizado(boolean finalizado) {
        this.finalizado = finalizado;
    }
}
