package com.unipainformatika.myapplication.adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.unipainformatika.myapplication.DetailSkripsi;
import com.unipainformatika.myapplication.R;
import com.unipainformatika.myapplication.model.DataSkripsi;

import java.util.ArrayList;

public class SkripsiAdapter extends RecyclerView.Adapter<SkripsiAdapter.MyViewHolder> {

    private ArrayList<DataSkripsi> mData = new ArrayList<>() ;
    private Context context;

    private ProgressDialog mDialog;

    //variable untuk firebase
    FirebaseDatabase database;
    DatabaseReference myRef;

    public SkripsiAdapter(Context c, ArrayList<DataSkripsi> list) {
        this.context = c;
        this.mData=list;
        database = FirebaseDatabase.getInstance();
    }

    @NonNull
    @Override
    public SkripsiAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemRow = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_skripsi, viewGroup, false);
        mDialog = new ProgressDialog(context);
        return new SkripsiAdapter.MyViewHolder(itemRow);
    }

    @Override
    public void onBindViewHolder(@NonNull SkripsiAdapter.MyViewHolder holder, final int position) {
        final DataSkripsi dataskripsi =mData.get(position);
        holder.judulskripsi.setText(mData.get(position).getJudul());
        holder.studikasus.setText(mData.get(position).getStudikasus());
        holder.abstrak.setText(mData.get(position).getAbstrak());

        holder.detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent detail = new Intent(v.getContext(), DetailSkripsi.class);
                detail.putExtra(DetailSkripsi.EXTRA_DATA, dataskripsi);
                v.getContext().startActivity(detail);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView judulskripsi, studikasus, abstrak, detail;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            judulskripsi = (TextView) itemView.findViewById(R.id.judulskripsi);
            studikasus = (TextView) itemView.findViewById(R.id.tvstudikasus);
            abstrak = (TextView) itemView.findViewById(R.id.tvabstrak);
            detail = (TextView) itemView.findViewById(R.id.tvdetail);
        }
    }
}