package com.autocare.gestao.repositorio;

import com.autocare.gestao.excecoes.PersistenciaException;
import com.autocare.gestao.excecoes.RegistoNaoEncontradoException;
import com.autocare.gestao.modelo.Intervencao;
import com.autocare.gestao.modelo.Marcacao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Repositório responsável pelo acesso a dados da entidade Intervenção.
 * Migrado da persistência em ficheiro para JDBC (Tarefa 8.1). Para
 * reconstruir a Marcação associada, delega no MarcacaoRepositorio, que já
 * sabe fazer o JOIN necessário — evita duplicar essa lógica aqui.
 */
public class IntervencaoRepositorio {

    private final Connection connection;
    private final MarcacaoRepositorio marcacaoRepositorio;

    public IntervencaoRepositorio(Connection connection) {
        this.connection = connection;
        this.marcacaoRepositorio = new MarcacaoRepositorio(connection);
    }

    public Intervencao inserir(Intervencao intervencao) throws PersistenciaException {
        String sql = "INSERT INTO intervencao (descricao, pecas_usadas, custo, data_conclusao, id_marcacao) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, intervencao.getDescricao());
            ps.setString(2, intervencao.getPecasUsadas());
            ps.setDouble(3, intervencao.getCusto());
            ps.setDate(4, Date.valueOf(intervencao.getDataConclusao()));
            ps.setLong(5, intervencao.getMarcacao().getIdMarcacao());
            ps.executeUpdate();
            try (ResultSet chaves = ps.getGeneratedKeys()) {
                if (chaves.next()) {
                    intervencao.setIdIntervencao(chaves.getLong(1));
                }
            }
            return intervencao;
        } catch (SQLException erro) {
            throw new PersistenciaException("Não foi possível inserir a intervenção.", erro);
        }
    }

    public Intervencao buscarPorId(long idIntervencao) throws PersistenciaException {
        String sql = "SELECT id_intervencao, descricao, pecas_usadas, custo, data_conclusao, id_marcacao "
                + "FROM intervencao WHERE id_intervencao = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, idIntervencao);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? mapear(rs) : null;
            }
        } catch (SQLException erro) {
            throw new PersistenciaException("Não foi possível procurar a intervenção.", erro);
        }
    }

    /** Histórico completo de intervenções de uma viatura, ordenado por data (RF06). */
    public List<Intervencao> listarPorViatura(long idViatura) throws PersistenciaException {
        String sql = "SELECT i.id_intervencao, i.descricao, i.pecas_usadas, i.custo, i.data_conclusao, i.id_marcacao "
                + "FROM intervencao i JOIN marcacao m ON m.id_marcacao = i.id_marcacao "
                + "WHERE m.id_viatura = ? ORDER BY i.data_conclusao";
        List<Intervencao> lista = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, idViatura);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapear(rs));
                }
            }
            return lista;
        } catch (SQLException erro) {
            throw new PersistenciaException("Não foi possível obter o histórico da viatura.", erro);
        }
    }

    public void atualizar(Intervencao intervencao) throws PersistenciaException {
        String sql = "UPDATE intervencao SET descricao = ?, pecas_usadas = ?, custo = ?, data_conclusao = ? WHERE id_intervencao = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, intervencao.getDescricao());
            ps.setString(2, intervencao.getPecasUsadas());
            ps.setDouble(3, intervencao.getCusto());
            ps.setDate(4, Date.valueOf(intervencao.getDataConclusao()));
            ps.setLong(5, intervencao.getIdIntervencao());
            if (ps.executeUpdate() == 0) {
                throw new RegistoNaoEncontradoException(
                        "Não existe intervenção com id " + intervencao.getIdIntervencao() + " para atualizar.");
            }
        } catch (SQLException erro) {
            throw new PersistenciaException("Não foi possível atualizar a intervenção.", erro);
        }
    }

    public void remover(long idIntervencao) throws PersistenciaException {
        String sql = "DELETE FROM intervencao WHERE id_intervencao = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, idIntervencao);
            if (ps.executeUpdate() == 0) {
                throw new RegistoNaoEncontradoException("Não existe intervenção com id " + idIntervencao + " para remover.");
            }
        } catch (SQLException erro) {
            throw new PersistenciaException("Não foi possível remover a intervenção.", erro);
        }
    }

    private Intervencao mapear(ResultSet rs) throws SQLException, PersistenciaException {
        long idMarcacao = rs.getLong("id_marcacao");
        Marcacao marcacao = marcacaoRepositorio.buscarPorId(idMarcacao);
        return new Intervencao(rs.getLong("id_intervencao"), rs.getString("descricao"), rs.getString("pecas_usadas"),
                rs.getDouble("custo"), rs.getDate("data_conclusao").toLocalDate(), marcacao);
    }
}
