package com.autocare.marcacoes.repositorio;

import com.autocare.marcacoes.modelo.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MarcacaoRepositorio {
    private static final String URL = "jdbc:mysql://localhost:3306/autocare_db";
    private static final String USER = "autocare";
    private static final String PASS = "autocare123";

    public void guardar(Marcacao marcacao) {
        String sql = "INSERT INTO Marcacao (data, hora, estado, idViatura, idMecanico) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDate(1, Date.valueOf(marcacao.getData()));
            stmt.setTime(2, Time.valueOf(marcacao.getHora()));
            stmt.setString(3, marcacao.getEstado());
            stmt.setLong(4, marcacao.getViatura().getIdViatura());
            stmt.setLong(5, marcacao.getMecanico().getIdMecanico());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erro ao guardar: " + e.getMessage());
        }
    }

    public List<Marcacao> listarTodas() {
        List<Marcacao> lista = new ArrayList<>();
        // NOVO: Lê também o Cliente para podermos mostrar o NIF e Contacto nos detalhes!
        String sql = "SELECT m.idMarcacao, m.data, m.hora, m.estado, " +
                "v.idViatura, v.matricula, v.marca, v.modelo, v.ano, " +
                "mec.idMecanico, mec.nome as mecNome, mec.especialidade, " +
                "c.idCliente, c.nome as cliNome, c.contacto, c.nif " +
                "FROM Marcacao m " +
                "JOIN Viatura v ON m.idViatura = v.idViatura " +
                "JOIN Mecanico mec ON m.idMecanico = mec.idMecanico " +
                "JOIN Cliente c ON v.idCliente = c.idCliente";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Cliente c = new Cliente(rs.getLong("idCliente"), rs.getString("cliNome"), rs.getString("contacto"), rs.getString("nif"));
                Viatura v = new Viatura(rs.getLong("idViatura"), rs.getString("matricula"), rs.getString("marca"), rs.getString("modelo"), rs.getInt("ano"), c);
                Mecanico mec = new Mecanico(rs.getLong("idMecanico"), rs.getString("mecNome"), rs.getString("especialidade"));
                Marcacao marc = new Marcacao(rs.getLong("idMarcacao"), rs.getDate("data").toLocalDate(), rs.getTime("hora").toLocalTime(), rs.getString("estado"), v, mec);
                lista.add(marc);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar: " + e.getMessage());
        }
        return lista;
    }

    public boolean existe(long idMarcacao) {
        String sql = "SELECT 1 FROM Marcacao WHERE idMarcacao = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, idMarcacao);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) { return false; }
    }

    public void atualizar(Marcacao marcacao) {
        String sql = "UPDATE Marcacao SET data=?, hora=?, estado=? WHERE idMarcacao=?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDate(1, Date.valueOf(marcacao.getData()));
            stmt.setTime(2, Time.valueOf(marcacao.getHora()));
            stmt.setString(3, marcacao.getEstado());
            stmt.setLong(4, marcacao.getIdMarcacao());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar: " + e.getMessage());
        }
    }

    public void remover(long idMarcacao) {
        String sql = "DELETE FROM Marcacao WHERE idMarcacao=?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, idMarcacao);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erro ao remover: " + e.getMessage());
        }
    }
}