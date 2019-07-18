package com.car.career.mycars;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.car.career.R;
import com.car.career.database.model.Car;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;
import static com.car.career.mycars.MyCarsActivity.itemList;

class MyCars_Adapter extends BaseAdapter {
    private final Activity activity;
    private final LayoutInflater inflater;
    private SharedPreferences sharedPreferences;


    MyCars_Adapter(Activity activityP) {
        activity = activityP;
        inflater = (LayoutInflater) activityP.getSystemService(LAYOUT_INFLATER_SERVICE);
        sharedPreferences = activityP.getSharedPreferences("settings", 0);
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        View view;
        if (convertView!=null)
            view = convertView;
        else
            view = inflater.inflate(R.layout.activity_my_cars_row, parent, false);

        final Car car = itemList.get(position);

        TextView textView_title = view.findViewById(R.id.textView_title);
        textView_title.setText(car.getTitle());

        TextView textView_description = view.findViewById(R.id.textView_description);
        textView_description.setText(car.getModel());

        TextView textView_date = view.findViewById(R.id.textView_date);
        textView_date.setText(String.valueOf(car.getYear()));

        ImageView imageView = view.findViewById(R.id.imageView_check);
        if (sharedPreferences.getLong("currentCar", 1) == car.getId()) {
            imageView.setVisibility(View.VISIBLE);
        }
        else {
            imageView.setVisibility(View.INVISIBLE);
        }

        LinearLayout linearLayout_myCarsRow = view.findViewById(R.id.linearLayout_myCarsRow);
        linearLayout_myCarsRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putLong("currentCar", itemList.get(position).getId());
                editor.apply();

                notifyDataSetChanged();
            }
        });

        return view;
    }
    public Car getItem(int position) {
            return itemList.get(position);
    }
    public long getItemId(int position) {
        return position;
    }
    public int getCount() {
        return itemList.size();
    }

}
