package com.unipainformatika.myapplication.model;

import android.os.Parcel;
import android.os.Parcelable;

public class DataDosen implements Parcelable {
    String namadosen, nip, alamat, foto;

    public DataDosen(){}

    public DataDosen(String namadosen, String nip, String alamat, String foto) {
        this.namadosen = namadosen;
        this.nip = nip;
        this.alamat = alamat;
        this.foto = foto;
    }

    public String getNamadosen() {
        return namadosen;
    }

    public void setNamadosen(String namadosen) {
        this.namadosen = namadosen;
    }

    public String getNip() {
        return nip;
    }

    public void setNip(String nip) {
        this.nip = nip;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.namadosen);
        dest.writeString(this.nip);
        dest.writeString(this.alamat);
        dest.writeString(this.foto);
    }

    protected DataDosen(Parcel in) {
        this.namadosen = in.readString();
        this.nip = in.readString();
        this.alamat = in.readString();
        this.foto = in.readString();
    }

    public static final Parcelable.Creator<DataDosen> CREATOR = new Parcelable.Creator<DataDosen>() {
        @Override
        public DataDosen createFromParcel(Parcel source) {
            return new DataDosen(source);
        }

        @Override
        public DataDosen[] newArray(int size) {
            return new DataDosen[size];
        }
    };
}
