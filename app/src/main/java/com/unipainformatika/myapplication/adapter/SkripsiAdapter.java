package com.unipainformatika.myapplication.adapter;

import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
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
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.unipainformatika.myapplication.DetailSkripsi;
import com.unipainformatika.myapplication.R;
import com.unipainformatika.myapplication.helper.Helper;
import com.unipainformatika.myapplication.helper.Session;
import com.unipainformatika.myapplication.model.DataSkripsi;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

import static androidx.core.content.ContextCompat.getSystemService;

public class SkripsiAdapter extends RecyclerView.Adapter<SkripsiAdapter.MyViewHolder> {

    private ArrayList<DataSkripsi> mData = new ArrayList<>() ;
    private Context context;

    private ProgressDialog mDialog;

    Session sharedPrefManager;

    //variable untuk firebase
    FirebaseDatabase database;
    DatabaseReference myRef;

    public SkripsiAdapter(Context c, ArrayList<DataSkripsi> list) {
        this.context = c;
        this.mData=list;
        database = FirebaseDatabase.getInstance();
        sharedPrefManager = new Session(c);
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
                downloadfile(mData.get(position).getNamafile());
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


    private void downloadfile(final String namafile) {

        FirebaseStorage storage = FirebaseStorage.getInstance();

        StorageReference storageRef = storage.getReferenceFromUrl(
                "gs://repositorybuku.appspot.com").child("skripsi/"+namafile+".pdf");

        storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
//                    String downloadUri = uri.toString();
                DownloadManager downloadmanager = (DownloadManager)context.
                        getSystemService(Context.DOWNLOAD_SERVICE);

                DownloadManager.Request request = new DownloadManager.Request(uri);
                request.setTitle("SKRIPSI "+namafile);
                request.setDescription("Downloading");
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                request.setDestinationInExternalFilesDir(context, "Images", "Faris.pdf");

                request.setVisibleInDownloadsUi(false);

                downloadmanager.enqueue(request);
                Toast.makeText(context, "berhasil", Toast.LENGTH_SHORT).show();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Toast.makeText(context, "gagal " + namafile, Toast.LENGTH_SHORT).show();
            }
        });
        //        storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//            @Override
//            public void onSuccess(Uri uri) {
//                Toast.makeText(context, uri.toString(), Toast.LENGTH_LONG).show();
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception exception) {
//                Toast.makeText(context, exception.toString(), Toast.LENGTH_LONG).show();
//            }
//        });

    }
}