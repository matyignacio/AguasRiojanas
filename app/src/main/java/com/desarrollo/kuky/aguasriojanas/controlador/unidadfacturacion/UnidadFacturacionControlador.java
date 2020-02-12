package com.desarrollo.kuky.aguasriojanas.controlador.unidadfacturacion;

import android.app.Activity;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.desarrollo.kuky.aguasriojanas.ui.unidadfacturacion.NuevaUnidadAguasActivity;
import com.desarrollo.kuky.aguasriojanas.ui.unidadfacturacion.NuevaUnidadEdelarActivity;

import java.util.ArrayList;

import static com.desarrollo.kuky.aguasriojanas.util.Util.abrirActivity;

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

}
