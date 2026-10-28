package com.autocare.marcacoes.servico;

import com.autocare.marcacoes.modelo.Marcacao;
import com.autocare.marcacoes.repositorio.MarcacaoRepositorio;
import com.autocare.marcacoes.excecoes.AutoCareException;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class GestaoMarcacoesServico {

    private MarcacaoRepositorio repositorio;

    public GestaoMarcacoesServico(MarcacaoRepositorio repositorio) {
        this.repositorio = repositorio;
    }

    public void criarMarcacao(Marcacao novaMarcacao) {
        // Validação da Tarefa 6.2: Não permitir datas no passado
        if (novaMarcacao.getData().isBefore(LocalDate.now())) {
            throw new AutoCareException("A data da marcação não pode ser no passado.");
        }

        // RF04 - Impedir sobreposição de marcações para o mesmo mecânico
        List<Marcacao> existentes = repositorio.listarTodas();
        boolean sobreposicao = existentes.stream()
                .anyMatch(m -> m.getMecanico().getIdMecanico() == novaMarcacao.getMecanico().getIdMecanico() &&
                        m.getData().equals(novaMarcacao.getData()) &&
                        m.getHora().equals(novaMarcacao.getHora()));

        if (sobreposicao) {
            throw new AutoCareException("O mecânico já tem uma viatura agendada para essa hora!");
        }

        // Se passar as validações, guarda na base de dados
        repositorio.guardar(novaMarcacao);
    }

    public void atualizarMarcacao(Marcacao marcacaoAtualizada) {
        if (!repositorio.existe(marcacaoAtualizada.getIdMarcacao())) {
            throw new AutoCareException("Erro: Não é possível atualizar. A marcação não existe.");
        }
        repositorio.atualizar(marcacaoAtualizada);
    }

    public void removerMarcacao(long idMarcacao) {
        if (!repositorio.existe(idMarcacao)) {
            throw new AutoCareException("Erro: Não é possível remover. A marcação não foi encontrada.");
        }
        repositorio.remover(idMarcacao);
    }

    // Tarefa 7.1 - Pesquisa com múltiplos critérios (Streams)
    public List<Marcacao> listarMarcacoesDoDiaPorEstado(LocalDate data, String estado) {
        return repositorio.listarTodas().stream()
                .filter(m -> m.getData().equals(data) && m.getEstado().equalsIgnoreCase(estado))
                .collect(Collectors.toList());
    }

    // Método essencial para a interface gráfica carregar os dados reais
    public List<Marcacao> listarTodas() {
        return repositorio.listarTodas();
    }
}