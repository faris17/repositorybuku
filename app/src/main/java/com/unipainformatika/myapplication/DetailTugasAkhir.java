package com.unipainformatika.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.TextView;

import com.unipainformatika.myapplication.model.DataSkripsi;

public class DetailTugasAkhir extends AppCompatActivity {
    public static final String EXTRA_DATA = "extra_data";
    public static final String EXTRA_STUDIKASUS = "studikasus";
    public static final String EXTRA_ABSTRAK = "abstrak";
    public static final String EXTRA_PEMBIMBINGSATU = "pembimbingsatu";
    public static final String EXTRA_PEMBIMBINGDUA = "pembimbingdua";
    public static final String EXTRA_TANGGAL= "tanggal";

    private ProgressDialog dialog;

    TextView judul, tanggal, studikasus, pembimbingsatu, pembimbingdua, abstrak;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_tugas_akhir);

        dialog = new ProgressDialog(this);

        judul = findViewById(R.id.textSkripsi);
        studikasus = findViewById(R.id.textstudikasus);
        abstrak = findViewById(R.id.textAbstrak);
        pembimbingsatu = findViewById(R.id.textpembimbingsatu);
        pembimbingdua = findViewById(R.id.textpembimbingdua);
        tanggal = findViewById(R.id.tv_tanggal);

        dialog.setMessage("Sedang memuat data...");
        dialog.show();

        DataSkripsi data = getIntent().getParcelableExtra(EXTRA_DATA);
        String getSkripsi = data.getJudul();
        String getStudikasus = data.getStudikasus();
        String getAbstrak = data.getAbstrak();
        String getPembimbingsatu = data.getPembimbingsatu();
        String getPembimbingdua = data.getPembimbingdua();
        String getTanggal = data.getTanggal();

        judul.setText(getSkripsi);
        studikasus.setText(getStudikasus);
        abstrak.setText(getAbstrak);
        pembimbingsatu.setText(getPembimbingsatu);
        pembimbingdua.setText(getPembimbingdua);
        tanggal.setText(getTanggal);

        dialog.dismiss();
    }
}
