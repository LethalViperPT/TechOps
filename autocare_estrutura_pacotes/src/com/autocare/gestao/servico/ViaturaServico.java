package com.autocare.gestao.servico;

import com.autocare.gestao.excecoes.PersistenciaException;
import com.autocare.gestao.modelo.Cliente;
import com.autocare.gestao.modelo.Viatura;
import com.autocare.gestao.repositorio.ViaturaRepositorio;

import java.util.List;

/**
 * Serviço responsável pelas operações de negócio relacionadas com viaturas (RF02).
 * Nunca acede ao ficheiro diretamente — passa sempre pelo ViaturaRepositorio.
 */
public class ViaturaServico {

    private final ViaturaRepositorio viaturaRepositorio;

    public ViaturaServico(ViaturaRepositorio viaturaRepositorio) {
        this.viaturaRepositorio = viaturaRepositorio;
    }

    public Viatura registarViatura(String matricula, String marca, String modelo, int ano, Cliente cliente)
            throws PersistenciaException {
        Viatura viatura = new Viatura(0, matricula, marca, modelo, ano, cliente);
        return viaturaRepositorio.inserir(viatura);
    }

    public List<Viatura> listarViaturasDoCliente(long idCliente) throws PersistenciaException {
        return viaturaRepositorio.listarPorCliente(idCliente);
    }

    public List<Viatura> listarTodas() throws PersistenciaException {
        return viaturaRepositorio.listarTodas();
    }

    public void atualizarViatura(Viatura viatura) throws PersistenciaException {
        viaturaRepositorio.atualizar(viatura);
    }

    public void removerViatura(long idViatura) throws PersistenciaException {
        viaturaRepositorio.remover(idViatura);
    }
}
