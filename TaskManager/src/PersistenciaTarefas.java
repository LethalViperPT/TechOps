import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Responsável por guardar e carregar a lista de tarefas num ficheiro de texto local.
 */
public class PersistenciaTarefas {

    private final String caminhoFicheiro;

    public PersistenciaTarefas(String caminhoFicheiro) {
        this.caminhoFicheiro = caminhoFicheiro;
    }

    /**
     * Grava a lista de tarefas no ficheiro, uma tarefa por linha.
     */
    public void guardar(List<Tarefa> tarefas) {
        try (BufferedWriter writer = Files.newBufferedWriter(
                Paths.get(caminhoFicheiro), StandardCharsets.UTF_8)) {
            for (Tarefa tarefa : tarefas) {
                writer.write(tarefa.paraLinhaFicheiro());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Erro ao guardar os dados: " + e.getMessage());
        }
    }

    /**
     * Carrega a lista de tarefas a partir do ficheiro.
     * Se o ficheiro não existir ainda, devolve uma lista vazia (primeira execução).
     * Linhas corrompidas são ignoradas, com aviso, para não travar a aplicação.
     */
    public List<Tarefa> carregar() {
        List<Tarefa> tarefas = new ArrayList<>();
        Path caminho = Paths.get(caminhoFicheiro);

        if (!Files.exists(caminho)) {
            return tarefas; // primeira execução: ainda não há ficheiro
        }

        try (BufferedReader reader = Files.newBufferedReader(caminho, StandardCharsets.UTF_8)) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                if (linha.isBlank()) continue;
                try {
                    tarefas.add(Tarefa.deLinhaFicheiro(linha));
                } catch (Exception e) {
                    System.out.println("Aviso: linha inválida ignorada no ficheiro de dados.");
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao carregar os dados: " + e.getMessage());
        }

        return tarefas;
    }
}
