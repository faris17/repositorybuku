package com.unipainformatika.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.TextView;

import com.unipainformatika.myapplication.model.DataKP;
import com.unipainformatika.myapplication.model.DataSkripsi;

public class DetailKP extends AppCompatActivity {
    public static final String EXTRA_DATA = "extra_data";

    private ProgressDialog dialog;

    TextView judul, tanggal, studikasus, pembimbingsatu, pembimbingdua, abstrak;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_kp);

        dialog = new ProgressDialog(this);

        judul = findViewById(R.id.textkerjapraktek);
        studikasus = findViewById(R.id.textstudikasus);
        abstrak = findViewById(R.id.textAbstrak);
        pembimbingsatu = findViewById(R.id.textpembimbingsatu);
        pembimbingdua = findViewById(R.id.textpembimbingdua);

        dialog.setMessage("Sedang memuat data...");
        dialog.show();

        DataKP data = getIntent().getParcelableExtra(EXTRA_DATA);
        String getKP = data.getJudul();
        String getStudikasus = data.getStudikasus();
        String getAbstrak = data.getAbstrak();
        String getPembimbingsatu = data.getPembimbingsatu();
        String getPembimbingdua = data.getPembimbingdua();

        judul.setText(getKP);
        studikasus.setText(getStudikasus);
        abstrak.setText(getAbstrak);
        pembimbingsatu.setText(getPembimbingsatu);
        pembimbingdua.setText(getPembimbingdua);

        dialog.dismiss();
    }
}
