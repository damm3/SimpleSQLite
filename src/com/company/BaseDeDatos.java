package com.company;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BaseDeDatos {
    static final String url = "jdbc:sqlite:database.db";

    static BaseDeDatos instance;
    static Connection connection;

    public static BaseDeDatos get(){
        if(instance == null){
            instance = new BaseDeDatos();

            try {
                connection = DriverManager.getConnection(url);
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }

        }
        return instance;
    }

    void deleteTables(){
        try (Statement statement = connection.createStatement()) {
            statement.execute("DROP TABLE IF EXISTS estudiantes");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    void createTables(){
        try (Statement statement = connection.createStatement()) {
            statement.execute("CREATE TABLE IF NOT EXISTS estudiantes (nombre text, nota real)");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void insertEstudiante(String nombre, double nota) {
        String sql = "INSERT INTO estudiantes(nombre,nota) VALUES(?,?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, nombre);
            preparedStatement.setDouble(2, nota);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public List<Estudiante> selectEstudiantes(){
        String sql = "SELECT nombre, nota FROM estudiantes";

        List<Estudiante> listaEstudiantes = new ArrayList<>();
        try (PreparedStatement preparedStatement  = connection.prepareStatement(sql)){

            ResultSet resultSet  = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String nombre = resultSet.getString("nombre");
                float nota = resultSet.getFloat("nota");

                listaEstudiantes.add(new Estudiante(nombre, nota));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return listaEstudiantes;
    }

    public List<Estudiante> selectEstudiantesConNotaSuperiorA(double notaMinima){
        String sql = "SELECT nombre, nota FROM estudiantes WHERE nota > ?";

        List<Estudiante> listaEstudiantes = new ArrayList<>();
        try (PreparedStatement preparedStatement  = connection.prepareStatement(sql)){

            preparedStatement.setDouble(1, notaMinima);
            ResultSet resultSet  = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String nombre = resultSet.getString("nombre");
                float nota = resultSet.getFloat("nota");

                listaEstudiantes .add(new Estudiante(nombre, nota));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return listaEstudiantes ;
    }
}