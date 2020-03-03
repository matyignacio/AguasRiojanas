package com.desarrollo.kuky.aguasriojanas.ui.unidadfacturacion.ui.estadocuenta;

import android.util.Log;

import com.desarrollo.kuky.aguasriojanas.objeto.unidadfacturacion.Comprobante;
import com.desarrollo.kuky.aguasriojanas.ui.unidadfacturacion.EstadoCuentaActivity;

import java.util.ArrayList;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

public class PageViewModel extends ViewModel {

    private MutableLiveData<Integer> mIndex = new MutableLiveData<>();
    private LiveData<ArrayList<Comprobante>> mComprobantes = Transformations.map(mIndex, (Integer input) -> {
        ArrayList<Comprobante> comprobantes = new ArrayList<>();
        try {
            if (input < 2) { //Puede ser 0 o 1 si esta en el primer fragment
                comprobantes.add(EstadoCuentaActivity.comprobantes.get(0));
            } else {
                for (int i = 1; i < EstadoCuentaActivity.comprobantes.size(); i++) {
                    comprobantes.add(EstadoCuentaActivity.comprobantes.get(i));
                }
            }
        } catch (Exception e) {
            Log.e(this.getClass().getName(), e.toString());
        }

        return comprobantes;
    });

    public LiveData<ArrayList<Comprobante>> getComprobantes() {
        return mComprobantes;
    }

    public void setIndex(int index) {
        mIndex.setValue(index);
    }
}