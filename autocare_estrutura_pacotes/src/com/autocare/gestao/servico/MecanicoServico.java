package com.autocare.gestao.servico;

import com.autocare.gestao.excecoes.PersistenciaException;
import com.autocare.gestao.modelo.Mecanico;
import com.autocare.gestao.repositorio.MecanicoRepositorio;

import java.util.List;

/**
 * Serviço responsável pelas operações de negócio relacionadas com mecânicos.
 * Não corresponde a nenhum RF autónomo do documento de definição — existe
 * apenas como apoio, para que Marcação tenha sempre um Mecânico real e
 * persistido a referenciar (exigido pela chave estrangeira da Aula 8).
 */
public class MecanicoServico {

    private final MecanicoRepositorio mecanicoRepositorio;

    public MecanicoServico(MecanicoRepositorio mecanicoRepositorio) {
        this.mecanicoRepositorio = mecanicoRepositorio;
    }

    public Mecanico registarMecanico(String nome, String especialidade) throws PersistenciaException {
        return mecanicoRepositorio.inserir(new Mecanico(0, nome, especialidade));
    }

    public List<Mecanico> listarMecanicos() throws PersistenciaException {
        return mecanicoRepositorio.listarTodos();
    }
}
