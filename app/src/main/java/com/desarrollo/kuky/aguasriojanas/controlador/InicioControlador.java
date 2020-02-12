package com.desarrollo.kuky.aguasriojanas.controlador;

import android.app.Activity;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.desarrollo.kuky.aguasriojanas.controlador.unidadfacturacion.UnidadControlador;
import com.desarrollo.kuky.aguasriojanas.ui.datosutiles.DatosUtilesActivity;
import com.desarrollo.kuky.aguasriojanas.ui.unidadfacturacion.UnidadFacturacionActivity;

import java.util.ArrayList;

import static com.desarrollo.kuky.aguasriojanas.util.Util.abrirActivity;

public class InicioControlador {
    private DatosUtilesControlador datosUtilesControlador = new DatosUtilesControlador();

    public void abrirDatosUtilesActivity(Activity a, ProgressBar progressBar, TextView tvProgressBar) {
        datosUtilesControlador.syncDatosContacto(a, progressBar, tvProgressBar, () -> {
            datosUtilesControlador.syncOficinasComerciales(a, progressBar, tvProgressBar, () -> {
                datosUtilesControlador.syncLugaresPago(a, progressBar, tvProgressBar, () -> {
                    abrirActivity(a, DatosUtilesActivity.class);
                    return null;
                });
                return null;
            });
            return null;
        });
    }

    public void abrirUnidadFacturacionActivity(Activity a, ProgressBar progressBar, TextView tvProgressBar) {
        UnidadControlador unidadControlador = new UnidadControlador();
        UnidadFacturacionActivity.unidades = new ArrayList<>();
        unidadControlador.buscarUnidades(a, progressBar, tvProgressBar,
                UnidadFacturacionActivity.unidades, () -> {
                    abrirActivity(a, UnidadFacturacionActivity.class);
                    return null;
                });
    }
}
