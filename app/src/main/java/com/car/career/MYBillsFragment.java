package com.car.career;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toolbar;

import com.car.career.adapters.MyBillsAdapter;
import com.car.career.database.DBHelper;
import com.car.career.database.model.Car;

import java.util.ArrayList;


public class MYBillsFragment extends android.app.Fragment {

    private DBHelper db;
    private RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mybills, container, false);

        return view;
    }
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
        //toolbar.setTitle("Faturalarım");
        ArrayList<Car> p=new ArrayList<>();
        db=DBHelper.getInstance(getActivity().getApplicationContext());
        p.addAll(db.getCarList());
        recyclerView=getActivity().findViewById(R.id.recycler_view);
        MyBillsAdapter billsAdapter = new MyBillsAdapter(getActivity(), p);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity().getApplicationContext(), LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(billsAdapter);
    }


}
