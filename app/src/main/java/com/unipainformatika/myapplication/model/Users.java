package com.unipainformatika.myapplication.model;

public class Users {
    String namalengkap, email, password, id;

    public Users(){}

    public Users(String namalengkap, String email, String password) {
        this.namalengkap = namalengkap;
        this.email = email;
        this.password = password;
    }

    public String getNamalengkap() {
        return namalengkap;
    }

    public void setNamalengkap(String namalengkap) {
        this.namalengkap = namalengkap;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
