package com.car.career.mycars;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.car.career.R;
import com.car.career.database.DBHelper;
import com.car.career.database.model.Car;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MyCarsActivity extends AppCompatActivity {
    static ArrayList<Car> itemList;
    private MyCars_Adapter adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_cars);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }

        FloatingActionButton floatingActionButton = findViewById(R.id.floatingButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddCarDialog();
            }
        });
    }
    protected void onResume() {
        super.onResume();

        itemList = DBHelper.getInstance(this).getCarList();
        adapter = new MyCars_Adapter(this);

        ListView listView = findViewById(R.id.listView);
        listView.setAdapter(adapter);
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }


    private void showAddCarDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.setCancelable(true);
        final LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        final View view = inflater.inflate(R.layout.dialog_setup, (ViewGroup) findViewById(R.id.constraint));

        final EditText arac_text = view.findViewById(R.id.arac_text);
        final EditText model_text = view.findViewById(R.id.model_text);
        final EditText yil_text = view.findViewById(R.id.yil_text);

        Button save = view.findViewById(R.id.save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (arac_text.getText().toString().equals("") || model_text.getText().toString().equals("") || yil_text.getText().toString().equals("")) {
                        Toast.makeText(getApplicationContext(), "Lütfen bütün bilgileri giriniz.", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    dialog.dismiss();

                    DBHelper dbHelper = DBHelper.getInstance(getApplicationContext());
                    dbHelper.insertCar(arac_text.getText().toString(), model_text.getText().toString(), Long.parseLong(yil_text.getText().toString()));

                    itemList = dbHelper.getCarList();
                    adapter.notifyDataSetChanged();

                    Toast.makeText(getApplicationContext(), "Aracınız başarıyla eklendi.", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "Hatalı giriş: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        Button cancel = view.findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.setContentView(view);
        dialog.show();
    }
}
