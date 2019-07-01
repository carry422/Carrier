package com.car.career;

import android.app.Activity;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.car.career.database.DBHelper;

public class SetupFragment extends android.app.Fragment {
    private Activity activity;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setup, container, false);

        activity = getActivity();

        final TextView arac_text = view.findViewById(R.id.arac_text);
        final TextView model_text = view.findViewById(R.id.model_text);
        final TextView yil_text = view.findViewById(R.id.yil_text);

        Button save = view.findViewById(R.id.save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getFragmentManager().beginTransaction().remove(SetupFragment.this).commit();

                DBHelper dbHelper = DBHelper.getInstance(getActivity().getApplicationContext());
                dbHelper.insertCar(arac_text.getText().toString(), model_text.getText().toString(), Long.parseLong(yil_text.getText().toString()));
            }
        });

        return view;
    }
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle("Araç Yükleme Ekranı");
    }

    @Override
    public void onStop() {
        super.onStop();

        if (activity instanceof MainActivity)
            ((MainActivity) activity).openSpecialtiesFragment();
    }
}
