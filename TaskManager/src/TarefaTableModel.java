import javax.swing.table.AbstractTableModel;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Adapta uma lista de Tarefa para ser apresentada numa JTable.
 * Faz a ponte entre o modelo de dados (Tarefa) e o componente visual (JTable).
 */
public class TarefaTableModel extends AbstractTableModel {

    private static final DateTimeFormatter FORMATO_DATA = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final String[] COLUNAS = { "ID", "Título", "Prioridade", "Prazo", "Estado", "Descrição" };

    private List<Tarefa> tarefas = new ArrayList<>();

    public void atualizar(List<Tarefa> novasTarefas) {
        this.tarefas = new ArrayList<>(novasTarefas);
        fireTableDataChanged();
    }

    public Tarefa getTarefaNaLinha(int linha) {
        if (linha < 0 || linha >= tarefas.size()) return null;
        return tarefas.get(linha);
    }

    @Override
    public int getRowCount() {
        return tarefas.size();
    }

    @Override
    public int getColumnCount() {
        return COLUNAS.length;
    }

    @Override
    public String getColumnName(int coluna) {
        return COLUNAS[coluna];
    }

    @Override
    public Object getValueAt(int linha, int coluna) {
        Tarefa t = tarefas.get(linha);
        return switch (coluna) {
            case 0 -> t.getId();
            case 1 -> t.getTitulo();
            case 2 -> t.getPrioridade().name();
            case 3 -> t.getDataLimite().format(FORMATO_DATA);
            case 4 -> t.isConcluida() ? "Concluída" : "Pendente";
            case 5 -> t.getDescricao();
            default -> "";
        };
    }

    @Override
    public boolean isCellEditable(int linha, int coluna) {
        return false; // edição feita através do formulário, não diretamente na tabela
    }
}
