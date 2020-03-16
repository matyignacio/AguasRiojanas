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
import com.desarrollo.kuky.aguasriojanas.objeto.notificaciones.Notificacion;
import com.desarrollo.kuky.aguasriojanas.util.Util;

import java.util.ArrayList;

import static com.desarrollo.kuky.aguasriojanas.util.Util.setPrimaryFontBold;

public class lvaNotificaciones extends BaseAdapter {
    // Declare Variables
    private Activity context;
    private ArrayList<Notificacion> notificaciones;

    public lvaNotificaciones(Activity context, ArrayList<Notificacion> notificaciones) {
        this.context = context;
        this.notificaciones = notificaciones;
    }

    @Override
    public int getCount() {
        return notificaciones.size();
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
        TextView tvFecha, tvResumen;

        //http://developer.android.com/intl/es/reference/android/view/LayoutInflater.html
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View itemView = inflater.inflate(R.layout.sli_notificaciones, parent, false);

        // Locate the TextViews in listview_item.xml
        tvFecha = itemView.findViewById(R.id.tvFecha);
        tvResumen = itemView.findViewById(R.id.tvResumen);
        // Capture position and set to the TextViews
        /** SETEAMOS LOS TYPEFACES*/
        setPrimaryFontBold(context, tvFecha);
        setPrimaryFontBold(context, tvResumen);
        /**************************/
        tvFecha.setText(Util.convertirFecha(notificaciones.get(position).getFecha()));
        tvResumen.setText(notificaciones.get(position).getResumen());

        return itemView;
    }
}
