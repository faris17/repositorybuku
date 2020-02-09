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
    final DataKP datakp =getListMateri().get(position);
    holder.judul.setText(getListMateri().get(position).getJudul());
    holder.studikasus.setText(getListMateri().get(position).getStudikasus());
    holder.abstrak.setText(getListMateri().get(position).getAbstrak());

    holder.detail.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent detail = new Intent(v.getContext(), DetailKP.class);
            detail.putExtra(DetailTugasAkhir.EXTRA_DATA, datakp);
            v.getContext().startActivity(detail);
        }
    });

    holder.download.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            downloadfile(getListMateri().get(position).getNamafile());
        }
    });
        }

@Override
public int getItemCount() {
        return datakp.size();
        }

public class MyViewHolder extends RecyclerView.ViewHolder {
    TextView judul, studikasus, abstrak, detail;
    ImageView download;

    public MyViewHolder(@NonNull View itemView) {
        super(itemView);
        judul = (TextView) itemView.findViewById(R.id.judulkp);
        studikasus = (TextView) itemView.findViewById(R.id.tvstudikasus);
        abstrak = (TextView) itemView.findViewById(R.id.tvabstrak);
        detail = (TextView) itemView.findViewById(R.id.tvdetail);
        download = (ImageView) itemView.findViewById(R.id.btndownload);
    }
}

    private void downloadfile(final String namafile) {

        FirebaseStorage storage = FirebaseStorage.getInstance();

        StorageReference storageRef = storage.getReferenceFromUrl(
                "gs://repositorybuku.appspot.com").child("kps1/"+namafile+".pdf");

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
    }

}