package com.autocare.marcacoes.repositorio;

import com.autocare.marcacoes.modelo.Viatura;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ViaturaRepositorio {
    private static final String URL = "jdbc:mysql://localhost:3306/autocare_db";
    private static final String USER = "autocare";
    private static final String PASS = "autocare123";

    // NOVO: Método para guardar viaturas associadas a novos clientes
    public void guardar(Viatura viatura) {
        String sql = "INSERT INTO Viatura (matricula, marca, modelo, ano, idCliente) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, viatura.getMatricula());
            stmt.setString(2, viatura.getMarca());
            stmt.setString(3, viatura.getModelo());
            stmt.setInt(4, viatura.getAno());
            stmt.setLong(5, viatura.getCliente().getIdCliente());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erro ao guardar viatura: " + e.getMessage());
        }
    }

    public List<Viatura> listarTodas() {
        List<Viatura> lista = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM Viatura")) {
            while (rs.next()) {
                lista.add(new Viatura(rs.getLong("idViatura"), rs.getString("matricula"), rs.getString("marca"), rs.getString("modelo"), rs.getInt("ano"), null));
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return lista;
    }
}