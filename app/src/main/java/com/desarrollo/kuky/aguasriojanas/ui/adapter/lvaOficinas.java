package com.desarrollo.kuky.aguasriojanas.ui.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.desarrollo.kuky.aguasriojanas.R;

import java.util.ArrayList;

import objeto.OficinaComercial;

import static util.Util.setPrimaryFontBold;

public class lvaOficinas extends BaseAdapter {
    // Declare Variables
    private Context context;
    private ArrayList<OficinaComercial> oficinas;

    public lvaOficinas(Context context, ArrayList<OficinaComercial> oficinas) {
        this.context = context;
        this.oficinas = oficinas;
    }

    @Override
    public int getCount() {
        return oficinas.size();
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
        TextView tvLocalidad, tvDireccion, tvHorario;

        //http://developer.android.com/intl/es/reference/android/view/LayoutInflater.html
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View itemView = inflater.inflate(R.layout.sli_oficinas_comerciales, parent, false);

        // Locate the TextViews in listview_item.xml
        tvLocalidad = itemView.findViewById(R.id.tvLocalidad);
        tvDireccion = itemView.findViewById(R.id.tvDireccion);
        tvHorario = itemView.findViewById(R.id.tvHorario);
        // Capture position and set to the TextViews
        /** SETEAMOS LOS TYPEFACES*/
        setPrimaryFontBold(context, tvLocalidad);
        setPrimaryFontBold(context, tvDireccion);
        setPrimaryFontBold(context, tvHorario);
        /**************************/
        tvLocalidad.setText(oficinas.get(position).getLocalidad());
        tvDireccion.setText(oficinas.get(position).getDireccion());
        tvHorario.setText(oficinas.get(position).getHorarioDesde()
                + "hs - "
                + oficinas.get(position).getHorarioHasta() + "hs");

        return itemView;
    }
}
