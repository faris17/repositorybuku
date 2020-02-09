package com.unipainformatika.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.unipainformatika.myapplication.helper.Session;
import com.unipainformatika.myapplication.model.DataProfil;

public class Profile extends AppCompatActivity implements View.OnClickListener {
    ImageView foto;
    TextView nama, nim, kategori, nohp, editprofil;

    private FirebaseAuth Auth;
    private DatabaseReference getChild;

    private String TAG = "MyActivity";
    private Context context;

    private StorageReference reference;
    private String dnim;

    DataProfil profil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        reference = FirebaseStorage.getInstance().getReference();
        Auth = FirebaseAuth.getInstance();
        String uId = Auth.getCurrentUser().getUid();

        foto = findViewById(R.id.view_image);
        nama = findViewById(R.id.profile_namalengkap);
        nim = findViewById(R.id.profil_nim);
        kategori = findViewById(R.id.profil_gender);
        nohp = findViewById(R.id.profil_nomorhp);
        editprofil = findViewById(R.id.editprofile);


        getChild = FirebaseDatabase.getInstance().getReference().child("users").child(uId);
        getChild.keepSynced(true);

        editprofil.setOnClickListener(this);

        //ambil namalengkap
        getChild.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                dnim = (String) dataSnapshot.child("nim").getValue();
                String dnamalengkap = (String) dataSnapshot.child("namalengkap").getValue();
                String dgender = (String) dataSnapshot.child("gender").getValue();
                String dnohp = (String) dataSnapshot.child("nohp").getValue();

                nim.setText(dnim);
                nama.setText(dnamalengkap);
                if(dgender.equals("pria"))
                    kategori.setText("Pria");
                else
                    kategori.setText("Wanita");
                nohp.setText(dnohp);

                profil = new DataProfil(dnamalengkap, dnim, dnohp, dgender);

                showProfil(dnim);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e(TAG, "Gagal mengambil data.");
            }
        });




    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.editprofile:
                Intent halformprofil = new Intent(this, Form_Profil.class);
                halformprofil.putExtra("DATAPROFIL",profil);
                startActivity(halformprofil);
                break;
        }
    }

    public void showProfil(String photo){
        reference.child("profile/"+photo+".jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {

                Toast.makeText(getApplicationContext(),"sukses",Toast.LENGTH_SHORT).show();

                // Got the download URL for 'users/me/profile.png'
                Glide.with(getBaseContext())
                        .load(uri)
                        .into(foto);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
                Toast.makeText(getApplicationContext(),dnim,Toast.LENGTH_SHORT).show();
            }
        });
    }
}
