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

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.unipainformatika.myapplication.DetailTugasAkhir;
import com.unipainformatika.myapplication.R;
import com.unipainformatika.myapplication.model.DataTugasAkhir;

import java.util.ArrayList;

public class TugasAkhirAdapter extends RecyclerView.Adapter<TugasAkhirAdapter.MyViewHolder> {

private ArrayList<DataTugasAkhir> mData = new ArrayList<>() ;
private Context context;

private ProgressDialog mDialog;

        //variable untuk firebase
        FirebaseDatabase database;
        DatabaseReference myRef;

public TugasAkhirAdapter(Context c, ArrayList<DataTugasAkhir> list) {
        this.context = c;
        this.mData=list;
        database = FirebaseDatabase.getInstance();
        }

@NonNull
@Override
public TugasAkhirAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemRow = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_tugasakhir, viewGroup, false);
        mDialog = new ProgressDialog(context);
        return new TugasAkhirAdapter.MyViewHolder(itemRow);
        }

@Override
public void onBindViewHolder(@NonNull TugasAkhirAdapter.MyViewHolder holder, final int position) {
final DataTugasAkhir datata =mData.get(position);
        holder.judulskripsi.setText(mData.get(position).getJudul());
        holder.studikasus.setText(mData.get(position).getStudikasus());
        holder.abstrak.setText(mData.get(position).getAbstrak());

        holder.detail.setOnClickListener(new View.OnClickListener() {
@Override
public void onClick(View v) {
        Intent detail = new Intent(v.getContext(), DetailTugasAkhir.class);
        detail.putExtra(DetailTugasAkhir.EXTRA_DATA, datata);
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

        judulskripsi = (TextView) itemView.findViewById(R.id.judultugasakhir);
        studikasus = (TextView) itemView.findViewById(R.id.tvstudikasus);
        abstrak = (TextView) itemView.findViewById(R.id.tvabstrak);
        detail = (TextView) itemView.findViewById(R.id.tvdetail);
    }
}
}