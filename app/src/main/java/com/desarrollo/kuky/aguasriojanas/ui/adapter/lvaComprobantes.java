package com.desarrollo.kuky.aguasriojanas.ui.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.desarrollo.kuky.aguasriojanas.R;
import com.desarrollo.kuky.aguasriojanas.objeto.unidadfacturacion.Comprobante;

import java.text.DecimalFormat;
import java.util.ArrayList;

import static com.desarrollo.kuky.aguasriojanas.util.Util.formatearFechaString;
import static com.desarrollo.kuky.aguasriojanas.util.Util.mostrarMensaje;
import static com.desarrollo.kuky.aguasriojanas.util.Util.setPrimaryFontBold;

public class lvaComprobantes extends BaseAdapter {
    // Declare Variables
    private Activity context;
    private ArrayList<Comprobante> comprobantes;
    private DecimalFormat formatea = new DecimalFormat("###,###.##");

    public lvaComprobantes(Activity context, ArrayList<Comprobante> comprobantes) {
        this.context = context;
        this.comprobantes = comprobantes;
    }

    @Override
    public int getCount() {
        return comprobantes.size();
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
        TextView tvComprobante, tvPeriodo1, tvVencimiento1, tvImporte, tvVencimiento2, tvImporte2;
        Button bImprimirBoleta;

        //http://developer.android.com/intl/es/reference/android/view/LayoutInflater.html
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View itemView = inflater.inflate(R.layout.sli_comprobantes, parent, false);

        // Locate the TextViews in listview_item.xml
        tvComprobante = itemView.findViewById(R.id.tvComprobante);
        tvPeriodo1 = itemView.findViewById(R.id.tvPeriodo1);
        tvVencimiento1 = itemView.findViewById(R.id.tvVencimiento1);
        tvImporte = itemView.findViewById(R.id.tvImporte);
        tvVencimiento2 = itemView.findViewById(R.id.tvVencimiento2);
        tvImporte2 = itemView.findViewById(R.id.tvImporte2);
        bImprimirBoleta = itemView.findViewById(R.id.bImprimirBoleta);
        // Capture position and set to the TextViews
        /** SETEAMOS LOS TYPEFACES*/
        setPrimaryFontBold(context, tvComprobante);
        setPrimaryFontBold(context, tvPeriodo1);
        setPrimaryFontBold(context, tvVencimiento1);
        setPrimaryFontBold(context, tvImporte);
        setPrimaryFontBold(context, tvVencimiento2);
        setPrimaryFontBold(context, tvImporte2);
        setPrimaryFontBold(context, bImprimirBoleta);
        /**************************/
        tvComprobante.setText(comprobantes.get(position).getTipoComprobante() + " " +
                comprobantes.get(position).getPrenumeracion() + " " +
                comprobantes.get(position).getNumeroComprobante());
        tvPeriodo1.setText(comprobantes.get(position).getPeriodo());
        tvVencimiento1.setText(
                formatearFechaString(comprobantes.get(position).getVencimiento1().substring(0, 10)));
        tvImporte.setText("$ " + formatea.format(comprobantes.get(position).getImporte()));
        tvVencimiento2.setText(
                formatearFechaString(comprobantes.get(position).getVencimiento2().substring(0, 10)));
        tvImporte2.setText("$ " + formatea.format(comprobantes.get(position).getImporte() +
                comprobantes.get(position).getRecargo() +
                comprobantes.get(position).getRecargoIva()));
        bImprimirBoleta.setOnClickListener(v -> {
            if (comprobantes.get(position).isImprimible()) {
                // ACA LA LOGICA PARA IMPRIMIR LA BOLETA.
                Uri uriUrl = Uri.parse("http://volley.aguasriojanas.com.ar/presionaguas/aguasriojanas/imprimir/imprimirfactura.php?tpo_com=");
                Intent launchBrowser = new Intent(Intent.ACTION_VIEW, Uri.parse(uriUrl + comprobantes.get(position).getTipoComprobante()
                        + "&pre_com=" + comprobantes.get(position).getPrenumeracion()
                        + "&num_com=" + comprobantes.get(position).getNumeroComprobante()));
                context.startActivity(launchBrowser);
            } else {
                mostrarMensaje(context, "Ud posee una boleta unificada.");
                mostrarMensaje(context, "Para poder imprimir esta boleta debera dirigirse a EDELaR.");
            }
        });
        return itemView;
    }
}
