package com.unipainformatika.myapplication.adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.unipainformatika.myapplication.R;
import com.unipainformatika.myapplication.model.DataKP;

import java.util.ArrayList;

public class KP_sSatuAdapter extends RecyclerView.Adapter<KP_sSatuAdapter.MyViewHolder> {


private Context context;
private ArrayList<DataKP> datakp;

private ProgressDialog mDialog;

        //variable untuk firebase
        FirebaseDatabase database;
        DatabaseReference myRef;

public KP_sSatuAdapter(Context c, ArrayList<DataKP> p) {
        this.context = c;
        datakp = p;
        database = FirebaseDatabase.getInstance();
        }

public ArrayList<DataKP> getListMateri() {
        return datakp;
        }

public void setListDataKP(ArrayList<DataKP> datakp) {
        this.datakp = datakp;
        }

@NonNull
@Override
public KP_sSatuAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemRow = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_kp, viewGroup, false);
        mDialog = new ProgressDialog(context);
        return new KP_sSatuAdapter.MyViewHolder(itemRow);
        }

@Override
public void onBindViewHolder(@NonNull KP_sSatuAdapter.MyViewHolder holder, final int position) {
        holder.judul.setText(getListMateri().get(position).getJudul());
        }

@Override
public int getItemCount() {
        return datakp.size();
        }

public class MyViewHolder extends RecyclerView.ViewHolder {
    TextView judul;

    public MyViewHolder(@NonNull View itemView) {
        super(itemView);
        judul = (TextView) itemView.findViewById(R.id.judulkp);
    }
}

}