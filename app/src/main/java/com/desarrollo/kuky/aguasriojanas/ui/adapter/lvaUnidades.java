package com.desarrollo.kuky.aguasriojanas.ui.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.desarrollo.kuky.aguasriojanas.R;
import com.desarrollo.kuky.aguasriojanas.controlador.InicioControlador;
import com.desarrollo.kuky.aguasriojanas.controlador.unidadfacturacion.UnidadControlador;
import com.desarrollo.kuky.aguasriojanas.objeto.unidadfacturacion.Unidad;
import com.desarrollo.kuky.aguasriojanas.util.Util;

import java.util.ArrayList;

import static com.desarrollo.kuky.aguasriojanas.util.Util.setPrimaryFontBold;

public class lvaUnidades extends BaseAdapter {
    // Declare Variables
    private Activity context;
    private ArrayList<Unidad> unidades;
    private ProgressBar progressBar;
    private TextView tvProgressBar;

    public lvaUnidades(Activity context, ArrayList<Unidad> unidades, ProgressBar progressBar, TextView tvProgressBar) {
        this.context = context;
        this.unidades = unidades;
        this.progressBar = progressBar;
        this.tvProgressBar = tvProgressBar;
    }

    @Override
    public int getCount() {
        return unidades.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @SuppressLint("SetTextI18n")
    public View getView(int position, View convertView, ViewGroup parent) {

        // Declare Variables
        TextView tvUnidad, tvRazon, tvDireccion;
        ImageButton bEliminar;

        //http://developer.android.com/intl/es/reference/android/view/LayoutInflater.html
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View itemView = inflater.inflate(R.layout.sli_unidades, parent, false);

        // Locate the TextViews in listview_item.xml
        tvUnidad = itemView.findViewById(R.id.tvUnidad);
        tvRazon = itemView.findViewById(R.id.tvRazon);
        tvDireccion = itemView.findViewById(R.id.tvDireccion);
        bEliminar = itemView.findViewById(R.id.bEliminar);
        // Capture position and set to the TextViews
        /** SETEAMOS LOS TYPEFACES*/
        setPrimaryFontBold(context, tvUnidad);
        setPrimaryFontBold(context, tvRazon);
        setPrimaryFontBold(context, tvDireccion);
        /**************************/
        tvUnidad.setText(String.valueOf(unidades.get(position).getUnidad()));
        tvRazon.setText(unidades.get(position).getNombre());
        tvDireccion.setText(unidades.get(position).getCalle()
                + " Nº"
                + unidades.get(position).getNumeroCasa());
        bEliminar.setOnClickListener(view -> Util.createCustomDialog(context, "ATENCION!",
                "Esta seguro que desea eliminar esta unidad?",
                "SI, ELIMINAR",
                "CANCELAR",
                // mensajeSi
                () -> {
                    UnidadControlador unidadControlador = new UnidadControlador();
                    unidadControlador.eliminarUnidad(context, progressBar, tvProgressBar,
                            unidades.get(position).getUnidad(), () -> {
                                InicioControlador inicioControlador = new InicioControlador();
                                inicioControlador.abrirUnidadFacturacionActivity(context, progressBar, tvProgressBar);
                                return null;
                            });
//                    );
                    return null;
                },
                // mensajeNo
                () -> null).show());

        return itemView;
    }
}
