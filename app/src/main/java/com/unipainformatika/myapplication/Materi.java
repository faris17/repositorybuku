package com.unipainformatika.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.unipainformatika.myapplication.adapter.MateriAdapter;

import java.util.ArrayList;

public class Materi extends AppCompatActivity {
    private RecyclerView Recycler;
    private LinearLayoutManager mManager;

    private DatabaseReference Database;

    ArrayList<com.unipainformatika.myapplication.model.Materi> list;
    MateriAdapter adapter;
    private ProgressDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_materi);

        Database = FirebaseDatabase.getInstance().getReference().child("materi");

        Recycler = findViewById(R.id.list_materi);
        Recycler.setHasFixedSize(true);

        mManager = new LinearLayoutManager(this);
        mManager.setReverseLayout(true);
        mManager.setStackFromEnd(true);
        Recycler.setLayoutManager(mManager);

        Database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list = new ArrayList<>();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    com.unipainformatika.myapplication.model.Materi p = dataSnapshot1.getValue(com.unipainformatika.myapplication.model.Materi.class);
                    p.setId(dataSnapshot1.getKey());
                    list.add(p);
                }
                adapter = new MateriAdapter(getApplicationContext(),list);
                Recycler.setAdapter(adapter);
//                mDialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), "Terjadi kesalahan.", Toast.LENGTH_SHORT).show();
            }

        });
    }
}
