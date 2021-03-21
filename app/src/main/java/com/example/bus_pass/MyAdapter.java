package com.example.bus_pass;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    ArrayList<Model> mList;
    Context context;

    public MyAdapter(Context context,ArrayList<Model> mList){
        this.mList = mList;
        this.context = context;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Model model = mList.get(position);
        holder.busno.setText(model.getBusno());
        holder.busnumber.setText(model.getBusnumber());
        holder.drivername.setText(model.getDrivername());
        holder.drivernumber.setText(model.getDrivernumber());
        holder.route.setText(model.getRoute());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static class MyViewHolder extends  RecyclerView.ViewHolder{

        TextView busno,busnumber,drivername,drivernumber,route;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            busno = itemView.findViewById(R.id.busNos);
            busnumber = itemView.findViewById(R.id.busNumbers);
            drivername = itemView.findViewById(R.id.driverNames);
            drivernumber = itemView.findViewById(R.id.driverNumbers);
            route = itemView.findViewById(R.id.busRoutes);
        }
    }
}
