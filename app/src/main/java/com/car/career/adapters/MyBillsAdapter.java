package com.car.career.adapters;


import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.car.career.R;
import com.car.career.database.model.Car;
import com.github.vipulasri.timelineview.TimelineView;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;



    public class MyBillsAdapter extends RecyclerView.Adapter<MyBillsAdapter.TimeLineViewOrder> {

        private Context context;
        private List<Car> cars;

        public class TimeLineViewOrder extends RecyclerView.ViewHolder {
            public TextView date;
            public TextView carname;
            public TextView price;
            public TimelineView timeline;
            public TimeLineViewOrder(View view) {
                super(view);
                date=view.findViewById(R.id.date);
                carname=view.findViewById(R.id.carname);
                price=view.findViewById(R.id.price);
                timeline=view.findViewById(R.id.timeline);
            }
        }

        public MyBillsAdapter(Context context, List<Car> reminders) {
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

            Car car = cars.get(position);

            holder.carname.setText(car.getTitle());

            // Displaying dot from HTML character code

            // Formatting and displaying timestamp
            holder.date.setText(car.getCreatedAt());
            holder.price.setText(""+car.getYear());
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


