package com.example.appcrud;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class MyOpenHelper extends SQLiteOpenHelper {
    private static final String TEAMS_TABLE_CREATE = "CREATE TABLE teams(_id INTEGER PRIMARY KEY AUTOINCREMENT, nombre TEXT, equipo TEXT)";
    private static final String DB_NAME = "teams.sqlite";
    private static final int DB_VERSION = 1;
    private SQLiteDatabase db;

    public MyOpenHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        db=this.getWritableDatabase();
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TEAMS_TABLE_CREATE);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void update(Team actual){
        ContentValues cv = new ContentValues();
        cv.put("nombre", actual.getNombre());
        cv.put("equipo", actual.getEquipo());
        String[] args = new String[]{String.valueOf(actual.getId())};
        db.update("teams", cv, "_id=?", args);
    }

    public void insertar(String nombre,String equipo){
        ContentValues cv = new ContentValues();
        cv.put("nombre", nombre);
        cv.put("equipo", equipo);
        db.insert("teams", null, cv);
    }

    
    public void borrar(Team actual){
        String[] args = new String[]{String.valueOf(actual.getId())};
        db.delete("teams", "_id=?", args);
    }

    //Obtener la lista de equipos en la base de datos
    public ArrayList<Team> getTeams(){
        //Creamos el cursor
        ArrayList<Team> lista=new ArrayList<Team>();
        Cursor c = db.rawQuery("select _id, nombre,equipo from teams", null);
        if (c != null && c.getCount()>0) {
            c.moveToFirst();
            do {
                //Asignamos el valor en nuestras variables para crear un nuevo objeto Equipo
                String nombre = c.getString(c.getColumnIndex("nombre"));
                String equipo = c.getString(c.getColumnIndex("equipo"));
                int id = c.getInt(c.getColumnIndex("_id"));
                Team tm =new Team(id,nombre,equipo);
                //Añadimos el equipo a la lista
                lista.add(tm);
            } while (c.moveToNext());
        }

        //Cerramos el cursor
        c.close();
        return lista;
    }
}