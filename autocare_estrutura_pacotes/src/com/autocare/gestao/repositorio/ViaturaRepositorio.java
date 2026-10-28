package com.autocare.gestao.repositorio;

import com.autocare.gestao.excecoes.PersistenciaException;
import com.autocare.gestao.excecoes.RegistoNaoEncontradoException;
import com.autocare.gestao.modelo.Cliente;
import com.autocare.gestao.modelo.Viatura;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Repositório responsável pelo acesso a dados da entidade Viatura.
 * Migrado da persistência em ficheiro para JDBC (Tarefa 8.1). Reconstrói
 * sempre o Cliente associado através de um JOIN, tal como o objeto Viatura
 * o exige (RF02).
 */
public class ViaturaRepositorio {

    private static final String SELECAO_BASE =
            "SELECT v.id_viatura, v.matricula, v.marca, v.modelo, v.ano, "
                    + "       c.id_cliente, c.nome AS cliente_nome, c.contacto AS cliente_contacto, c.nif AS cliente_nif "
                    + "FROM viatura v JOIN cliente c ON c.id_cliente = v.id_cliente ";

    private final Connection connection;

    public ViaturaRepositorio(Connection connection) {
        this.connection = connection;
    }

    public Viatura inserir(Viatura viatura) throws PersistenciaException {
        String sql = "INSERT INTO viatura (matricula, marca, modelo, ano, id_cliente) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, viatura.getMatricula());
            ps.setString(2, viatura.getMarca());
            ps.setString(3, viatura.getModelo());
            ps.setInt(4, viatura.getAno());
            ps.setLong(5, viatura.getCliente().getIdCliente());
            ps.executeUpdate();
            try (ResultSet chaves = ps.getGeneratedKeys()) {
                if (chaves.next()) {
                    viatura.setIdViatura(chaves.getLong(1));
                }
            }
            return viatura;
        } catch (SQLException erro) {
            throw new PersistenciaException("Não foi possível inserir a viatura.", erro);
        }
    }

    public Viatura buscarPorId(long idViatura) throws PersistenciaException {
        String sql = SELECAO_BASE + "WHERE v.id_viatura = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, idViatura);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? mapear(rs) : null;
            }
        } catch (SQLException erro) {
            throw new PersistenciaException("Não foi possível procurar a viatura.", erro);
        }
    }

    public List<Viatura> listarPorCliente(long idCliente) throws PersistenciaException {
        String sql = SELECAO_BASE + "WHERE v.id_cliente = ?";
        List<Viatura> lista = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, idCliente);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapear(rs));
                }
            }
            return lista;
        } catch (SQLException erro) {
            throw new PersistenciaException("Não foi possível listar as viaturas do cliente.", erro);
        }
    }

    public List<Viatura> listarTodas() throws PersistenciaException {
        List<Viatura> lista = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(SELECAO_BASE);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(mapear(rs));
            }
            return lista;
        } catch (SQLException erro) {
            throw new PersistenciaException("Não foi possível listar as viaturas.", erro);
        }
    }

    public void atualizar(Viatura viatura) throws PersistenciaException {
        String sql = "UPDATE viatura SET matricula = ?, marca = ?, modelo = ?, ano = ?, id_cliente = ? WHERE id_viatura = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, viatura.getMatricula());
            ps.setString(2, viatura.getMarca());
            ps.setString(3, viatura.getModelo());
            ps.setInt(4, viatura.getAno());
            ps.setLong(5, viatura.getCliente().getIdCliente());
            ps.setLong(6, viatura.getIdViatura());
            if (ps.executeUpdate() == 0) {
                throw new RegistoNaoEncontradoException(
                        "Não existe viatura com id " + viatura.getIdViatura() + " para atualizar.");
            }
        } catch (SQLException erro) {
            throw new PersistenciaException("Não foi possível atualizar a viatura.", erro);
        }
    }

    public void remover(long idViatura) throws PersistenciaException {
        String sql = "DELETE FROM viatura WHERE id_viatura = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, idViatura);
            if (ps.executeUpdate() == 0) {
                throw new RegistoNaoEncontradoException("Não existe viatura com id " + idViatura + " para remover.");
            }
        } catch (SQLException erro) {
            throw new PersistenciaException("Não foi possível remover a viatura.", erro);
        }
    }

    private Viatura mapear(ResultSet rs) throws SQLException {
        Cliente cliente = new Cliente(
                rs.getLong("id_cliente"), rs.getString("cliente_nome"),
                rs.getString("cliente_contacto"), rs.getString("cliente_nif"));
        return new Viatura(rs.getLong("id_viatura"), rs.getString("matricula"), rs.getString("marca"),
                rs.getString("modelo"), rs.getInt("ano"), cliente);
    }
}
