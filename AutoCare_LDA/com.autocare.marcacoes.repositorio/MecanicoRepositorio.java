package com.autocare.marcacoes.repositorio;
import com.autocare.marcacoes.modelo.Mecanico;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MecanicoRepositorio {
    private static final String URL = "jdbc:mysql://localhost:3306/autocare_db";
    private static final String USER = "autocare";
    private static final String PASS = "autocare123";

    public List<Mecanico> listarTodos() {
        List<Mecanico> lista = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM Mecanico")) {
            while (rs.next()) {
                lista.add(new Mecanico(rs.getLong("idMecanico"), rs.getString("nome"), rs.getString("especialidade")));
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return lista;
    }
}