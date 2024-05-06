package com.example.th2.model;

import java.io.Serializable;

public class Baihat implements Serializable {
    private int id_baihat;
    private String namebaihat;
    private Casi casi;
    private String album, theloai;
    private int yeuthich;

    public Baihat() {
    }

    public Baihat(int id_baihat, String namebaihat, Casi casi, String album, String theloai, int yeuthich) {
        this.id_baihat = id_baihat;
        this.namebaihat = namebaihat;
        this.casi = casi;
        this.album = album;
        this.theloai = theloai;
        this.yeuthich = yeuthich;
    }

    public Baihat(String namebaihat, Casi casi, String album, String theloai, int yeuthich) {
        this.namebaihat = namebaihat;
        this.casi = casi;
        this.album = album;
        this.theloai = theloai;
        this.yeuthich = yeuthich;
    }

    public int getId_baihat() {
        return id_baihat;
    }

    public void setId_baihat(int id_baihat) {
        this.id_baihat = id_baihat;
    }

    public String getNamebaihat() {
        return namebaihat;
    }

    public void setNamebaihat(String namebaihat) {
        this.namebaihat = namebaihat;
    }

    public Casi getCasi() {
        return casi;
    }

    public void setCasi(Casi casi) {
        this.casi = casi;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getTheloai() {
        return theloai;
    }

    public void setTheloai(String theloai) {
        this.theloai = theloai;
    }

    public int isYeuthich() {
        return yeuthich;
    }

    public void setYeuthich(int yeuthich) {
        this.yeuthich = yeuthich;
    }
}
