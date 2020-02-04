package com.unipainformatika.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.unipainformatika.myapplication.adapter.SkripsiAdapter;
import com.unipainformatika.myapplication.adapter.TugasAkhirAdapter;
import com.unipainformatika.myapplication.model.DataSkripsi;
import com.unipainformatika.myapplication.model.DataTugasAkhir;

import java.util.ArrayList;

public class TugasAkhir extends AppCompatActivity {

    private DatabaseReference Database;
    private ProgressDialog Dialog;
    TugasAkhirAdapter adapter;
    ArrayList<DataTugasAkhir> list;

    private RecyclerView Recycler;
    private LinearLayoutManager mManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tugas_akhir);
        setTitle("Tugas Akhir");

        FloatingActionButton btnAdd = findViewById(R.id.add);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Form_tugasakhir.class));
            }
        });

        Dialog = new ProgressDialog(this);

        Database = FirebaseDatabase.getInstance().getReference().child("buku").child("tugasakhir");

        Recycler = findViewById(R.id.list_tugasakhir);
        Recycler.setHasFixedSize(true);

        mManager = new LinearLayoutManager(this);
        mManager.setReverseLayout(true);
        mManager.setStackFromEnd(true);
        Recycler.setLayoutManager(mManager);

        Dialog.setMessage("Sedang memuat data");
        Dialog.show();

        Database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list = new ArrayList<>();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    DataTugasAkhir p = dataSnapshot1.getValue(DataTugasAkhir.class);
                    list.add(p);
                }
                adapter = new TugasAkhirAdapter(getApplicationContext(),list);
                Recycler.setAdapter(adapter);
                Dialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), "Terjadi kesalahan.", Toast.LENGTH_SHORT).show();
            }

        });
    }
}
