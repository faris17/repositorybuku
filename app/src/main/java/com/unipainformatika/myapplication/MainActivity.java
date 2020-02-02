package com.unipainformatika.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.unipainformatika.myapplication.helper.Session;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    LinearLayout skripsi, tugasakhir, materi, dosen, halregister, kp_d3, kp_s1;
    TextView register, keluar;
    Button login;

    private FirebaseAuth Auth;
    private DatabaseReference getChild;

    private String TAG = "MyActivity";

    private Session sharedPrefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPrefManager = new Session(this);

        materi = findViewById(R.id.btnmateri);
        skripsi = findViewById(R.id.btnskripsi);
        tugasakhir = findViewById(R.id.btntugasakhir);
        dosen = findViewById(R.id.btndosen);

        register = findViewById(R.id.tvregister);
        login = findViewById(R.id.btnLogin);
        keluar = findViewById(R.id.logout);
        halregister = findViewById(R.id.halamanregister);
        kp_d3 = findViewById(R.id.btnkpd3);
        kp_s1 = findViewById(R.id.btnkps1);

        Auth = FirebaseAuth.getInstance();
        if(Auth.getCurrentUser() != null){
            login.setVisibility(View.GONE);
            halregister.setVisibility(View.GONE);
            keluar.setVisibility(View.VISIBLE);
            cekLevel();
        }


        materi.setOnClickListener(this);
        skripsi.setOnClickListener(this);
        tugasakhir.setOnClickListener(this);
        kp_d3.setOnClickListener(this);
        kp_s1.setOnClickListener(this);
        dosen.setOnClickListener(this);

        register.setOnClickListener(this);
        login.setOnClickListener(this);
        keluar.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnmateri:
                Intent halmateri = new Intent(this, Materi.class);
                startActivity(halmateri);
                break;

            case R.id.btnskripsi:
                Intent halskripsi = new Intent(this, Skripsi.class);
                startActivity(halskripsi);
                break;

            case R.id.btntugasakhir:
                Intent haltugasakhir = new Intent(this, TugasAkhir.class);
                startActivity(haltugasakhir);
                break;

            case R.id.btnkpd3:
                Intent halkpd3 = new Intent(this, KP_Dtiga.class);
                startActivity(halkpd3);
                break;

            case R.id.btnkps1:
                Intent halkps1 = new Intent(this, KP_Ssatu.class);
                startActivity(halkps1);
                break;

            case R.id.btnLogin:
                Intent hallogin = new Intent(this, Login.class);
                startActivity(hallogin);
                break;

            case R.id.tvregister:
                Intent halregister = new Intent(this, Pendaftaran.class);
                startActivity(halregister);
                break;

            case R.id.logout:
                Auth.signOut();
                sharedPrefManager.HapusSession();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                break;
        }
    }

    public void cekLevel(){
        //untuk session

        //untuk firebase
        FirebaseApp.initializeApp(this);
        FirebaseUser currentU = FirebaseAuth.getInstance().getCurrentUser();
        String uId = currentU.getUid();

        //ambil child users
        getChild = FirebaseDatabase.getInstance().getReference().child("users").child(uId);
        getChild.keepSynced(true);

        //ambil namalengkap
        getChild.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String nim = (String) dataSnapshot.child("nim").getValue();
                String level = (String) dataSnapshot.child("kategori").getValue();

                sharedPrefManager.saveSPString(Session.key_nim, nim);
                sharedPrefManager.saveSPString(Session.key_level, level);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e(TAG, "Gagal mengambil data.");
            }
        });
    }
}
