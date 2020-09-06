package com.desarrollo.kuky.aguasriojanas.controlador.unidadfacturacion;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.desarrollo.kuky.aguasriojanas.ui.unidadfacturacion.EstadoCuentaActivity;
import com.desarrollo.kuky.aguasriojanas.ui.unidadfacturacion.NuevaUnidadAguasActivity;
import com.desarrollo.kuky.aguasriojanas.ui.unidadfacturacion.NuevaUnidadEdelarActivity;

import java.util.ArrayList;

import static com.desarrollo.kuky.aguasriojanas.util.Util.abrirActivity;
import static com.desarrollo.kuky.aguasriojanas.util.Util.abrirActivityWithBundle;

public class UnidadFacturacionControlador {
    private RelacionInmuebleControlador relacionInmuebleControlador = new RelacionInmuebleControlador();

    public void abrirNuevaUnidadAguas(Activity a, ProgressBar progressBar, TextView tvProgressBar) {
        NuevaUnidadAguasActivity.relacionesInmuebles = new ArrayList<>();
        relacionInmuebleControlador.syncRelacionesInmuebles(a, progressBar, tvProgressBar, NuevaUnidadAguasActivity.relacionesInmuebles, () -> {
            if (NuevaUnidadAguasActivity.relacionesInmuebles.size() > 0) {
                abrirActivity(a, NuevaUnidadAguasActivity.class);
            }
            return null;
        });
    }

    public void abrirNuevaUnidadEdelar(Activity a, ProgressBar progressBar, TextView tvProgressBar) {
        NuevaUnidadEdelarActivity.relacionesInmuebles = new ArrayList<>();
        relacionInmuebleControlador.syncRelacionesInmuebles(a, progressBar, tvProgressBar, NuevaUnidadEdelarActivity.relacionesInmuebles, () -> {
            if (NuevaUnidadEdelarActivity.relacionesInmuebles.size() > 0) {
                abrirActivity(a, NuevaUnidadEdelarActivity.class);
            }
            return null;
        });
    }

    public void abrirEstadoCuenta(Activity a, ProgressBar progressBar, TextView tvProgressBar, int unidad) {
        EstadoCuentaActivity.comprobantes = new ArrayList<>();
        ComprobanteControlador comprobanteControlador = new ComprobanteControlador();
        comprobanteControlador.buscarComprobantes(a, progressBar, tvProgressBar, unidad, EstadoCuentaActivity.comprobantes, () -> {
            if (EstadoCuentaActivity.comprobantes.size() > 0) {
                Bundle mBundle = new Bundle();
                mBundle.putInt("unidad", unidad);
                abrirActivityWithBundle(a, EstadoCuentaActivity.class, mBundle);
            }
            return null;
        });
    }
}
