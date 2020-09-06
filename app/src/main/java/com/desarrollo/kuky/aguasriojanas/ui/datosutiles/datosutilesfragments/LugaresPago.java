package com.desarrollo.kuky.aguasriojanas.ui.datosutiles.datosutilesfragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.desarrollo.kuky.aguasriojanas.R;
import com.desarrollo.kuky.aguasriojanas.controlador.datosutiles.DatosUtilesControlador;
import com.desarrollo.kuky.aguasriojanas.ui.adapter.lvaLugaresPago;

public class LugaresPago extends Fragment {

    DatosUtilesControlador datosUtilesControlador = new DatosUtilesControlador();

    public LugaresPago() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_lugares_pago, container, false);

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
        ListView lvLugares = getActivity().findViewById(R.id.lvLugares);
        lvaLugaresPago adaptador = new lvaLugaresPago(getActivity(), datosUtilesControlador.getLugaresPago(getActivity()));
        lvLugares.setAdapter(adaptador);
        /*** SETEAMOS TYPEFACES ***********************************/

        /**********************************************************/


    }
}
