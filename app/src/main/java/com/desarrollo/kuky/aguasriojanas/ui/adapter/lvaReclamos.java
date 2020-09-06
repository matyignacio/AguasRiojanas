package com.desarrollo.kuky.aguasriojanas.ui.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.desarrollo.kuky.aguasriojanas.R;
import com.desarrollo.kuky.aguasriojanas.objeto.reclamo.Reclamo;
import com.desarrollo.kuky.aguasriojanas.util.Util;

import java.util.ArrayList;

import static com.desarrollo.kuky.aguasriojanas.ui.reclamo.ReclamosActivity.tiposReclamos;
import static com.desarrollo.kuky.aguasriojanas.util.Util.setPrimaryFontBold;

public class lvaReclamos extends BaseAdapter {
    // Declare Variables
    private Activity context;
    private ArrayList<Reclamo> reclamos;

    public lvaReclamos(Activity context, ArrayList<Reclamo> reclamos) {
        this.context = context;
        this.reclamos = reclamos;
    }

    @Override
    public int getCount() {
        return reclamos.size();
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
        TextView tvFecha, etTipoReclamo, etDireccion;

        //http://developer.android.com/intl/es/reference/android/view/LayoutInflater.html
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View itemView = inflater.inflate(R.layout.sli_reclamos, parent, false);

        // Locate the TextViews in listview_item.xml
        tvFecha = itemView.findViewById(R.id.tvFecha);
        etTipoReclamo = itemView.findViewById(R.id.etTipoReclamo);
        etDireccion = itemView.findViewById(R.id.etDireccion);
        // Capture position and set to the TextViews
        /** SETEAMOS LOS TYPEFACES*/
        setPrimaryFontBold(context, tvFecha);
        setPrimaryFontBold(context, etTipoReclamo);
        setPrimaryFontBold(context, etDireccion);
        /**************************/
        tvFecha.setText(Util.convertirFecha(reclamos.get(position).getFecha()));
        for (int i = 0; i < tiposReclamos.size(); i++) {
            if (reclamos.get(position).getIdTipoReclamo() ==
                    tiposReclamos.get(i).getId()) {
                etTipoReclamo.setText(
                        tiposReclamos.get(i).getNombre()
                );
            }
        }
        etDireccion.setText(reclamos.get(position).getDireccion());
        return itemView;
    }
}
