package com.example.bus_pass;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class pendingApplications extends AppCompatActivity {
    private RecyclerView recyclerView2;
    private FirebaseDatabase dpPend = FirebaseDatabase.getInstance();
    private DatabaseReference rootPend = dpPend.getReference().child("pending");
    private MyAdapter2 adapter2;
    private ArrayList<Model2> list2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pending_applications);
        recyclerView2 = findViewById(R.id.recyclerViewer);
        recyclerView2.setHasFixedSize(true);
        recyclerView2.setLayoutManager(new LinearLayoutManager(this));
        list2 = new ArrayList<>();
        adapter2 = new MyAdapter2(list2,this);
        recyclerView2.setAdapter(adapter2);

        rootPend.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                    Model2 model2 = dataSnapshot.getValue(Model2.class);
                    list2.add(model2);
                }
                adapter2.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}