package com.autocare.gestao.repositorio;

import com.autocare.gestao.excecoes.PersistenciaException;
import com.autocare.gestao.excecoes.RegistoNaoEncontradoException;
import com.autocare.gestao.modelo.Mecanico;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Repositório responsável pelo acesso a dados da entidade Mecânico.
 * Agora com implementação real via JDBC (necessária para satisfazer a
 * chave estrangeira id_mecanico em marcacao), ainda que não exista nenhum
 * RF autónomo de gestão de mecânicos no documento de definição.
 */
public class MecanicoRepositorio {

    private final Connection connection;

    public MecanicoRepositorio(Connection connection) {
        this.connection = connection;
    }

    public Mecanico inserir(Mecanico mecanico) throws PersistenciaException {
        String sql = "INSERT INTO mecanico (nome, especialidade) VALUES (?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, mecanico.getNome());
            ps.setString(2, mecanico.getEspecialidade());
            ps.executeUpdate();
            try (ResultSet chaves = ps.getGeneratedKeys()) {
                if (chaves.next()) {
                    mecanico.setIdMecanico(chaves.getLong(1));
                }
            }
            return mecanico;
        } catch (SQLException erro) {
            throw new PersistenciaException("Não foi possível inserir o mecânico.", erro);
        }
    }

    public Mecanico buscarPorId(long idMecanico) throws PersistenciaException {
        String sql = "SELECT id_mecanico, nome, especialidade FROM mecanico WHERE id_mecanico = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, idMecanico);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? mapear(rs) : null;
            }
        } catch (SQLException erro) {
            throw new PersistenciaException("Não foi possível procurar o mecânico.", erro);
        }
    }

    public List<Mecanico> listarTodos() throws PersistenciaException {
        String sql = "SELECT id_mecanico, nome, especialidade FROM mecanico";
        List<Mecanico> lista = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(mapear(rs));
            }
            return lista;
        } catch (SQLException erro) {
            throw new PersistenciaException("Não foi possível listar os mecânicos.", erro);
        }
    }

    public void atualizar(Mecanico mecanico) throws PersistenciaException {
        String sql = "UPDATE mecanico SET nome = ?, especialidade = ? WHERE id_mecanico = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, mecanico.getNome());
            ps.setString(2, mecanico.getEspecialidade());
            ps.setLong(3, mecanico.getIdMecanico());
            if (ps.executeUpdate() == 0) {
                throw new RegistoNaoEncontradoException(
                        "Não existe mecânico com id " + mecanico.getIdMecanico() + " para atualizar.");
            }
        } catch (SQLException erro) {
            throw new PersistenciaException("Não foi possível atualizar o mecânico.", erro);
        }
    }

    public void remover(long idMecanico) throws PersistenciaException {
        String sql = "DELETE FROM mecanico WHERE id_mecanico = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, idMecanico);
            if (ps.executeUpdate() == 0) {
                throw new RegistoNaoEncontradoException("Não existe mecânico com id " + idMecanico + " para remover.");
            }
        } catch (SQLException erro) {
            throw new PersistenciaException("Não foi possível remover o mecânico.", erro);
        }
    }

    private Mecanico mapear(ResultSet rs) throws SQLException {
        return new Mecanico(rs.getLong("id_mecanico"), rs.getString("nome"), rs.getString("especialidade"));
    }
}
