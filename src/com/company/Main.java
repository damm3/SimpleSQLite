package com.company;

import java.util.List;

public class Main {

    public static void main(String[] args) {
	    BaseDeDatos db = BaseDeDatos.get();

        db.createTables();

        db.insertEstudiante("hola", 4);
        db.insertEstudiante("adios", 6);

        List<Estudiante> estudianteList = db.selectEstudiantesConNotaSuperiorA(3);

        for(Estudiante estudiante:estudianteList) {
            System.out.println(estudiante.nombre + " : " + estudiante.nota);
        }
    }
}
