package com.example.riccardo.myuniversity;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Database extends SQLiteOpenHelper {

    private static final String DB_NAME = "data";
    private static final int DB_VERSION = 1;

    public Database(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE piano";
        sql += "(_id INTEGER PRIMARY KEY,";
        sql += "codice TEXT NOT NULL,";
        sql += "materia TEXT NOT NULL,";
        sql += "cfu INTEGER NOT NULL,";
        sql += "voto INTEGER,";
        sql += "data TEXT,";
        sql += "seguito TEXT NOT NULL);";
        db.execSQL(sql);

        sql = "CREATE TABLE sessione";
        sql += "(_id INTEGER PRIMARY KEY,";
        sql += "codice TEXT NOT NULL,";
        sql += "materia TEXT NOT NULL,";
        sql += "data1 TEXT NOT NULL,";
        sql += "ora1 TEXT NOT NULL,";
        sql += "aula1 TEXT NOT NULL,";
        sql += "data2 TEXT NOT NULL,";
        sql += "ora2 TEXT NOT NULL,";
        sql += "aula2 TEXT NOT NULL);";
        db.execSQL(sql);

        sql = "CREATE TABLE orario";
        sql += "(_id INTEGER PRIMARY KEY,";
        sql += "codice TEXT NOT NULL,";
        sql += "materia TEXT NOT NULL,";
        sql += "aula TEXT NOT NULL,";
        sql += "lun TEXT,";
        sql += "mar TEXT,";
        sql += "mer TEXT,";
        sql += "gio TEXT,";
        sql += "ven TEXT,";
        sql += "cfu INTEGER NOT NULL);";
        db.execSQL(sql);

        sql = "CREATE TABLE coordinate";
        sql += "(_id INTEGER PRIMARY KEY,";
        sql += "aula TEXT NOT NULL,";
        sql += "latitudine TEXT NOT NULL,";
        sql += "longitudine TEXT NOT NULL);";
        db.execSQL(sql);

        sql = "CREATE TABLE prenotati";
        sql += "(_id INTEGER PRIMARY KEY,";
        sql += "codice TEXT NOT NULL,";
        sql += "data TEXT NOT NULL,";
        sql += "ora TEXT NOT NULL,";
        sql += "aula TEXT NOT NULL);";
        db.execSQL(sql);

        sql = "CREATE TABLE seguiti";
        sql += "(_id INTEGER PRIMARY KEY,";
        sql += "codice TEXT NOT NULL,";
        sql += "materia TEXT);";
        db.execSQL(sql);

        sql = "CREATE TABLE semestre";
        sql += "(_id INTEGER PRIMARY KEY,";
        sql += "inizio TEXT NOT NULL,";
        sql += "fine TEXT NOT NULL);";
        db.execSQL(sql);

    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}