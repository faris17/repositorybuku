package com.unipainformatika.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.unipainformatika.myapplication.model.DataKP;
import com.unipainformatika.myapplication.model.DataProfil;

import java.io.IOException;
import java.util.UUID;

public class Form_Profil extends AppCompatActivity implements View.OnClickListener{
    Button Pilih, Simpan, update;
    ImageView imageView;
    EditText nama, editnim, nohp;
    RadioButton male, female;
    RadioGroup kategorigender;

    String idkey;
    private Uri filePath;

    private final int PICK_IMAGE_REQUEST = 71;

    //Firebase
    StorageReference storageReference;
    DatabaseReference mDatabase;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form__profil);

        nama = findViewById(R.id.namalengkap);
        editnim = findViewById(R.id.nim);
        nohp = findViewById(R.id.nomorhp);

        Pilih = findViewById(R.id.btnChoose);
        Simpan = findViewById(R.id.btnSave);
        update = findViewById(R.id.btnUpdate);
        imageView = findViewById(R.id.imgView);
        kategorigender = findViewById(R.id.kateg_gender);
        male = findViewById(R.id.pria);
        female = findViewById(R.id.wanita);

//      FirebaseApp.initializeApp(this);

        storageReference = FirebaseStorage.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();

        Pilih.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.btnChoose:
                chooseImage();
                break;

            case R.id.btnSave:
                savedata();
                break;

            case R.id.btnUpdate:
                savedata();
                break;
        }
    }

    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null )
        {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                imageView.setImageBitmap(bitmap);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    //proses upload image
    private void savedata() {


        //ambil data
        String inputnama = nama.getText().toString().trim();
        String inputnim = editnim.getText().toString().trim();
        String inputnohp = nohp.getText().toString().trim();


        int kateg = kategorigender.getCheckedRadioButtonId();
        String dstatus;

        mDatabase = FirebaseDatabase.getInstance().getReference().child("users").child(idkey);

        if(kateg == male.getId()){
            dstatus = "pria";
        }
        else {
            dstatus = "wanita";
        }

        DataProfil insert = new DataProfil(inputnama, inputnim,inputnohp,dstatus);
        mDatabase.setValue(insert);

        if(filePath != null)
        {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            StorageReference ref = storageReference.child("profil/"+ inputnim+".jpg");
            ref.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), "Uploaded", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot
                                    .getTotalByteCount());
                            progressDialog.setMessage("Uploaded "+(int)progress+"%");
                        }
                    });
        }
        hapusfield();
    }


    private void hapusfield(){
        nama.getText().clear();
        editnim.getText().clear();
        nohp.getText().clear();
        male.setChecked(true);
    }

    public void getImage(String data, final ImageView foto){
        storageReference.child(data).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess( Uri uri) {

                // Got the download URL for 'users/me/profile.png'
                Glide.with(getApplicationContext())
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
