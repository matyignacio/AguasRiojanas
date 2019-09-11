package com.desarrollo.kuky.aguasriojanas.ui.DatosUtilesFragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.desarrollo.kuky.aguasriojanas.R;

import controlador.DatosUtilesControlador;
import objeto.DatoContacto;

import static util.Util.setPrimaryFontBold;

public class Contacto extends Fragment {

    TextView tvTelefono, tvWeb, tvCorreo;

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
        tvTelefono = getActivity().findViewById(R.id.tvTelefono);
        tvCorreo = getActivity().findViewById(R.id.tvCorreo);
        tvWeb = getActivity().findViewById(R.id.tvWeb);
        /*** SETEAMOS TYPEFACES ***********************************/
        setPrimaryFontBold(getActivity(), tvCorreo);
        setPrimaryFontBold(getActivity(), tvWeb);
        setPrimaryFontBold(getActivity(), tvTelefono);
        /**********************************************************/
        DatoContacto datoContacto = new DatoContacto();
        DatosUtilesControlador datosUtilesControlador = new DatosUtilesControlador();
        datoContacto = datosUtilesControlador.getDatosContacto(getActivity());
        tvTelefono.setText(datoContacto.getTelefono());
        tvWeb.setText(datoContacto.getWeb());
        tvCorreo.setText(datoContacto.getCorreo());

    }
}
