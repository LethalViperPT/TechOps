package com.autocare.gestao.servico;

import com.autocare.gestao.excecoes.PersistenciaException;
import com.autocare.gestao.modelo.Cliente;
import com.autocare.gestao.repositorio.ClienteRepositorio;

import java.util.List;

/**
 * Serviço responsável pelas operações de negócio relacionadas com clientes (RF01).
 * Nunca acede ao ficheiro diretamente — passa sempre pelo ClienteRepositorio.
 */
public class ClienteServico {

    private final ClienteRepositorio clienteRepositorio;

    public ClienteServico(ClienteRepositorio clienteRepositorio) {
        this.clienteRepositorio = clienteRepositorio;
    }

    public Cliente registarCliente(String nome, String contacto, String nif) throws PersistenciaException {
        // A validação de contacto/nif acontece dentro do próprio construtor de Cliente (Tarefa 6.2).
        Cliente cliente = new Cliente(0, nome, contacto, nif);
        return clienteRepositorio.inserir(cliente);
    }

    public Cliente consultarCliente(long idCliente) throws PersistenciaException {
        return clienteRepositorio.buscarPorId(idCliente);
    }

    public List<Cliente> listarClientes() throws PersistenciaException {
        return clienteRepositorio.listarTodos();
    }

    public void atualizarCliente(Cliente cliente) throws PersistenciaException {
        clienteRepositorio.atualizar(cliente);
    }

    public void removerCliente(long idCliente) throws PersistenciaException {
        clienteRepositorio.remover(idCliente);
    }
}
