package com.desarrollo.kuky.aguasriojanas.ui.unidadfacturacion.ui.estadocuenta;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.desarrollo.kuky.aguasriojanas.R;
import com.desarrollo.kuky.aguasriojanas.objeto.unidadfacturacion.Comprobante;
import com.desarrollo.kuky.aguasriojanas.ui.adapter.lvaComprobantes;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

/**
 * A placeholder fragment containing a simple view.
 */
public class PlaceholderFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";

    private PageViewModel pageViewModel;
    private int index = 0;

    static PlaceholderFragment newInstance(int index) {
        PlaceholderFragment fragment = new PlaceholderFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageViewModel = ViewModelProviders.of(this).get(PageViewModel.class);
        if (getArguments() != null) {
            index = getArguments().getInt(ARG_SECTION_NUMBER);
        }
        pageViewModel.setIndex(index);
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_estado_cuenta, container, false);
        ListView lvComprobantes = root.findViewById(R.id.lvComprobantes);
        pageViewModel.getComprobantes().observe(this, (ArrayList<Comprobante> comprobantes) -> {
            lvaComprobantes adaptador = new lvaComprobantes(this.getActivity(), comprobantes);
            lvComprobantes.setAdapter(adaptador);
        });
        return root;
    }
}