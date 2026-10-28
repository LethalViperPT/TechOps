package com.autocare.gestao.servico;

import com.autocare.gestao.excecoes.NotificacaoExternaException;
import com.autocare.gestao.excecoes.PersistenciaException;
import com.autocare.gestao.externo.NotificacaoEmailService;
import com.autocare.gestao.modelo.EstadoMarcacao;
import com.autocare.gestao.modelo.Intervencao;
import com.autocare.gestao.modelo.Marcacao;
import com.autocare.gestao.repositorio.IntervencaoRepositorio;

import java.time.LocalDate;
import java.util.List;

/**
 * Serviço responsável pelas operações de negócio relacionadas com
 * intervenções (RF05 e RF06). Nunca acede ao ficheiro/BD diretamente —
 * passa sempre pelo IntervencaoRepositorio.
 */
public class IntervencaoServico {

    private final IntervencaoRepositorio intervencaoRepositorio;
    private final NotificacaoEmailService notificacaoEmailService;

    public IntervencaoServico(IntervencaoRepositorio intervencaoRepositorio) {
        this.intervencaoRepositorio = intervencaoRepositorio;
        this.notificacaoEmailService = new NotificacaoEmailService();
    }

    /**
     * Regista a intervenção realizada numa marcação, conclui a marcação
     * (via avancarEstado, nunca diretamente) e tenta notificar o cliente
     * por email (Tarefa 8.2). Uma falha na notificação externa não deve
     * impedir o registo da intervenção em si — por isso é tratada e
     * apenas registada como aviso, não propagada.
     */
    public Intervencao registarIntervencao(Marcacao marcacao, String descricao, String pecasUsadas,
                                            double custo, LocalDate dataConclusao) throws PersistenciaException {
        Intervencao intervencao = new Intervencao(0, descricao, pecasUsadas, custo, dataConclusao, marcacao);
        Intervencao guardada = intervencaoRepositorio.inserir(intervencao);
        marcacao.setIntervencao(guardada);
        if (marcacao.getEstado() == EstadoMarcacao.AGENDADA) {
            marcacao.avancarEstado(EstadoMarcacao.CONCLUIDA);
        }

        try {
            String contactoCliente = marcacao.getViatura().getCliente().getContacto();
            notificacaoEmailService.enviarNotificacao(
                    contactoCliente,
                    "A sua viatura está pronta",
                    "Concluímos a intervenção \"" + descricao + "\" na sua viatura " + marcacao.getViatura().getMatricula() + ".");
        } catch (NotificacaoExternaException erro) {
            System.err.println("Aviso: não foi possível notificar o cliente — " + erro.getMessage());
        }

        return guardada;
    }

    /** Histórico completo de intervenções de uma viatura (RF06). */
    public List<Intervencao> consultarHistoricoDaViatura(long idViatura) throws PersistenciaException {
        return intervencaoRepositorio.listarPorViatura(idViatura);
    }

    public void atualizarIntervencao(Intervencao intervencao) throws PersistenciaException {
        intervencaoRepositorio.atualizar(intervencao);
    }

    public void removerIntervencao(long idIntervencao) throws PersistenciaException {
        intervencaoRepositorio.remover(idIntervencao);
    }
}
