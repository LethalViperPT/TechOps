import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Scanner;

/**
 * Gestão de Veículos - versão em modo consola.
 * Permite adicionar carros, motas e autocarros, listar todos os veículos
 * (ou só de um tipo) e remover um veículo pela matrícula.
 */
public class GaragemConsola {

    private static final String FICHEIRO_DADOS = "veiculos.txt";

    private final Scanner scanner = new Scanner(System.in);
    private final PersistenciaVeiculos persistencia = new PersistenciaVeiculos(FICHEIRO_DADOS);
    private final GestorVeiculos gestor = new GestorVeiculos();

    public GaragemConsola() {
        gestor.addAll(persistencia.carregar());
    }

    public static void main(String[] args) {
        System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));
        new GaragemConsola().executar();
    }

    private void executar() {
        System.out.println("=========================================");
        System.out.println("        GESTAO DE VEICULOS");
        System.out.println("=========================================");

        boolean continuar = true;
        while (continuar) {
            mostrarMenu();
            int opcao = lerOpcao();
            switch (opcao) {
                case 1 -> adicionarVeiculo();
                case 2 -> listarVeiculos(gestor.listarTodos());
                case 3 -> listarPorTipo();
                case 4 -> removerVeiculo();
                case 5 -> pesquisarVeiculos();
                case 0 -> {
                    System.out.println("Ate' a proxima!");
                    continuar = false;
                }
                default -> System.out.println("Opcao invalida. Tenta novamente.");
            }
        }
        scanner.close();
    }

    private void mostrarMenu() {
        System.out.println();
        System.out.println("------------- MENU -------------");
        System.out.println("1 - Adicionar veiculo");
        System.out.println("2 - Listar todos os veiculos");
        System.out.println("3 - Listar veiculos por tipo");
        System.out.println("4 - Remover veiculo");
        System.out.println("5 - Pesquisar veiculos");
        System.out.println("0 - Sair");
        System.out.print("Escolhe uma opcao: ");
    }

    private int lerOpcao() {
        String linha = scanner.nextLine().trim();
        try {
            return Integer.parseInt(linha);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private void adicionarVeiculo() {
        System.out.println("\n-- Novo veiculo --");
        System.out.println("Tipo de veiculo:");
        System.out.println("1 - Carro");
        System.out.println("2 - Mota");
        System.out.println("3 - Autocarro");
        System.out.print("Escolhe o tipo: ");
        int tipo = lerNumeroEntre("Escolhe o tipo: ", 1, 3);

        String matricula = lerTexto("Matricula: ");
        if (gestor.procurarPorMatricula(matricula) != null) {
            System.out.println("Ja existe um veiculo com essa matricula. Operacao cancelada.");
            return;
        }
        String marca = lerTexto("Marca: ");
        String modelo = lerTexto("Modelo: ");
        int ano = lerNumero("Ano: ");
        String cor = lerTexto("Cor: ");
        double preco = lerDecimal("Preco (EUR): ");

        Veiculo veiculo;
        switch (tipo) {
            case 1 -> {
                int portas = lerNumero("Numero de portas: ");
                String combustivel = lerTexto("Tipo de combustivel (Gasolina/Gasoleo/Eletrico/Hibrido): ");
                veiculo = new Carro(matricula, marca, modelo, ano, cor, preco, portas, combustivel);
            }
            case 2 -> {
                int cilindrada = lerNumero("Cilindrada (cc): ");
                veiculo = new Mota(matricula, marca, modelo, ano, cor, preco, cilindrada);
            }
            default -> {
                int capacidade = lerNumero("Capacidade de passageiros: ");
                veiculo = new Autocarro(matricula, marca, modelo, ano, cor, preco, capacidade);
            }
        }

        gestor.add(veiculo);
        persistencia.guardar(gestor);
        System.out.println("Veiculo adicionado com sucesso:");
        System.out.println(veiculo);
    }

    private void listarVeiculos(List<Veiculo> veiculos) {
        System.out.println();
        if (veiculos.isEmpty()) {
            System.out.println("Nao ha veiculos para mostrar.");
            return;
        }
        System.out.println("Total: " + veiculos.size() + " veiculo(s)");
        for (Veiculo v : veiculos) {
            System.out.println(v);
        }
    }

    private void listarPorTipo() {
        String tipo = lerTexto("Tipo a listar (Carro/Mota/Autocarro): ");
        listarVeiculos(gestor.listarPorTipo(tipo));
    }

    private void removerVeiculo() {
        String matricula = lerTexto("Matricula do veiculo a remover: ");
        boolean removido = gestor.removerPorMatricula(matricula);
        if (removido) {
            persistencia.guardar(gestor);
            System.out.println("Veiculo removido.");
        } else {
            System.out.println("Nao foi encontrado nenhum veiculo com essa matricula.");
        }
    }

    private void pesquisarVeiculos() {
        String termo = lerTexto("Pesquisar (matricula, marca, modelo, cor ou tipo): ");
        List<Veiculo> resultado = gestor.pesquisar(termo);
        System.out.println();
        if (resultado.isEmpty()) {
            System.out.println("Nenhum veiculo corresponde a pesquisa \"" + termo + "\".");
            return;
        }
        System.out.println(resultado.size() + " veiculo(s) encontrado(s) para \"" + termo + "\":");
        for (Veiculo v : resultado) {
            System.out.println(v);
        }
    }

    // --- Auxiliares de leitura com validação ---

    private String lerTexto(String mensagem) {
        while (true) {
            System.out.print(mensagem);
            String texto = scanner.nextLine().trim();
            if (!texto.isBlank()) {
                return texto;
            }
            System.out.println("Este campo nao pode ficar vazio.");
        }
    }

    private int lerNumero(String mensagem) {
        while (true) {
            System.out.print(mensagem);
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Introduz um numero valido.");
            }
        }
    }

    private int lerNumeroEntre(String mensagem, int min, int max) {
        while (true) {
            String linha = scanner.nextLine().trim();
            try {
                int valor = Integer.parseInt(linha);
                if (valor >= min && valor <= max) {
                    return valor;
                }
            } catch (NumberFormatException ignored) {
                // cai para a mensagem de erro abaixo
            }
            System.out.print("Valor invalido. Escolhe entre " + min + " e " + max + ": ");
        }
    }

    private double lerDecimal(String mensagem) {
        while (true) {
            System.out.print(mensagem);
            try {
                return Double.parseDouble(scanner.nextLine().trim().replace(",", "."));
            } catch (NumberFormatException e) {
                System.out.println("Introduz um valor numerico valido (ex: 15000 ou 15000.50).");
            }
        }
    }
}
