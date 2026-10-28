package com.autocare.marcacoes.repositorio;

import com.autocare.marcacoes.modelo.Cliente;
import java.sql.*;

public class ClienteRepositorio {
    private static final String URL = "jdbc:mysql://localhost:3306/autocare_db";
    private static final String USER = "autocare";
    private static final String PASS = "autocare123";

    // NOVO MÉTODO: Verifica se o NIF já existe na base de dados
    public boolean existeNif(String nif) {
        String sql = "SELECT 1 FROM Cliente WHERE nif = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nif);
            ResultSet rs = stmt.executeQuery();
            return rs.next(); // Devolve true se encontrou o NIF
        } catch (SQLException e) {
            return false;
        }
    }

    public long guardar(Cliente cliente) {
        String sql = "INSERT INTO Cliente (nome, contacto, nif) VALUES (?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, cliente.getNome());
            stmt.setString(2, cliente.getContacto());
            stmt.setString(3, cliente.getNif());
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                return rs.getLong(1);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao guardar cliente: " + e.getMessage());
        }
        return -1;
    }
}