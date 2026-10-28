import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Representa uma tarefa do sistema de gestão de tarefas.
 */
public class Tarefa {

    private static final DateTimeFormatter FORMATO_DATA = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private final int id;
    private String titulo;
    private String descricao;
    private LocalDate dataLimite;
    private Prioridade prioridade;
    private boolean concluida;

    public Tarefa(int id, String titulo, String descricao, LocalDate dataLimite, Prioridade prioridade) {
        this.id = id;
        this.titulo = titulo;
        this.descricao = descricao;
        this.dataLimite = dataLimite;
        this.prioridade = prioridade;
        this.concluida = false;
    }

    // --- Getters e Setters ---

    public int getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public LocalDate getDataLimite() {
        return dataLimite;
    }

    public void setDataLimite(LocalDate dataLimite) {
        this.dataLimite = dataLimite;
    }

    public Prioridade getPrioridade() {
        return prioridade;
    }

    public void setPrioridade(Prioridade prioridade) {
        this.prioridade = prioridade;
    }

    public boolean isConcluida() {
        return concluida;
    }

    public void marcarConcluida() {
        this.concluida = true;
    }

    public void setConcluida(boolean concluida) {
        this.concluida = concluida;
    }

    /**
     * Serializa a tarefa numa linha de texto delimitada por ';' para gravação em ficheiro.
     * Substitui ';' dentro dos campos de texto para não corromper o formato.
     */
    public String paraLinhaFicheiro() {
        return id + ";" +
                titulo.replace(";", ",") + ";" +
                descricao.replace(";", ",") + ";" +
                dataLimite.format(FORMATO_DATA) + ";" +
                prioridade.name() + ";" +
                concluida;
    }

    /**
     * Reconstrói uma Tarefa a partir de uma linha de texto gravada em ficheiro.
     */
    public static Tarefa deLinhaFicheiro(String linha) {
        String[] campos = linha.split(";", -1);
        int id = Integer.parseInt(campos[0]);
        String titulo = campos[1];
        String descricao = campos[2];
        LocalDate data = LocalDate.parse(campos[3], FORMATO_DATA);
        Prioridade prioridade = Prioridade.valueOf(campos[4]);
        boolean concluida = Boolean.parseBoolean(campos[5]);

        Tarefa tarefa = new Tarefa(id, titulo, descricao, data, prioridade);
        tarefa.setConcluida(concluida);
        return tarefa;
    }

    @Override
    public String toString() {
        String estado = concluida ? "[X]" : "[ ]";
        return String.format("%s #%-3d %-25s | Prioridade: %-6s | Prazo: %s | %s",
                estado, id, titulo, prioridade, dataLimite.format(FORMATO_DATA), descricao);
    }
}
