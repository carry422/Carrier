package com.car.career;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.car.career.adapters.MyBillsAdapter;
import com.car.career.database.DBHelper;
import com.car.career.database.model.Bill;

import java.util.ArrayList;

public class BillActivity extends AppCompatActivity {

    private DBHelper db;
    private RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_mybills);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }
    }
    protected void onResume() {
        super.onResume();

        ArrayList<Bill> p=new ArrayList<Bill>();
        db=DBHelper.getInstance(getApplicationContext());
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("settings", 0);

        Long d=sharedPreferences.getLong("currentCar",0);
        p.addAll(db.getBills(d));
        recyclerView=findViewById(R.id.recycler_view);
        MyBillsAdapter billsAdapter = new MyBillsAdapter(this, p);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(billsAdapter);
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return true;
    }
}
