package com.company;

public class Main {

    public static void main(String[] args) {
	    Database db = Database.get();

        db.createTables();

        db.insertEstudiante("hola", 4);
        db.insertEstudiante("adios", 6);

        db.selectEstudiantesConNotaSuperiorA(3);

    }
}
