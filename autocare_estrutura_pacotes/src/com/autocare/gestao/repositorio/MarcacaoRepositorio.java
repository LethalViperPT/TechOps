package com.autocare.gestao.repositorio;

import com.autocare.gestao.excecoes.PersistenciaException;
import com.autocare.gestao.excecoes.RegistoNaoEncontradoException;
import com.autocare.gestao.modelo.Cliente;
import com.autocare.gestao.modelo.EstadoMarcacao;
import com.autocare.gestao.modelo.Marcacao;
import com.autocare.gestao.modelo.Mecanico;
import com.autocare.gestao.modelo.Viatura;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Repositório responsável pelo acesso a dados da entidade Marcação.
 * Migrado da persistência em ficheiro para JDBC (Tarefa 8.1). Reconstrói
 * Viatura, Cliente e Mecânico através de um JOIN de três tabelas, e usa
 * Marcacao.reconstruir(...) para respeitar o estado tal como está gravado.
 */
public class MarcacaoRepositorio {

    private static final String SELECAO_BASE =
            "SELECT m.id_marcacao, m.data, m.hora, m.estado, "
                    + "       v.id_viatura, v.matricula, v.marca, v.modelo, v.ano, "
                    + "       c.id_cliente, c.nome AS cliente_nome, c.contacto AS cliente_contacto, c.nif AS cliente_nif, "
                    + "       me.id_mecanico, me.nome AS mecanico_nome, me.especialidade "
                    + "FROM marcacao m "
                    + "JOIN viatura v  ON v.id_viatura = m.id_viatura "
                    + "JOIN cliente c  ON c.id_cliente = v.id_cliente "
                    + "JOIN mecanico me ON me.id_mecanico = m.id_mecanico ";

    private final Connection connection;

    public MarcacaoRepositorio(Connection connection) {
        this.connection = connection;
    }

    public Marcacao inserir(Marcacao marcacao) throws PersistenciaException {
        String sql = "INSERT INTO marcacao (data, hora, estado, id_viatura, id_mecanico) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setDate(1, Date.valueOf(marcacao.getData()));
            ps.setTime(2, Time.valueOf(marcacao.getHora()));
            ps.setString(3, marcacao.getEstado().name());
            ps.setLong(4, marcacao.getViatura().getIdViatura());
            ps.setLong(5, marcacao.getMecanico().getIdMecanico());
            ps.executeUpdate();
            try (ResultSet chaves = ps.getGeneratedKeys()) {
                if (chaves.next()) {
                    marcacao.setIdMarcacao(chaves.getLong(1));
                }
            }
            return marcacao;
        } catch (SQLException erro) {
            throw new PersistenciaException("Não foi possível inserir a marcação.", erro);
        }
    }

    public Marcacao buscarPorId(long idMarcacao) throws PersistenciaException {
        String sql = SELECAO_BASE + "WHERE m.id_marcacao = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, idMarcacao);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? mapear(rs) : null;
            }
        } catch (SQLException erro) {
            throw new PersistenciaException("Não foi possível procurar a marcação.", erro);
        }
    }

    public List<Marcacao> listarTodas() throws PersistenciaException {
        List<Marcacao> lista = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(SELECAO_BASE);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(mapear(rs));
            }
            return lista;
        } catch (SQLException erro) {
            throw new PersistenciaException("Não foi possível listar as marcações.", erro);
        }
    }

    public List<Marcacao> listarPorDia(LocalDate data) throws PersistenciaException {
        String sql = SELECAO_BASE + "WHERE m.data = ?";
        List<Marcacao> lista = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setDate(1, Date.valueOf(data));
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapear(rs));
                }
            }
            return lista;
        } catch (SQLException erro) {
            throw new PersistenciaException("Não foi possível listar as marcações do dia.", erro);
        }
    }

    public List<Marcacao> listarPorMecanicoEHora(long idMecanico, LocalDate data) throws PersistenciaException {
        String sql = SELECAO_BASE + "WHERE m.id_mecanico = ? AND m.data = ?";
        List<Marcacao> lista = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, idMecanico);
            ps.setDate(2, Date.valueOf(data));
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapear(rs));
                }
            }
            return lista;
        } catch (SQLException erro) {
            throw new PersistenciaException("Não foi possível verificar as marcações do mecânico.", erro);
        }
    }

    public void atualizar(Marcacao marcacao) throws PersistenciaException {
        String sql = "UPDATE marcacao SET data = ?, hora = ?, estado = ?, id_viatura = ?, id_mecanico = ? WHERE id_marcacao = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setDate(1, Date.valueOf(marcacao.getData()));
            ps.setTime(2, Time.valueOf(marcacao.getHora()));
            ps.setString(3, marcacao.getEstado().name());
            ps.setLong(4, marcacao.getViatura().getIdViatura());
            ps.setLong(5, marcacao.getMecanico().getIdMecanico());
            ps.setLong(6, marcacao.getIdMarcacao());
            if (ps.executeUpdate() == 0) {
                throw new RegistoNaoEncontradoException(
                        "Não existe marcação com id " + marcacao.getIdMarcacao() + " para atualizar.");
            }
        } catch (SQLException erro) {
            throw new PersistenciaException("Não foi possível atualizar a marcação.", erro);
        }
    }

    public void remover(long idMarcacao) throws PersistenciaException {
        String sql = "DELETE FROM marcacao WHERE id_marcacao = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, idMarcacao);
            if (ps.executeUpdate() == 0) {
                throw new RegistoNaoEncontradoException("Não existe marcação com id " + idMarcacao + " para remover.");
            }
        } catch (SQLException erro) {
            throw new PersistenciaException("Não foi possível remover a marcação.", erro);
        }
    }

    private Marcacao mapear(ResultSet rs) throws SQLException {
        Cliente cliente = new Cliente(rs.getLong("id_cliente"), rs.getString("cliente_nome"),
                rs.getString("cliente_contacto"), rs.getString("cliente_nif"));
        Viatura viatura = new Viatura(rs.getLong("id_viatura"), rs.getString("matricula"), rs.getString("marca"),
                rs.getString("modelo"), rs.getInt("ano"), cliente);
        Mecanico mecanico = new Mecanico(rs.getLong("id_mecanico"), rs.getString("mecanico_nome"), rs.getString("especialidade"));
        EstadoMarcacao estado = EstadoMarcacao.valueOf(rs.getString("estado"));
        return Marcacao.reconstruir(rs.getLong("id_marcacao"), rs.getDate("data").toLocalDate(),
                rs.getTime("hora").toLocalTime(), estado, viatura, mecanico);
    }
}
