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

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.unipainformatika.myapplication.DetailKP;
import com.unipainformatika.myapplication.DetailTugasAkhir;
import com.unipainformatika.myapplication.R;
import com.unipainformatika.myapplication.model.DataDosen;
import com.unipainformatika.myapplication.model.DataKP;

import java.util.ArrayList;

public class DosenAdapter extends RecyclerView.Adapter<DosenAdapter.MyViewHolder> {


    private Context context;
    private ArrayList<DataDosen> datadosen;

    private ProgressDialog mDialog;

    //variable untuk firebase
    FirebaseDatabase database;
    DatabaseReference myRef;

    public DosenAdapter(Context c, ArrayList<DataDosen> p) {
        this.context = c;
        datadosen = p;
        database = FirebaseDatabase.getInstance();
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
        View itemRow = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_kp, viewGroup, false);
        mDialog = new ProgressDialog(context);
        return new DosenAdapter.MyViewHolder(itemRow);
    }

    @Override
    public void onBindViewHolder(@NonNull DosenAdapter.MyViewHolder holder, final int position) {

        holder.nama.setText(getListDosen().get(position).getNamadosen());
        holder.nip.setText(getListDosen().get(position).getNip());
        holder.alamat.setText(getListDosen().get(position).getAlamat());

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



}