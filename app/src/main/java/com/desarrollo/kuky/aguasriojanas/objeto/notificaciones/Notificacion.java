package com.desarrollo.kuky.aguasriojanas.objeto.notificaciones;

import java.util.Date;

public class Notificacion {
    private String resumen;
    private Date fecha;

    public String getResumen() {
        return resumen;
    }

    public void setResumen(String resumen) {
        this.resumen = resumen;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
}
