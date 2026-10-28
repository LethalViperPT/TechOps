import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Grava e carrega a lista de veículos num ficheiro de texto local
 * (veiculos.txt), uma linha por veículo. Cada linha começa pelo tipo
 * (Carro / Mota / Autocarro), o que permite reconstruir a subclasse
 * correta ao carregar.
 */
public class PersistenciaVeiculos {

    private final String caminhoFicheiro;

    public PersistenciaVeiculos(String caminhoFicheiro) {
        this.caminhoFicheiro = caminhoFicheiro;
    }

    public void guardar(List<Veiculo> veiculos) {
        try (BufferedWriter writer = Files.newBufferedWriter(
                Paths.get(caminhoFicheiro), StandardCharsets.UTF_8)) {
            for (Veiculo v : veiculos) {
                writer.write(v.paraLinhaFicheiro());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Erro ao guardar os dados: " + e.getMessage());
        }
    }

    /**
     * Carrega os veículos do ficheiro. Se o ficheiro ainda não existir
     * (primeira execução), devolve uma lista vazia. Linhas corrompidas ou
     * de um tipo desconhecido são ignoradas, com aviso, para não travar a
     * aplicação.
     */
    public List<Veiculo> carregar() {
        List<Veiculo> veiculos = new ArrayList<>();
        Path caminho = Paths.get(caminhoFicheiro);

        if (!Files.exists(caminho)) {
            return veiculos;
        }

        try (BufferedReader reader = Files.newBufferedReader(caminho, StandardCharsets.UTF_8)) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                if (linha.isBlank()) continue;
                try {
                    veiculos.add(interpretarLinha(linha));
                } catch (Exception e) {
                    System.out.println("Aviso: linha invalida ignorada no ficheiro de dados.");
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao carregar os dados: " + e.getMessage());
        }

        return veiculos;
    }

    private Veiculo interpretarLinha(String linha) {
        String[] campos = linha.split(";", -1);
        String tipo = campos[0];
        String matricula = campos[1];
        String marca = campos[2];
        String modelo = campos[3];
        int ano = Integer.parseInt(campos[4]);
        String cor = campos[5];
        double preco = Double.parseDouble(campos[6]);

        return switch (tipo) {
            case "Carro" -> new Carro(matricula, marca, modelo, ano, cor, preco,
                    Integer.parseInt(campos[7]), campos[8]);
            case "Mota" -> new Mota(matricula, marca, modelo, ano, cor, preco,
                    Integer.parseInt(campos[7]));
            case "Autocarro" -> new Autocarro(matricula, marca, modelo, ano, cor, preco,
                    Integer.parseInt(campos[7]));
            default -> throw new IllegalArgumentException("Tipo de veiculo desconhecido: " + tipo);
        };
    }
}
