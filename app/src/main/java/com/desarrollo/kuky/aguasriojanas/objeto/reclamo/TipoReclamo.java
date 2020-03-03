package com.desarrollo.kuky.aguasriojanas.objeto.reclamo;

public class TipoReclamo {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TipoReclamo that = (TipoReclamo) o;
        return id == that.id;
    }
}
