package com.a94googlemail.schneider04.tom.summonersbattle;

import android.content.Context;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Tuple implements Serializable {
    private String name;
    private int filename;

    public Tuple(String name, int filename) {
        this.name = name;
        this.filename = filename;
    }

    public String getName() {
        return name;
    }

    public int getFilename() {
        return filename;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setFilename(int filename) {
        this.filename = filename;
    }


    private void writeObject(ObjectOutputStream oos) throws IOException {
        oos.defaultWriteObject();
        oos.writeObject(name);
        oos.writeObject(filename);
    }

    private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        ois.defaultReadObject();
        name = (String)ois.readObject();
        filename = (int)ois.readObject();
    }
    public static Tuple readTuple(String fileName, GameSurface gameSurface) {
        FileInputStream fis;
        ObjectInputStream ois;
        Tuple t;
        try {
            fis = gameSurface.getContext().openFileInput(fileName+".tup");
            ois  = new ObjectInputStream(fis);
            t = (Tuple) ois.readObject();
            ois.close();
            fis.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            t = new Tuple("adam",R.drawable.adam);
            t.writeTuple(fileName,gameSurface);
        }
        return t;
    }

    public void writeTuple(String fileName, GameSurface gameSurface) {
        FileOutputStream fos;
        ObjectOutputStream oos;
        try {
            fos = gameSurface.getContext().openFileOutput(fileName+".tup", Context.MODE_PRIVATE);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(this);
            oos.close();
            fos.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
