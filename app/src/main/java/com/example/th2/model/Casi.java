package com.example.th2.model;

import java.io.Serializable;

public class Casi implements Serializable {
    private int id_casi;
    private String name;
    private String img;

    public Casi() {
    }
    public Casi(int id_casi, String name, String img) {
        this.id_casi = id_casi;
        this.name = name;
        this.img = img;
    }

    public Casi(String name, String img) {
        this.name = name;
        this.img = img;
    }

    public int getId_casi() {
        return id_casi;
    }

    public void setId_casi(int id_casi) {
        this.id_casi = id_casi;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
