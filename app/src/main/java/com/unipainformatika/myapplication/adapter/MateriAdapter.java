package com.unipainformatika.myapplication.adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.unipainformatika.myapplication.R;
import com.unipainformatika.myapplication.model.Materi;

import java.util.ArrayList;

public class MateriAdapter extends RecyclerView.Adapter<MateriAdapter.MyViewHolder> {


    private Context context;
    private ArrayList<Materi> materidata;

    private ProgressDialog mDialog;

    //variable untuk firebase
    FirebaseDatabase database;
    DatabaseReference myRef;

    public MateriAdapter(Context c, ArrayList<Materi> p) {
        this.context = c;
        materidata = p;
        database = FirebaseDatabase.getInstance();
    }

    public ArrayList<Materi> getListMateri() {
        return materidata;
    }

    public void setListMateri(ArrayList<Materi> databooks) {
        this.materidata = materidata;
    }

    @NonNull
    @Override
    public MateriAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemRow = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_materi, viewGroup, false);
        mDialog = new ProgressDialog(context);
        return new MateriAdapter.MyViewHolder(itemRow);
    }

    @Override
    public void onBindViewHolder(@NonNull MateriAdapter.MyViewHolder holder, final int position) {
        holder.judulbuku.setText(getListMateri().get(position).getJudulbuku());
    }

    @Override
    public int getItemCount() {
        return materidata.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView judulbuku;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            judulbuku = (TextView) itemView.findViewById(R.id.judulbuku);
        }
    }
}