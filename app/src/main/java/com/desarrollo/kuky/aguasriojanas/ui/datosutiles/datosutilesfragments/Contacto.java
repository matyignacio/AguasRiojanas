package com.desarrollo.kuky.aguasriojanas.ui.datosutiles.datosutilesfragments;

import android.Manifest;
import android.app.Fragment;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.desarrollo.kuky.aguasriojanas.R;
import com.desarrollo.kuky.aguasriojanas.controlador.datosutiles.DatosUtilesControlador;
import com.desarrollo.kuky.aguasriojanas.objeto.datosutiles.DatoContacto;
import com.desarrollo.kuky.aguasriojanas.ui.datosutiles.WebActivity;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import static com.desarrollo.kuky.aguasriojanas.util.Util.abrirActivity;
import static com.desarrollo.kuky.aguasriojanas.util.Util.mostrarMensaje;
import static com.desarrollo.kuky.aguasriojanas.util.Util.setPrimaryFontBold;

public class Contacto extends Fragment {

    Button bTelefono, bWeb, bCorreo;
    DatoContacto datoContacto = new DatoContacto();
    DatosUtilesControlador datosUtilesControlador = new DatosUtilesControlador();
    final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 0;

    public Contacto() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_contacto, container, false);

        // Nuevos parametros para el view del fragmento
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT);
        // Nueva Regla: EL fragmento estara debajo del menu navigation
        //params.addRule(RelativeLayout.ABOVE, R.id.navigation);
        // Margenes: top:15dp
        params.setMargins(0, 15, 0, 15);
        // Setear los parametros al view
        view.setLayoutParams(params);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        bTelefono = getActivity().findViewById(R.id.tvTelefono);
        bCorreo = getActivity().findViewById(R.id.tvCorreo);
        bWeb = getActivity().findViewById(R.id.tvWeb);
        /*** SETEAMOS TYPEFACES ***********************************/
        setPrimaryFontBold(getActivity(), bCorreo);
        setPrimaryFontBold(getActivity(), bWeb);
        setPrimaryFontBold(getActivity(), bTelefono);
        /**********************************************************/
        datoContacto = datosUtilesControlador.getDatosContacto(getActivity());
        bTelefono.setText(datoContacto.getTelefono());
        bWeb.setText(datoContacto.getWeb());
        bCorreo.setText(datoContacto.getCorreo());
        ////////////////////////////////////////////////////////////
        bTelefono.setOnClickListener(v -> {
            solicitarPermisosLlamada();
        });
        bWeb.setOnClickListener(v -> {
            abrirActivity(getActivity(), WebActivity.class);
        });
        bCorreo.setOnClickListener(v -> {
            try {
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", datoContacto.getCorreo(), null));
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Consulta");
                startActivity(Intent.createChooser(emailIntent, "Enviar mail"));
            } catch (Exception e) {
                mostrarMensaje(getActivity(), e.toString());
            }
        });


    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_CALL_PHONE: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    callPhone();
                } else {
                    solicitarPermisosLlamada();
                    System.out.println("El usuario ha rechazado el permiso");
                }
                return;
            }
        }
    }

    public void callPhone() {
        Intent i = new Intent(android.content.Intent.ACTION_CALL,
                Uri.parse("tel:" + datoContacto.getTelefono()));
        startActivity(i);
    }

    private void solicitarPermisosLlamada() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (ContextCompat.checkSelfPermission(getActivity(),
                    Manifest.permission.CALL_PHONE)
                    != PackageManager.PERMISSION_GRANTED) {
//                if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
//                        Manifest.permission.CALL_PHONE)) {
                /** ACA DEBERIA MOSTRAR UN DIALOGO QUE EXPLIQUE EL PORQUE DEL PERMISO*/
//                } else {
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.CALL_PHONE},
                        MY_PERMISSIONS_REQUEST_CALL_PHONE);
//                }
            } else {
                callPhone();
            }
        }
    }
}
