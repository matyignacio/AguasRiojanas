package com.desarrollo.kuky.aguasriojanas.objeto.unidadfacturacion;

import androidx.annotation.NonNull;

public class RelacionInmueble {
    private int id;
    private String nombre;

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

    @NonNull
    @Override
    public String toString() {
        return this.nombre;
    }
}
