package com.desarrollo.kuky.aguasriojanas.ui.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.desarrollo.kuky.aguasriojanas.R;
import com.desarrollo.kuky.aguasriojanas.objeto.LugarPago;

import java.util.ArrayList;

import static com.desarrollo.kuky.aguasriojanas.util.Util.setPrimaryFontBold;

public class lvaLugaresPago extends BaseAdapter {
    // Declare Variables
    private Context context;
    private ArrayList<LugarPago> lugaresPago;

    public lvaLugaresPago(Context context, ArrayList<LugarPago> lugaresPago) {
        this.context = context;
        this.lugaresPago = lugaresPago;
    }

    @Override
    public int getCount() {
        return lugaresPago.size();
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
        TextView tvTitulo, tvDescripcion;

        //http://developer.android.com/intl/es/reference/android/view/LayoutInflater.html
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View itemView = inflater.inflate(R.layout.sli_lugares_pago, parent, false);

        // Locate the TextViews in listview_item.xml
        tvTitulo = itemView.findViewById(R.id.tvTitulo);
        tvDescripcion = itemView.findViewById(R.id.tvDescripcion);
        // Capture position and set to the TextViews
        /** SETEAMOS LOS TYPEFACES*/
        setPrimaryFontBold(context, tvTitulo);
        setPrimaryFontBold(context, tvDescripcion);
        /**************************/
        tvTitulo.setText(lugaresPago.get(position).getTitulo());
        tvDescripcion.setText(lugaresPago.get(position).getDescripcion());

        return itemView;
    }
}
