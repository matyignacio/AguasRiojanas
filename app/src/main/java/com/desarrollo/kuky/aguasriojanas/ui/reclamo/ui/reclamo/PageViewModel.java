package com.desarrollo.kuky.aguasriojanas.ui.reclamo.ui.reclamo;

import android.util.Log;

import com.desarrollo.kuky.aguasriojanas.objeto.reclamo.Reclamo;
import com.desarrollo.kuky.aguasriojanas.ui.reclamo.ReclamosActivity;

import java.util.ArrayList;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

public class PageViewModel extends ViewModel {

    private MutableLiveData<Integer> mIndex = new MutableLiveData<>();
    private LiveData<ArrayList<Reclamo>> mReclamos = Transformations.map(mIndex, (Integer input) -> {
        ArrayList<Reclamo> reclamos = new ArrayList<>();
        try {
            if (input < 2) { //Puede ser 0 o 1 si esta en el primer fragment
                for (int i = 0; i < ReclamosActivity.reclamos.size(); i++) {
                    if (!ReclamosActivity.reclamos.get(i).isFinalizado()) {
                        reclamos.add(ReclamosActivity.reclamos.get(i));
                    }
                }
            } else {
                for (int i = 0; i < ReclamosActivity.reclamos.size(); i++) {
                    if (ReclamosActivity.reclamos.get(i).isFinalizado()) {
                        reclamos.add(ReclamosActivity.reclamos.get(i));
                    }
                }
            }
        } catch (Exception e) {
            Log.e(this.getClass().getName(), e.toString());
        }

        return reclamos;
    });

    public LiveData<ArrayList<Reclamo>> getmReclamos() {
        return mReclamos;
    }

    public void setIndex(int index) {
        mIndex.setValue(index);
    }
}