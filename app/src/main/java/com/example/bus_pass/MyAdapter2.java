package com.example.bus_pass;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter2 extends RecyclerView.Adapter<MyAdapter2.MyViewHolder2> {
    ArrayList<Model2> mList2;
    Context context2;

    public MyAdapter2(ArrayList<Model2> mList2, Context context2) {
        this.mList2 = mList2;
        this.context2 = context2;
    }

    @NonNull
    @Override
    public MyViewHolder2 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context2).inflate(R.layout.item2,parent,false);
        return new MyAdapter2.MyViewHolder2(v);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder2 holder, int position) {
        Model2 model2 = mList2.get(position);
        holder.amt.setText(model2.getAmount());
        holder.passi.setText(model2.getPassId());
        holder.rolln.setText(model2.getRollno());
        holder.stati.setText(model2.getStatus());
        holder.typi.setText(model2.getType());
        holder.validi.setText(model2.getValid());
    }

    @Override
    public int getItemCount() {
        return mList2.size();
    }


    public static class MyViewHolder2 extends  RecyclerView.ViewHolder{

        TextView amt,passi,rolln,stati,typi,validi;

        public MyViewHolder2(@NonNull View itemView) {
            super(itemView);
            amt = itemView.findViewById(R.id.amounts);
            passi = itemView.findViewById(R.id.passids);
            rolln = itemView.findViewById(R.id.rolls);
            stati = itemView.findViewById(R.id.statuss);
            typi = itemView.findViewById(R.id.types);
            validi  = itemView.findViewById(R.id.valids);
        }
    }
}
