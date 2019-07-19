package com.car.career.adapters;


import android.content.Context;
import android.content.SharedPreferences;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.car.career.R;
import com.car.career.database.DBHelper;
import com.car.career.database.model.Bill;
import com.car.career.database.model.Car;
import com.github.vipulasri.timelineview.TimelineView;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;



    public class MyBillsAdapter extends RecyclerView.Adapter<MyBillsAdapter.TimeLineViewOrder> {

        private Context context;
        private List<Bill> cars;

        public class TimeLineViewOrder extends RecyclerView.ViewHolder {
            public TextView date;
            public TextView carname;
            public TextView yakit;
            public TextView price;
            public TextView kurum;

            public TimelineView timeline;
            public TimeLineViewOrder(View view) {
                super(view);
                carname=view.findViewById(R.id.carname);
                yakit=view.findViewById(R.id.yakit);
                date=view.findViewById(R.id.date);
                kurum=view.findViewById(R.id.kurum);
                price=view.findViewById(R.id.price);
                timeline=view.findViewById(R.id.timeline);
            }
        }

        public MyBillsAdapter(Context context, List<Bill> reminders) {
            this.context = context;
            this.cars = reminders;
        }

        @Override
        public TimeLineViewOrder onCreateViewHolder(ViewGroup parent, int viewType) {
            View reminderView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.row_bills, parent, false);

            return new TimeLineViewOrder(reminderView);
        }

        @Override
        public void onBindViewHolder(TimeLineViewOrder holder, int position) {
            holder.timeline.initLine(0);

            Bill car = cars.get(position);
            DBHelper db=DBHelper.getInstance(context);
            SharedPreferences sharedPreferences = context.getSharedPreferences("settings", 0);

            Long d=sharedPreferences.getLong("currentCar",0);
            holder.carname.setText("Arac:"+db.getCar(d).getTitle());
            holder.kurum.setText("Kurum:"+car.getKurum());
            holder.date.setText("Tarih:"+car.getDATE());
            holder.yakit.setText("Alınan Litre:"+car.getLitre()+"");
            holder.price.setText("Tutar:"+car.getCOST());
        }
        private String formatDate(String dateStr) {
            try {
                SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = fmt.parse(dateStr);
                SimpleDateFormat fmtOut = new SimpleDateFormat("d MMM yyyy - HH:mm:ss");
                return fmtOut.format(date);
            } catch (ParseException e) {

            }

            return "";
        }

        @Override
        public int getItemCount() {
            return cars.size();
        }

    }


