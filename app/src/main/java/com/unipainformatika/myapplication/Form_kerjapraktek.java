package com.unipainformatika.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.unipainformatika.myapplication.helper.Session;
import com.unipainformatika.myapplication.model.DataSkripsi;

import java.io.File;
import java.util.Calendar;

public class Form_kerjapraktek extends AppCompatActivity implements View.OnClickListener{
    //Declaring views
    private Button buttonChoose, buttonSave;
    private EditText pdfname,tanggal,judulta, abstrak, studikasus, pembimbingsatu, pembimbingdua;

    //Uri to store the image uri
    private Uri filePath;

    //Image request code
    private int PICK_PDF_REQUEST = 1;

    DatePickerDialog picker;

    //storage permission code
    private static final int STORAGE_PERMISSION_CODE = 123;

    //Firebase
    StorageReference storageReference;
    DatabaseReference Database;

    private Session sharedPrefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_kerjapraktek);

        //Requesting storage permission
        requestStoragePermission();
        storageReference = FirebaseStorage.getInstance().getReference();
        sharedPrefManager = new Session(this);

        //Initializing views
        buttonChoose = (Button) findViewById(R.id.buttonChoose);
        buttonSave = (Button) findViewById(R.id.buttonUpload);
        pdfname = (EditText) findViewById(R.id.textpdfname);
        tanggal = (EditText) findViewById(R.id.edittanggal);

        judulta = findViewById(R.id.judulkerjapraktek);
        abstrak = findViewById(R.id.abstrak);
        studikasus = findViewById(R.id.studikasus);
        pembimbingsatu = findViewById(R.id.pembimbingsatu);
        pembimbingdua = findViewById(R.id.pembimbingdua);

        buttonChoose.setOnClickListener(this);
        buttonSave.setOnClickListener(this);
        tanggal.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.edittanggal:
                getTanggal();
                break;
            case R.id.buttonChoose:
                showFileChooser();
                break;

            case R.id.buttonUpload:
                saveDataInputan();
                break;
        }
    }

    //ambil calender
    private void getTanggal(){
        final Calendar cldr = Calendar.getInstance();
        int day = cldr.get(Calendar.DAY_OF_MONTH);
        int month = cldr.get(Calendar.MONTH);
        int year = cldr.get(Calendar.YEAR);
        // date picker dialog
        picker = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        tanggal.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                    }
                }, year, month, day);
        picker.show();
    }


    //method to show file chooser
    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Pdf"), PICK_PDF_REQUEST);
    }

    //handling the ima chooser activity result
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_PDF_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
            String uriString = filePath.toString();
            File myFile = new File(uriString);
            String path = myFile.getAbsolutePath();
            String displayName = null;

            if (uriString.startsWith("content://")) {
                Cursor cursor = null;
                try {
                    cursor = getApplicationContext().getContentResolver().query(filePath, null, null, null, null);
                    if (cursor != null && cursor.moveToFirst()) {
                        displayName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                        pdfname.setText(displayName);
                    }
                } finally {
                    cursor.close();
                }
            } else if (uriString.startsWith("file://")) {
                displayName = myFile.getName();
            }

        }
    }


    //Requesting permission
    private void requestStoragePermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
            return;

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            //If the user has denied the permission previously your code will come to this block
            //Here you can explain why you need this permission
            //Explain here why you need this permission
        }
        //And finally ask for the permission
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
    }

    //This method will be called when the user will tap on allow or deny
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        //Checking the request code of our request
        if (requestCode == STORAGE_PERMISSION_CODE) {

            //If permission is granted
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //Displaying a toast
                Toast.makeText(this, "Permission granted now you can read the storage", Toast.LENGTH_LONG).show();
            } else {
                //Displaying another toast if permission is not granted
            }
        }
    }

    public void saveDataInputan(){
        //mengambil data
        String getJudul = judulta.getText().toString().trim();
        String getAbstrak = abstrak.getText().toString().trim();
        String getStudikasus = studikasus.getText().toString().trim();
        String getPembimbingsatu = pembimbingsatu.getText().toString().trim();
        String getPembimbingdua = pembimbingdua.getText().toString().trim();
        String getTanggal = tanggal.getText().toString().trim();
        String nim = "kp_"+sharedPrefManager.getSes_nim();

        Database = FirebaseDatabase.getInstance().getReference().child("buku").child("kerjapraktek").child(nim);

        DataSkripsi setSkripsi = new DataSkripsi(getJudul, getStudikasus, getAbstrak, getPembimbingsatu, getPembimbingdua, getTanggal, nim);
        Database.setValue(setSkripsi);

        if(filePath != null)
        {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            StorageReference ref = storageReference.child("kerjapraktek/"+nim+".jpg");
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
    }

    public void resetField(){
        tanggal.getText().clear();
        judulta.getText().clear();
        abstrak.getText().clear();
        studikasus.getText().clear();
        pembimbingsatu.getText().clear();
        pembimbingdua.getText().clear();
        pdfname.getText().clear();
        filePath= null;
    }
}
