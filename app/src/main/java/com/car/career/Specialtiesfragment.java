package com.car.career;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.car.career.database.DBHelper;
import com.car.career.database.model.Car;

import java.util.ArrayList;

public class Specialtiesfragment extends android.app.Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_specialties, container, false);

        final TextView tv1 = view.findViewById(R.id.tv1);
        final TextView tv2 = view.findViewById(R.id.tv2);
        final TextView tv3 = view.findViewById(R.id.tv3);

        DBHelper dbHelper = DBHelper.getInstance(getActivity().getApplicationContext());
        ArrayList<Car> arrayList = dbHelper.getCarList();

        tv1.setText("Araç:  " + arrayList.get(0).getTitle());
        tv2.setText("Model:  " + arrayList.get(0).getModel());
        tv3.setText("Year:  " + arrayList.get(0).getYear());

        return view;
    }
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle("Araç Yükleme Ekranı");
    }
}
