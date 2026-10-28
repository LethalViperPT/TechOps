package com.autocare.gestao.repositorio;

import com.autocare.gestao.excecoes.PersistenciaException;
import com.autocare.gestao.excecoes.RegistoNaoEncontradoException;
import com.autocare.gestao.modelo.Cliente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Repositório responsável pelo acesso a dados da entidade Cliente.
 * Migrado da persistência em ficheiro (Aula 5) para JDBC (Tarefa 8.1).
 * As assinaturas públicas mantêm-se exatamente iguais às da versão anterior —
 * só o construtor mudou, para receber a ligação à base de dados.
 */
public class ClienteRepositorio {

    private final Connection connection;

    public ClienteRepositorio(Connection connection) {
        this.connection = connection;
    }

    public Cliente inserir(Cliente cliente) throws PersistenciaException {
        String sql = "INSERT INTO cliente (nome, contacto, nif) VALUES (?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, cliente.getNome());
            ps.setString(2, cliente.getContacto());
            ps.setString(3, cliente.getNif());
            ps.executeUpdate();
            try (ResultSet chaves = ps.getGeneratedKeys()) {
                if (chaves.next()) {
                    cliente.setIdCliente(chaves.getLong(1));
                }
            }
            return cliente;
        } catch (SQLException erro) {
            throw new PersistenciaException("Não foi possível inserir o cliente.", erro);
        }
    }

    public Cliente buscarPorId(long idCliente) throws PersistenciaException {
        String sql = "SELECT id_cliente, nome, contacto, nif FROM cliente WHERE id_cliente = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, idCliente);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? mapear(rs) : null;
            }
        } catch (SQLException erro) {
            throw new PersistenciaException("Não foi possível procurar o cliente.", erro);
        }
    }

    public List<Cliente> listarTodos() throws PersistenciaException {
        String sql = "SELECT id_cliente, nome, contacto, nif FROM cliente";
        List<Cliente> lista = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(mapear(rs));
            }
            return lista;
        } catch (SQLException erro) {
            throw new PersistenciaException("Não foi possível listar os clientes.", erro);
        }
    }

    public void atualizar(Cliente cliente) throws PersistenciaException {
        String sql = "UPDATE cliente SET nome = ?, contacto = ?, nif = ? WHERE id_cliente = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, cliente.getNome());
            ps.setString(2, cliente.getContacto());
            ps.setString(3, cliente.getNif());
            ps.setLong(4, cliente.getIdCliente());
            if (ps.executeUpdate() == 0) {
                throw new RegistoNaoEncontradoException(
                        "Não existe cliente com id " + cliente.getIdCliente() + " para atualizar.");
            }
        } catch (SQLException erro) {
            throw new PersistenciaException("Não foi possível atualizar o cliente.", erro);
        }
    }

    public void remover(long idCliente) throws PersistenciaException {
        String sql = "DELETE FROM cliente WHERE id_cliente = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, idCliente);
            if (ps.executeUpdate() == 0) {
                throw new RegistoNaoEncontradoException("Não existe cliente com id " + idCliente + " para remover.");
            }
        } catch (SQLException erro) {
            throw new PersistenciaException("Não foi possível remover o cliente.", erro);
        }
    }

    private Cliente mapear(ResultSet rs) throws SQLException {
        return new Cliente(rs.getLong("id_cliente"), rs.getString("nome"), rs.getString("contacto"), rs.getString("nif"));
    }
}
