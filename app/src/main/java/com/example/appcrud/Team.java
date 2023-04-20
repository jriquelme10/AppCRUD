package com.example.appcrud;

public class Team {

    int id;
    String nombre;
    String equipo;

    //Constructor
    public Team(int _id,String _nombre,String _equipo){
        id=_id;
        nombre=_nombre;
        equipo=_equipo;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setEquipo(String equipo) {
        this.equipo = equipo;
    }

    //Represetacion del objeto como cadena de texto
    @Override
    public String toString() {
        return nombre;
    }

    //Metodos de acceso a cada atribito de la clase
    public int getId(){
        return id;
    }

    public String getNombre(){
        return nombre;
    }

    public String getEquipo(){
        return equipo;
    }

}
