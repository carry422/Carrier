package com.car.career;


import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.app.AlertDialog;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;


public class ControlPanelFragment extends android.app.Fragment {
    private FuelActivity fa;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_control_panel, container, false);
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FloatingActionButton fab=(FloatingActionButton)getActivity().findViewById( R.id.searchfuel );
        fab.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
                fa=new FuelActivity();
                long plaka=0;
                String sehir;
                LayoutInflater layoutInflaterAndroid = LayoutInflater.from(getContext());
                View view2 = layoutInflaterAndroid.inflate(R.layout.check_fuel_money, null);

                AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(getContext());
                alertDialogBuilderUserInput.setView(view2);

                final EditText inputSehir = view2.findViewById(R.id.editText2);
                final EditText inputplaka = view2.findViewById(R.id.editText3);


                alertDialogBuilderUserInput
                        .setCancelable(false)
                        .setPositiveButton("Ara", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogBox, int id) {
                                try {
                                    ArrayList<Double> list=fa.execute((Long)Long.parseLong(inputplaka.getText().toString())).get();
                                    LayoutInflater layoutInflaterAndroid2 = LayoutInflater.from(getContext());
                                    View view3 = layoutInflaterAndroid2.inflate(R.layout.show_fuel_money, null);
                                    AlertDialog.Builder alertDialogBuilderUserInput2 = new AlertDialog.Builder(getContext());
                                    alertDialogBuilderUserInput2.setView(view3);
                                    TextView t0=view3.findViewById(R.id.textView20);
                                    t0.setText(inputSehir.getText().toString());
                                    TextView t1=view3.findViewById(R.id.textView18);
                                    t1.setText(inputSehir.getText().toString());
                                    TextView t2=view3.findViewById(R.id.textView17);
                                    t2.setText(""+list.get(0));
                                    TextView t3=view3.findViewById(R.id.textView16);
                                    t3.setText(""+list.get(1));
                                    TextView t4=view3.findViewById(R.id.textView15);
                                    t4.setText(""+list.get(2));
                                    TextView t5=view3.findViewById(R.id.textView14);
                                    t5.setText(""+list.get(3));
                                    TextView t6=view3.findViewById(R.id.textView13);
                                    t6.setText(""+list.get(4));
                                    TextView t7=view3.findViewById(R.id.textView12);
                                    t7.setText(""+list.get(5));
                                    TextView t8=view3.findViewById(R.id.textView11);
                                    t8.setText(""+list.get(6));
                                    TextView t9=view3.findViewById(R.id.textView10);
                                    t9.setText(""+list.get(7));

                                    final AlertDialog alertDialog = alertDialogBuilderUserInput2.create();
                                    alertDialog.show();
                                } catch (ExecutionException e) {
                                    e.printStackTrace();
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        })
                        .setNegativeButton("iptal",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialogBox, int id) {
                                        dialogBox.cancel();
                                    }
                                });

                final AlertDialog alertDialog = alertDialogBuilderUserInput.create();
                alertDialog.show();


            }
        });
    }
}
