package com.car.career.mycars;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.car.career.R;
import com.car.career.database.DBHelper;
import com.car.career.database.model.Car;
import com.car.career.mycars.MyCars_Adapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class SpecialtiesActivity extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_setup);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }

    }
    static ArrayList<Car> itemList;
    private MyCars_Adapter adapter;
    protected void onResume() {
        super.onResume();
        DBHelper dbHelper = DBHelper.getInstance(getApplicationContext());
        final EditText arac_text = findViewById(R.id.arac_text);
        final EditText model_text = findViewById(R.id.model_text);
        final EditText yil_text = findViewById(R.id.yil_text);

        Button save = findViewById(R.id.save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (arac_text.getText().toString().equals("") || model_text.getText().toString().equals("") || yil_text.getText().toString().equals("")) {
                        Toast.makeText(getApplicationContext(), "Lütfen bütün bilgileri giriniz.", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    finish();
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

        Button cancel = findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



    }
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return true;
    }
}
