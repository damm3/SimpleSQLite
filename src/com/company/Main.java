package com.company;

import java.util.List;

public class Main {

    public static void main(String[] args) {
	    BaseDeDatos db = BaseDeDatos.get();

	    db.deleteTables();
        db.createTables();

        db.insertEstudiante("hola", 2);
        db.insertEstudiante("hola", 4);
        db.insertEstudiante("adios", 6);

        System.out.println("TODOS");
        List<Estudiante> estudianteList1 = db.selectEstudiantes();
        for(Estudiante estudiante:estudianteList1) {
            System.out.println(estudiante.nombre + " : " + estudiante.nota);
        }

        System.out.println("CON NOTA SUPERIOR A 3");
        List<Estudiante> estudianteList2 = db.selectEstudiantesConNotaSuperiorA(3);
        for(Estudiante estudiante:estudianteList2) {
            System.out.println(estudiante.nombre + " : " + estudiante.nota);
        }
    }
}
