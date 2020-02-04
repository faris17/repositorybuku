package com.unipainformatika.myapplication.adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.util.Log;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.unipainformatika.myapplication.DetailSkripsi;
import com.unipainformatika.myapplication.R;
import com.unipainformatika.myapplication.model.DataSkripsi;

import java.io.File;
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

        holder.download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                downloadfile();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView judulskripsi, studikasus, abstrak, detail;
        ImageView download;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            judulskripsi = (TextView) itemView.findViewById(R.id.judulskripsi);
            studikasus = (TextView) itemView.findViewById(R.id.tvstudikasus);
            abstrak = (TextView) itemView.findViewById(R.id.tvabstrak);
            detail = (TextView) itemView.findViewById(R.id.tvdetail);
            download = (ImageView) itemView.findViewById(R.id.btndownload);
        }
    }


    private void downloadfile() {

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReferenceFromUrl("https://firebasestorage.googleapis.com/v0/b/imagestore-b2432.appspot.com/o/Nature.jpg?alt=media&token=07d95162-45f8-424e-9658-8f9022485930");

        final ProgressDialog  pd = new ProgressDialog(context);
        pd.setTitle("Nature.jpg");
        pd.setMessage("Downloading Please Wait!");
        pd.setIndeterminate(true);
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.show();


        final File rootPath = new File(Environment.getExternalStorageDirectory(), "MADBO DOWNLOADS");

        if (!rootPath.exists()) {
            rootPath.mkdirs();
        }


        final File localFile = new File(rootPath, "Nature.jpg");

        storageRef.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                Log.e("firebase ", ";local tem file created  created " + localFile.toString());

                if (localFile.canRead()){

                    pd.dismiss();
                }

                Toast.makeText(context, "Download Completed", Toast.LENGTH_SHORT).show();
                Toast.makeText(context, "Internal storage/MADBO/Nature.jpg", Toast.LENGTH_LONG).show();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Log.e("firebase ", ";local tem file not created  created " + exception.toString());
                Toast.makeText(context, "Download Incompleted", Toast.LENGTH_LONG).show();
            }
        });
    }
}