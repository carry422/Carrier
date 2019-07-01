package com.car.career;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.car.career.database.DBHelper;
import com.car.career.database.model.Car;

import java.util.ArrayList;

public class SpecialtiesFragment extends android.app.Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_specialties, container, false);

        final TextView tv1 = view.findViewById(R.id.tv1);

        DBHelper dbHelper = DBHelper.getInstance(getActivity().getApplicationContext());
        ArrayList<Car> arrayList = dbHelper.getCarList();

        StringBuilder text = new StringBuilder();
        for (int i = 0; i<arrayList.size(); i++) {
            text.append("Araç:  ").append(arrayList.get(0).getTitle())
                    .append("\nModel:  ").append(arrayList.get(0).getModel())
                    .append("\nYear:  ").append(arrayList.get(0).getYear())
                    .append("\n\n");
        }
        tv1.setText(text.toString());

        return view;
    }
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle("Araç Yükleme Ekranı");
    }
}
