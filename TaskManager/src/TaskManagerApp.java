import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

/**
 * Ponto de entrada da aplicação. Apresenta o menu em modo consola e
 * faz a ponte entre o utilizador e o GestorTarefas.
 */
public class TaskManagerApp {

    private static final String FICHEIRO_DADOS = "tarefas.txt";
    private static final DateTimeFormatter FORMATO_DATA = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private final Scanner scanner = new Scanner(System.in);
    private final PersistenciaTarefas persistencia = new PersistenciaTarefas(FICHEIRO_DADOS);
    private final GestorTarefas gestor;

    public TaskManagerApp() {
        this.gestor = new GestorTarefas(persistencia.carregar());
    }

    public static void main(String[] args) {
        // Garante que os caracteres especiais (bordas da tabela, ✔, ✘, ○)
        // são sempre mostrados corretamente, independentemente da
        // codificação predefinida do sistema operativo.
        System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));
        new TaskManagerApp().executar();
    }

    private void executar() {
        System.out.println(Cores.CIANO + "╔═══════════════════════════════════════╗" + Cores.RESET);
        System.out.println(Cores.CIANO + "║" + Cores.RESET + Cores.BOLD + "     SISTEMA DE GESTAO DE TAREFAS      " + Cores.RESET + Cores.CIANO + "║" + Cores.RESET);
        System.out.println(Cores.CIANO + "╚═══════════════════════════════════════╝" + Cores.RESET);

        boolean continuar = true;
        while (continuar) {
            mostrarMenu();
            int opcao = lerOpcaoMenu();
            switch (opcao) {
                case 1 -> criarTarefa();
                case 2 -> listarTarefas(gestor.listarTarefas());
                case 3 -> editarTarefa();
                case 4 -> marcarConcluida();
                case 5 -> eliminarTarefa();
                case 6 -> filtrarTarefas();
                case 7 -> ordenarTarefas();
                case 0 -> {
                    persistencia.guardar(gestor.listarTarefas());
                    System.out.println(Cores.colorir("✔ Dados guardados. Ate' a proxima!", Cores.VERDE));
                    continuar = false;
                }
                default -> System.out.println(Cores.colorir("✘ Opcao invalida. Tenta novamente.", Cores.VERMELHO));
            }
        }
        scanner.close();
    }

    private void mostrarMenu() {
        System.out.println();
        System.out.println(Cores.CINZENTO + "───────────────── MENU ─────────────────" + Cores.RESET);
        imprimirOpcao("1", "Criar tarefa");
        imprimirOpcao("2", "Listar tarefas");
        imprimirOpcao("3", "Editar tarefa");
        imprimirOpcao("4", "Marcar tarefa como concluida");
        imprimirOpcao("5", "Eliminar tarefa");
        imprimirOpcao("6", "Filtrar tarefas");
        imprimirOpcao("7", "Ordenar tarefas");
        imprimirOpcao("0", "Guardar e sair");
        System.out.print(Cores.BOLD + "Escolhe uma opcao: " + Cores.RESET);
    }

    private void imprimirOpcao(String numero, String descricao) {
        System.out.println(Cores.colorir(numero, Cores.AZUL) + " - " + descricao);
    }

    private int lerOpcaoMenu() {
        String linha = scanner.nextLine().trim();
        try {
            return Integer.parseInt(linha);
        } catch (NumberFormatException e) {
            return -1; // cai no "default" do switch, sem rebentar o programa
        }
    }

    // --- Ações do menu ---

    private void criarTarefa() {
        System.out.println("\n-- Nova tarefa --");
        String titulo = lerTitulo("Titulo: ");

        System.out.print("Descricao: ");
        String descricao = scanner.nextLine().trim();

        LocalDate data = lerData("Data limite (dd/MM/yyyy): ");
        Prioridade prioridade = lerPrioridade("Prioridade (1-Baixa, 2-Media, 3-Alta): ");

        Tarefa nova = gestor.adicionarTarefa(titulo, descricao, data, prioridade);
        persistencia.guardar(gestor.listarTarefas());
        System.out.println(Cores.colorir("✔ Tarefa #" + nova.getId() + " criada com sucesso.", Cores.VERDE));
    }

    // Larguras (em caracteres) de cada coluna da tabela de tarefas
    private static final int LARG_ID = 4;
    private static final int LARG_ESTADO = 3;
    private static final int LARG_TITULO = 22;
    private static final int LARG_PRIORIDADE = 10;
    private static final int LARG_PRAZO = 12;
    private static final int LARG_DESCRICAO = 26;

    private void listarTarefas(List<Tarefa> tarefas) {
        System.out.println();
        if (tarefas.isEmpty()) {
            System.out.println(Cores.colorir("Sem tarefas para mostrar.", Cores.CINZENTO));
            return;
        }

        long concluidas = tarefas.stream().filter(Tarefa::isConcluida).count();
        System.out.println(Cores.colorir(
                String.format("Total: %d tarefa(s)  ·  %d concluida(s)  ·  %d pendente(s)",
                        tarefas.size(), concluidas, tarefas.size() - concluidas),
                Cores.CINZENTO));

        imprimirLinhaTabela('┌', '┬', '┐');
        imprimirCabecalhoTabela();
        imprimirLinhaTabela('├', '┼', '┤');
        for (Tarefa t : tarefas) {
            imprimirLinhaTarefa(t);
        }
        imprimirLinhaTabela('└', '┴', '┘');
    }

    private void imprimirLinhaTabela(char esquerda, char meio, char direita) {
        int[] larguras = { LARG_ID, LARG_ESTADO, LARG_TITULO, LARG_PRIORIDADE, LARG_PRAZO, LARG_DESCRICAO };
        StringBuilder sb = new StringBuilder();
        sb.append(esquerda);
        for (int i = 0; i < larguras.length; i++) {
            sb.append("─".repeat(larguras[i] + 2));
            sb.append(i == larguras.length - 1 ? direita : meio);
        }
        System.out.println(Cores.CINZENTO + sb + Cores.RESET);
    }

    private void imprimirCabecalhoTabela() {
        System.out.println(Cores.CINZENTO + "│ " + Cores.RESET + Cores.BOLD +
                celula("ID", LARG_ID) + Cores.CINZENTO + " │ " + Cores.RESET + Cores.BOLD +
                celula("St", LARG_ESTADO) + Cores.CINZENTO + " │ " + Cores.RESET + Cores.BOLD +
                celula("Titulo", LARG_TITULO) + Cores.CINZENTO + " │ " + Cores.RESET + Cores.BOLD +
                celula("Prioridade", LARG_PRIORIDADE) + Cores.CINZENTO + " │ " + Cores.RESET + Cores.BOLD +
                celula("Prazo", LARG_PRAZO) + Cores.CINZENTO + " │ " + Cores.RESET + Cores.BOLD +
                celula("Descricao", LARG_DESCRICAO) + Cores.CINZENTO + " │" + Cores.RESET);
    }

    private void imprimirLinhaTarefa(Tarefa t) {
        String iconeEstado = t.isConcluida() ? "✔" : "○";
        String corEstado = t.isConcluida() ? Cores.VERDE : Cores.CINZENTO;
        String estado = Cores.colorir(celula(iconeEstado, LARG_ESTADO), corEstado);

        String prioridadeColorida = Cores.colorir(celula(t.getPrioridade().name(), LARG_PRIORIDADE), Cores.corDaPrioridade(t.getPrioridade()));
        String tituloFmt = t.isConcluida()
                ? Cores.colorir(celula(t.getTitulo(), LARG_TITULO), Cores.CINZENTO)
                : celula(t.getTitulo(), LARG_TITULO);

        System.out.println(Cores.CINZENTO + "│ " + Cores.RESET +
                celula(String.valueOf(t.getId()), LARG_ID) + Cores.CINZENTO + " │ " + Cores.RESET +
                estado + Cores.CINZENTO + " │ " + Cores.RESET +
                tituloFmt + Cores.CINZENTO + " │ " + Cores.RESET +
                prioridadeColorida + Cores.CINZENTO + " │ " + Cores.RESET +
                celula(t.getDataLimite().format(FORMATO_DATA), LARG_PRAZO) + Cores.CINZENTO + " │ " + Cores.RESET +
                celula(t.getDescricao(), LARG_DESCRICAO) + Cores.CINZENTO + " │" + Cores.RESET);
    }

    /** Alinha à esquerda e trunca com reticências se o texto exceder a largura da coluna. */
    private String celula(String texto, int largura) {
        if (texto.length() > largura) {
            return texto.substring(0, largura - 1) + "…";
        }
        return String.format("%-" + largura + "s", texto);
    }

    private void editarTarefa() {
        int id = lerInteiro("ID da tarefa a editar: ");

        System.out.print("Novo titulo (Enter para manter): ");
        String titulo = scanner.nextLine().trim();

        System.out.print("Nova descricao (Enter para manter): ");
        String descricao = scanner.nextLine().trim();

        System.out.print("Nova data dd/MM/yyyy (Enter para manter): ");
        String dataTexto = scanner.nextLine().trim();
        LocalDate data = null;
        if (!dataTexto.isBlank()) {
            try {
                data = LocalDate.parse(dataTexto, FORMATO_DATA);
            } catch (DateTimeParseException e) {
                System.out.println("Data invalida, mantida a anterior.");
            }
        }

        System.out.print("Nova prioridade 1/2/3 (Enter para manter): ");
        String prioridadeTexto = scanner.nextLine().trim();
        Prioridade prioridade = null;
        if (!prioridadeTexto.isBlank()) {
            try {
                prioridade = Prioridade.fromTexto(prioridadeTexto);
            } catch (IllegalArgumentException e) {
                System.out.println("Prioridade invalida, mantida a anterior.");
            }
        }

        boolean sucesso = gestor.editarTarefa(id, titulo, descricao, data, prioridade);
        if (sucesso) {
            persistencia.guardar(gestor.listarTarefas());
            System.out.println(Cores.colorir("✔ Tarefa atualizada com sucesso.", Cores.VERDE));
        } else {
            System.out.println(Cores.colorir("✘ Nao foi encontrada nenhuma tarefa com esse ID.", Cores.VERMELHO));
        }
    }

    private void marcarConcluida() {
        int id = lerInteiro("ID da tarefa a marcar como concluida: ");
        boolean sucesso = gestor.marcarConcluida(id);
        if (sucesso) {
            persistencia.guardar(gestor.listarTarefas());
            System.out.println(Cores.colorir("✔ Tarefa marcada como concluida.", Cores.VERDE));
        } else {
            System.out.println(Cores.colorir("✘ Nao foi encontrada nenhuma tarefa com esse ID.", Cores.VERMELHO));
        }
    }

    private void eliminarTarefa() {
        int id = lerInteiro("ID da tarefa a eliminar: ");
        boolean sucesso = gestor.removerTarefa(id);
        if (sucesso) {
            persistencia.guardar(gestor.listarTarefas());
            System.out.println(Cores.colorir("✔ Tarefa eliminada.", Cores.VERDE));
        } else {
            System.out.println(Cores.colorir("✘ Nao foi encontrada nenhuma tarefa com esse ID.", Cores.VERMELHO));
        }
    }

    private void filtrarTarefas() {
        System.out.print("Filtrar por prioridade? (1-Baixa,2-Media,3-Alta, Enter para ignorar): ");
        String prioridadeTexto = scanner.nextLine().trim();
        Prioridade prioridade = null;
        if (!prioridadeTexto.isBlank()) {
            try {
                prioridade = Prioridade.fromTexto(prioridadeTexto);
            } catch (IllegalArgumentException e) {
                System.out.println("Prioridade invalida, filtro ignorado.");
            }
        }

        System.out.print("Filtrar por estado? (c-concluidas, n-nao concluidas, Enter para ignorar): ");
        String estadoTexto = scanner.nextLine().trim().toLowerCase();
        Boolean concluida = null;
        if (estadoTexto.equals("c")) concluida = true;
        else if (estadoTexto.equals("n")) concluida = false;

        listarTarefas(gestor.filtrarTarefas(prioridade, concluida));
    }

    private void ordenarTarefas() {
        System.out.print("Ordenar por (data / prioridade / titulo): ");
        String criterio = scanner.nextLine().trim();
        try {
            listarTarefas(gestor.ordenarTarefas(criterio));
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    // --- Auxiliares de leitura com validação ---

    private int lerInteiro(String mensagem) {
        while (true) {
            System.out.print(mensagem);
            String linha = scanner.nextLine().trim();
            try {
                return Integer.parseInt(linha);
            } catch (NumberFormatException e) {
                System.out.println("Por favor introduz um numero valido.");
            }
        }
    }

    private String lerTitulo(String mensagem) {
        while (true) {
            System.out.print(mensagem);
            String titulo = scanner.nextLine().trim();
            if (!titulo.isBlank()) {
                return titulo;
            }
            System.out.println(Cores.colorir("✘ O titulo nao pode ser vazio. Tenta novamente.", Cores.VERMELHO));
        }
    }

    private LocalDate lerData(String mensagem) {
        while (true) {
            System.out.print(mensagem);
            String linha = scanner.nextLine().trim();
            try {
                return LocalDate.parse(linha, FORMATO_DATA);
            } catch (DateTimeParseException e) {
                System.out.println("Formato invalido. Usa dd/MM/yyyy (ex: 31/12/2026).");
            }
        }
    }

    private Prioridade lerPrioridade(String mensagem) {
        while (true) {
            System.out.print(mensagem);
            String linha = scanner.nextLine().trim();
            try {
                return Prioridade.fromTexto(linha);
            } catch (IllegalArgumentException e) {
                System.out.println("Prioridade invalida. Usa 1, 2 ou 3.");
            }
        }
    }
}
