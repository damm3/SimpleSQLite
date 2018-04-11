package com.company;

import java.sql.*;

public class Database {
    static final String url = "jdbc:sqlite:database.db";
    static final int DATABASE_VERSION = 4;

    static Database instance;
    static Connection conn;

    public static Database get(){
        if(instance == null){
            instance = new Database();

            try {
                conn = DriverManager.getConnection(url);
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }

            if(instance.getVersion() != DATABASE_VERSION){
                System.out.println("VERSION = " + instance.getVersion());
                instance.upgradeDatabase();
                instance.setVersion();
            }
        }
        return instance;
    }

    public int getVersion(){
        try (Statement stmt  = conn.createStatement()){
            ResultSet rs  = stmt.executeQuery("PRAGMA user_version");
            while (rs.next()) {
                return rs.getInt("user_version");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return -1;
    }

    public void setVersion(){
        try (Statement stmt  = conn.createStatement()){
            stmt.execute("PRAGMA user_version = " + DATABASE_VERSION);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    void upgradeDatabase(){
        deleteTables();
        createTables();
    }

    void deleteTables(){
        try (Statement stmt = conn.createStatement()) {
            stmt.execute("DROP TABLE IF EXISTS estudiantes;");
            stmt.execute("DROP TABLE IF EXISTS grupos;");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    void createTables(){
        try (Statement stmt = conn.createStatement()) {
            stmt.execute("CREATE TABLE IF NOT EXISTS estudiantes (nombre text, nota real);");
            stmt.execute("CREATE TABLE IF NOT EXISTS grupos (nombre text);");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void insertEstudiante(String nombre, double nota) {
        String sql = "INSERT INTO estudiantes(nombre,nota) VALUES(?,?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, nombre);
            pstmt.setDouble(2, nota);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public Estudiante[] selectEstudiantesConNotaSuperiorA(double nota){
        String sql = "SELECT nombre, nota "
                + "FROM estudiantes WHERE nota > ?";

        try (PreparedStatement pstmt  = conn.prepareStatement(sql)){

            pstmt.setDouble(1, nota);
            ResultSet rs  = pstmt.executeQuery();

            while (rs.next()) {
                System.out.println(rs.getString("nombre") + "\t" +
                        rs.getDouble("nota"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return new Estudiante[1];
    }
}
