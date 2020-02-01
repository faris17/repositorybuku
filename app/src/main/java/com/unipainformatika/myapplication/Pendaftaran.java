package com.unipainformatika.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.unipainformatika.myapplication.model.Users;

public class Pendaftaran extends AppCompatActivity {
    EditText nama, email, password;
    Button daftar;

    private DatabaseReference Database;
    private FirebaseAuth Auth;
    private ProgressDialog Dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pendaftaran);

        FirebaseApp.initializeApp(this);

        Auth = FirebaseAuth.getInstance();
        Dialog = new ProgressDialog(this);

        nama = findViewById(R.id.namalengkap);
        email = findViewById(R.id.inputemail);
        password = findViewById(R.id.inputpassword);

        daftar = findViewById(R.id.btndaftar);

        daftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String getnama = nama.getText().toString().trim();
                final String getemail = email.getText().toString().trim();
                final String getpass = password.getText().toString().trim();

                //cek kosong apa tidak yang diambil
                if(TextUtils.isEmpty(getnama)){
                    nama.setError("Isi ini dulu toh...");
                    nama.requestFocus();
                    return ;
                }

                if(TextUtils.isEmpty(getemail) | !isValidEmail(getemail)){
                    email.setError("Ups ada kesalahan disini pace..");
                    email.requestFocus();
                    return;
                }
                if(getpass.length() < 6){
                    password.setError("Minimal 6 Karakter");
                    password.requestFocus();
                    return;
                }

                Dialog.setMessage("Sedang proses.");
                Dialog.show();

                //buat akun
                Auth.createUserWithEmailAndPassword(getemail, getpass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            FirebaseUser mUser = Auth.getCurrentUser();
                            String uId = mUser.getUid();
                            Database = FirebaseDatabase.getInstance().getReference().child("users").child(uId);
                            Database.keepSynced(true);
                            Users datainsert = new Users(getnama, getemail, getpass);

                            Database.setValue(datainsert);
                            Hapusfield();

                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
                            Toast.makeText(getApplicationContext(),"Berhasil",Toast.LENGTH_SHORT).show();
                            Dialog.dismiss();
                        }
                        else {
                            Toast.makeText(getApplicationContext(), "Terjadi Kesalahan", Toast.LENGTH_SHORT).show();
                            Dialog.dismiss();
                        }
                    }
                });
            }
        });
    }

    public void Hapusfield(){
        nama.getText().clear();
        email.getText().clear();
        password.getText().clear();
    }

    public static boolean isValidEmail(String email) {
        boolean validate;
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        String emailPattern2 = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+\\.+[a-z]+";

        if (email.matches(emailPattern)) {
            validate = true;
        } else if (email.matches(emailPattern2)) {
            validate = true;
        } else {
            validate = false;
        }

        return validate;
    }
}
