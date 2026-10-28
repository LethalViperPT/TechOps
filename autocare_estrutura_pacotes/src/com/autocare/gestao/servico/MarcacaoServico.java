package com.autocare.gestao.servico;

import com.autocare.gestao.excecoes.PersistenciaException;
import com.autocare.gestao.excecoes.ValidacaoException;
import com.autocare.gestao.modelo.EstadoMarcacao;
import com.autocare.gestao.modelo.Marcacao;
import com.autocare.gestao.modelo.Mecanico;
import com.autocare.gestao.modelo.Viatura;
import com.autocare.gestao.repositorio.MarcacaoRepositorio;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

/**
 * Serviço responsável pelas operações de negócio relacionadas com marcações
 * (RF03, RF04 e RF07). Nunca acede ao ficheiro diretamente — passa sempre
 * pelo MarcacaoRepositorio.
 */
public class MarcacaoServico {

    private final MarcacaoRepositorio marcacaoRepositorio;

    public MarcacaoServico(MarcacaoRepositorio marcacaoRepositorio) {
        this.marcacaoRepositorio = marcacaoRepositorio;
    }

    /**
     * Cria uma nova marcação, rejeitando-a se o mecânico já tiver outra
     * marcação (ainda agendada) na mesma data e hora (RF04).
     */
    public Marcacao criarMarcacao(Viatura viatura, Mecanico mecanico, LocalDate data, LocalTime hora)
            throws PersistenciaException {

        List<Marcacao> marcacoesDoMecanico = marcacaoRepositorio.listarPorMecanicoEHora(mecanico.getIdMecanico(), data);
        boolean existeConflito = marcacoesDoMecanico.stream()
                .anyMatch(m -> m.getHora().equals(hora) && m.getEstado() == EstadoMarcacao.AGENDADA);

        if (existeConflito) {
            throw new ValidacaoException(
                    "O mecânico " + mecanico.getNome() + " já tem uma marcação agendada em " + data + " às " + hora + ".");
        }

        Marcacao marcacao = new Marcacao(0, data, hora, viatura, mecanico);
        return marcacaoRepositorio.inserir(marcacao);
    }

    public List<Marcacao> listarMarcacoesDoDia(LocalDate data) throws PersistenciaException {
        return marcacaoRepositorio.listarPorDia(data);
    }

    public List<Marcacao> listarTodas() throws PersistenciaException {
        return marcacaoRepositorio.listarTodas();
    }

    /**
     * Pesquisa combinada de marcações (Tarefa 7.1): qualquer critério que seja
     * passado como null é ignorado, permitindo combinar estado, mecânico e/ou
     * data livremente, através da API de Streams.
     */
    public List<Marcacao> pesquisar(EstadoMarcacao estado, Long idMecanico, LocalDate data) throws PersistenciaException {
        return marcacaoRepositorio.listarTodas().stream()
                .filter(m -> estado == null || m.getEstado() == estado)
                .filter(m -> idMecanico == null || m.getMecanico().getIdMecanico() == idMecanico)
                .filter(m -> data == null || m.getData().equals(data))
                .toList();
    }

    public void atualizarMarcacao(Marcacao marcacao) throws PersistenciaException {
        marcacaoRepositorio.atualizar(marcacao);
    }

    public void removerMarcacao(long idMarcacao) throws PersistenciaException {
        marcacaoRepositorio.remover(idMarcacao);
    }

    /** Marca a marcação como concluída (usa a regra de transição de estado da própria entidade). */
    public void concluirMarcacao(Marcacao marcacao) throws PersistenciaException {
        marcacao.avancarEstado(EstadoMarcacao.CONCLUIDA);
        marcacaoRepositorio.atualizar(marcacao);
    }

    /** Marca a marcação como cancelada (usa a regra de transição de estado da própria entidade). */
    public void cancelarMarcacao(Marcacao marcacao) throws PersistenciaException {
        marcacao.avancarEstado(EstadoMarcacao.CANCELADA);
        marcacaoRepositorio.atualizar(marcacao);
    }
}
