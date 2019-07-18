package com.car.career;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.car.career.database.DBHelper;
import com.car.career.database.model.Car;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class SpecialtiesFragment extends android.app.Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_specialties, container, false);


        DBHelper dbHelper = DBHelper.getInstance(getActivity().getApplicationContext());
        ArrayList<Car> arrayList = dbHelper.getCarList();

        StringBuilder text = new StringBuilder();
        for (int i = 0; i<arrayList.size(); i++) {
            text.append("Araç:  ").append(arrayList.get(i).getTitle())
                    .append("\nModel:  ").append(arrayList.get(i).getModel())
                    .append("\nYear:  ").append(arrayList.get(i).getYear())
                    .append("\n\n");
        }

        return view;
    }
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FloatingActionButton fab=(FloatingActionButton)getActivity().findViewById( R.id.addCar );
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
        Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle("Aracım");
    }
}
