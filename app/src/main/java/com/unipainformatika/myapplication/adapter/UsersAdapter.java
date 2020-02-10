package com.unipainformatika.myapplication.adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.unipainformatika.myapplication.R;
import com.unipainformatika.myapplication.model.DataProfil;

import java.util.ArrayList;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.MyViewHolder> {


    private Context context;
    private ArrayList<DataProfil> datamhs;

    private ProgressDialog mDialog;

    //variable untuk firebase storage
    private StorageReference reference;

    //variable untuk firebase
    FirebaseDatabase database;
    DatabaseReference myRef;

    public UsersAdapter(Context c, ArrayList<DataProfil> p) {
        this.context = c;
        datamhs = p;
        database = FirebaseDatabase.getInstance();
        reference = FirebaseStorage.getInstance().getReference();
    }

    public ArrayList<DataProfil> getListDosen() {
        return datamhs;
    }

    public void setListDataDosen(ArrayList<DataProfil> datadosen) {
        this.datamhs = datamhs;
    }

    @NonNull
    @Override
    public UsersAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemRow = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_users, viewGroup, false);
        mDialog = new ProgressDialog(context);
        return new UsersAdapter.MyViewHolder(itemRow);
    }

    @Override
    public void onBindViewHolder(@NonNull UsersAdapter.MyViewHolder holder, final int position) {

        holder.nama.setText(getListDosen().get(position).getNamalengkap());
        holder.nim.setText(getListDosen().get(position).getNim());
        holder.jurus.setText(getListDosen().get(position).getJurusan());

        if(getListDosen().get(position).getStatus() =="enable"){
            holder.enable.setVisibility(View.GONE);
            holder.disable.setVisibility(View.VISIBLE);
        }
        else {
            holder.enable.setVisibility(View.VISIBLE);
            holder.disable.setVisibility(View.GONE);
        }

        if(getListDosen().get(position).getNim() != "-"){
            getImage("mahasiswa/"+getListDosen().get(position).getNim()+".jpg", holder.foto);
        }
    }

    @Override
    public int getItemCount() {
        return datamhs.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView nama, nim, jurus, enable, disable;
        ImageView foto;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            nama = (TextView) itemView.findViewById(R.id.namamahasiswa);
            nim = (TextView) itemView.findViewById(R.id.nimmahasiswa);
            jurus = (TextView) itemView.findViewById(R.id.jurusan);
            enable = (TextView) itemView.findViewById(R.id.tvEnable);
            disable = (TextView) itemView.findViewById(R.id.tvDisable);
        }
    }

    public void getImage(String data, final ImageView foto) {
        reference.child(data).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                // Got the download URL for 'users/me/profile.png'
                Glide.with(context)
                        .load(uri)
                        .into(foto);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
            }
        });
    }



}