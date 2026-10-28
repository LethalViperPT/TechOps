import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

/**
 * Responsável pela lógica de gestão da lista de tarefas:
 * criar, listar, editar, eliminar, marcar como concluída, filtrar e ordenar.
 */
public class GestorTarefas {

    private final List<Tarefa> tarefas;
    private int proximoId;

    public GestorTarefas(List<Tarefa> tarefasIniciais) {
        this.tarefas = new ArrayList<>(tarefasIniciais);
        this.proximoId = tarefasIniciais.stream()
                .mapToInt(Tarefa::getId)
                .max()
                .orElse(0) + 1;
    }

    /** RF01 — Criar uma nova tarefa. */
    public Tarefa adicionarTarefa(String titulo, String descricao, LocalDate dataLimite, Prioridade prioridade) {
        Tarefa tarefa = new Tarefa(proximoId, titulo, descricao, dataLimite, prioridade);
        tarefas.add(tarefa);
        proximoId++;
        return tarefa;
    }

    /** RF02 — Listar todas as tarefas. */
    public List<Tarefa> listarTarefas() {
        return new ArrayList<>(tarefas);
    }

    private Optional<Tarefa> procurarPorId(int id) {
        return tarefas.stream().filter(t -> t.getId() == id).findFirst();
    }

    /** RF03 — Editar uma tarefa existente. Campos a null/vazios mantêm o valor atual. */
    public boolean editarTarefa(int id, String novoTitulo, String novaDescricao,
                                 LocalDate novaData, Prioridade novaPrioridade) {
        Optional<Tarefa> tarefaOpt = procurarPorId(id);
        if (tarefaOpt.isEmpty()) {
            return false;
        }
        Tarefa tarefa = tarefaOpt.get();
        if (novoTitulo != null && !novoTitulo.isBlank()) tarefa.setTitulo(novoTitulo);
        if (novaDescricao != null && !novaDescricao.isBlank()) tarefa.setDescricao(novaDescricao);
        if (novaData != null) tarefa.setDataLimite(novaData);
        if (novaPrioridade != null) tarefa.setPrioridade(novaPrioridade);
        return true;
    }

    /** RF04 — Marcar tarefa como concluída. */
    public boolean marcarConcluida(int id) {
        Optional<Tarefa> tarefaOpt = procurarPorId(id);
        tarefaOpt.ifPresent(Tarefa::marcarConcluida);
        return tarefaOpt.isPresent();
    }

    /** RF05 — Eliminar uma tarefa. */
    public boolean removerTarefa(int id) {
        return tarefas.removeIf(t -> t.getId() == id);
    }

    /** RF08 (opcional) — Filtrar tarefas por prioridade e/ou estado de conclusão. */
    public List<Tarefa> filtrarTarefas(Prioridade prioridade, Boolean concluida) {
        List<Tarefa> resultado = new ArrayList<>();
        for (Tarefa t : tarefas) {
            boolean corresponde = true;
            if (prioridade != null && t.getPrioridade() != prioridade) corresponde = false;
            if (concluida != null && t.isConcluida() != concluida) corresponde = false;
            if (corresponde) resultado.add(t);
        }
        return resultado;
    }

    /** RF09 (opcional) — Ordenar todas as tarefas por data, prioridade ou título. */
    public List<Tarefa> ordenarTarefas(String criterio) {
        return ordenarLista(tarefas, criterio);
    }

    /**
     * Ordena uma lista de tarefas já fornecida (por exemplo, o resultado de um filtro),
     * sem alterar a lista interna do gestor. Reutilizado tanto pela consola como pela
     * interface gráfica, para não duplicar a lógica de ordenação.
     */
    public List<Tarefa> ordenarLista(List<Tarefa> lista, String criterio) {
        List<Tarefa> resultado = new ArrayList<>(lista);
        switch (criterio.toLowerCase()) {
            case "data":
                resultado.sort(Comparator.comparing(Tarefa::getDataLimite));
                break;
            case "prioridade":
                // ALTA primeiro: ordena pelo enum invertido (ALTA > MEDIA > BAIXA)
                resultado.sort(Comparator.comparing(Tarefa::getPrioridade).reversed());
                break;
            case "titulo":
                resultado.sort(Comparator.comparing(t -> t.getTitulo().toLowerCase()));
                break;
            default:
                throw new IllegalArgumentException("Critério de ordenação inválido: " + criterio);
        }
        return resultado;
    }
}
