package com.desarrollo.kuky.aguasriojanas.controlador;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.desarrollo.kuky.aguasriojanas.controlador.reclamo.ReclamoControlador;
import com.desarrollo.kuky.aguasriojanas.controlador.reclamo.TipoReclamoControlador;
import com.desarrollo.kuky.aguasriojanas.controlador.reclamo.UbicacionReclamoControlador;
import com.desarrollo.kuky.aguasriojanas.controlador.unidadfacturacion.UnidadControlador;
import com.desarrollo.kuky.aguasriojanas.ui.datosutiles.DatosUtilesActivity;
import com.desarrollo.kuky.aguasriojanas.ui.reclamo.ReclamosActivity;
import com.desarrollo.kuky.aguasriojanas.ui.unidadfacturacion.UnidadFacturacionActivity;

import java.util.ArrayList;

import static com.desarrollo.kuky.aguasriojanas.ui.reclamo.ReclamosActivity.reclamos;
import static com.desarrollo.kuky.aguasriojanas.ui.reclamo.ReclamosActivity.tiposReclamos;
import static com.desarrollo.kuky.aguasriojanas.ui.reclamo.ReclamosActivity.ubicacionReclamos;
import static com.desarrollo.kuky.aguasriojanas.util.Util.abrirActivity;
import static com.desarrollo.kuky.aguasriojanas.util.Util.mostrarMensaje;

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

    public void abrirReclamoActivity(Activity a, ProgressBar progressBar, TextView tvProgressBar) {
        ReclamosActivity.unidades = new ArrayList<>();
        tiposReclamos = new ArrayList<>();
        ubicacionReclamos = new ArrayList<>();
        reclamos = new ArrayList<>();
        UnidadControlador unidadControlador = new UnidadControlador();
        ReclamoControlador reclamoControlador = new ReclamoControlador();
        TipoReclamoControlador tipoReclamoControlador = new TipoReclamoControlador();
        UbicacionReclamoControlador ubicacionReclamoControlador = new UbicacionReclamoControlador();
        unidadControlador.buscarUnidades(a, progressBar, tvProgressBar,
                ReclamosActivity.unidades, () -> {
                    if (ReclamosActivity.unidades.size() > 0) {
                        tipoReclamoControlador.buscarTiposReclamo(a, progressBar, tvProgressBar, tiposReclamos, () -> {
                            if (tiposReclamos.size() > 0) {
                                ubicacionReclamoControlador.buscarUbicacionesReclamo(a, progressBar, tvProgressBar,
                                        ubicacionReclamos, () -> {
                                            if (ubicacionReclamos.size() > 0) {
                                                reclamoControlador.buscarReclamos(a, progressBar, tvProgressBar,
                                                        reclamos, () -> {
                                                            abrirActivity(a, ReclamosActivity.class);
                                                            return null;
                                                        });
                                            }
                                            return null;
                                        });
                            }
                            return null;
                        });
                    } else {
                        mostrarMensaje(a, "Para poder realizar reclamos, debe asignar al menos una unidad.");
                    }
                    return null;
                });
    }

    public void actualizarTablas(Activity a) {
        try {
            SQLiteDatabase db = BaseHelper.getInstance(a).getWritableDatabase();
            /** DROPEAMOS PARA QUE SE CREEN */
            db.execSQL(BaseHelper.getInstance(a).dropTable("lugares_pago"));
            db.execSQL(BaseHelper.getInstance(a).dropTable("lugares_pago"));
            db.execSQL(BaseHelper.getInstance(a).dropTable("datos_contacto"));
            /** Y AHORA LAS VOLVEMOS A CREAR CON FORMATO DEFINITIVO */
            db.execSQL(BaseHelper.getInstance(a).getSqlTablaDatosContacto());
            db.execSQL(BaseHelper.getInstance(a).getSqlTablaLugaresPago());
            db.execSQL(BaseHelper.getInstance(a).getSqlTablaOficinasComerciales());
            db.close();
        } catch (Exception e) {
            mostrarMensaje(a, e.toString());
        }
    }
}
