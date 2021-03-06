package com.unipainformatika.myapplication.adapter;

import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.unipainformatika.myapplication.DetailKP;
import com.unipainformatika.myapplication.DetailTugasAkhir;
import com.unipainformatika.myapplication.R;
import com.unipainformatika.myapplication.helper.Session;
import com.unipainformatika.myapplication.model.DataDosen;
import com.unipainformatika.myapplication.model.DataKP;

import java.util.ArrayList;

public class DosenAdapter extends RecyclerView.Adapter<DosenAdapter.MyViewHolder> {


    private Context context;
    private ArrayList<DataDosen> datadosen;

    private ProgressDialog mDialog;

    //variable untuk firebase storage
    private StorageReference reference;

    //variable untuk firebase
    FirebaseDatabase database;
    DatabaseReference myRef;

    public DosenAdapter(Context c, ArrayList<DataDosen> p) {
        this.context = c;
        datadosen = p;
        database = FirebaseDatabase.getInstance();
        reference = FirebaseStorage.getInstance().getReference();
    }

    public ArrayList<DataDosen> getListDosen() {
        return datadosen;
    }

    public void setListDataDosen(ArrayList<DataDosen> datadosen) {
        this.datadosen = datadosen;
    }

    @NonNull
    @Override
    public DosenAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemRow = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_dosen, viewGroup, false);
        mDialog = new ProgressDialog(context);
        return new DosenAdapter.MyViewHolder(itemRow);
    }

    @Override
    public void onBindViewHolder(@NonNull DosenAdapter.MyViewHolder holder, final int position) {

        holder.nama.setText(getListDosen().get(position).getNamadosen());
        holder.nip.setText(getListDosen().get(position).getNip());
        holder.alamat.setText(getListDosen().get(position).getAlamat());

        if(getListDosen().get(position).getFoto() != "-"){
            getImage("dosen/"+getListDosen().get(position).getFoto()+".jpg", holder.foto);
        }
    }

    @Override
    public int getItemCount() {
        return datadosen.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView nama, nip, alamat;
        ImageView foto;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            nama = (TextView) itemView.findViewById(R.id.namadosen);
            nip = (TextView) itemView.findViewById(R.id.nipdosen);
            alamat = (TextView) itemView.findViewById(R.id.alamat);
            foto = (ImageView) itemView.findViewById(R.id.profiledosen);
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