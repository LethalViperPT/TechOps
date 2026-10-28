import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/**
 * Jogo do Galo - versão em modo consola.
 * Suporta dois modos: 2 jogadores no mesmo computador, ou 1 jogador contra o computador.
 */
public class JogoConsola {

    private final Scanner scanner = new Scanner(System.in);
    private final Tabuleiro tabuleiro = new Tabuleiro();
    private final ComputadorIA ia = new ComputadorIA();

    public static void main(String[] args) {
        System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));
        new JogoConsola().executar();
    }

    private void executar() {
        System.out.println("=====================================");
        System.out.println("          JOGO DO GALO");
        System.out.println("=====================================");

        boolean continuarAJogar = true;
        while (continuarAJogar) {
            int modo = escolherModo();
            if (modo == 0) {
                System.out.println("Ate' a proxima!");
                break;
            }
            jogarPartida(modo);
            continuarAJogar = perguntarSeQuerJogarOutraVez();
        }

        scanner.close();
    }

    private int escolherModo() {
        System.out.println();
        System.out.println("1 - Dois jogadores");
        System.out.println("2 - Jogador contra computador");
        System.out.println("0 - Sair");
        System.out.print("Escolhe o modo: ");

        while (true) {
            String linha = scanner.nextLine().trim();
            if (linha.equals("0") || linha.equals("1") || linha.equals("2")) {
                return Integer.parseInt(linha);
            }
            System.out.print("Opcao invalida. Escolhe 0, 1 ou 2: ");
        }
    }

    private void jogarPartida(int modo) {
        tabuleiro.limpar();
        char jogadorAtual = 'X';
        boolean contraComputador = (modo == 2);

        System.out.println("\nJogador 1 = X" + (contraComputador ? "   Computador = O" : "   Jogador 2 = O"));

        while (true) {
            System.out.println();
            System.out.println(tabuleiro);

            if (contraComputador && jogadorAtual == 'O') {
                System.out.println("O computador esta a pensar...");
                int[] jogada = ia.escolherJogada(tabuleiro, 'O', 'X');
                tabuleiro.jogar(jogada[0], jogada[1], 'O');
                System.out.println("O computador jogou na linha " + (jogada[0] + 1) + ", coluna " + (jogada[1] + 1) + ".");
            } else {
                pedirJogada(jogadorAtual);
            }

            char vencedor = tabuleiro.verificarVencedor();
            if (vencedor != Tabuleiro.VAZIO) {
                System.out.println();
                System.out.println(tabuleiro);
                System.out.println("O jogador " + vencedor + " ganhou!");
                return;
            }
            if (tabuleiro.tabuleiroCheio()) {
                System.out.println();
                System.out.println(tabuleiro);
                System.out.println("Empate!");
                return;
            }

            jogadorAtual = (jogadorAtual == 'X') ? 'O' : 'X';
        }
    }

    private void pedirJogada(char jogador) {
        while (true) {
            System.out.print("Jogador " + jogador + ", escolhe a linha (1-3): ");
            int linha = lerNumero();
            System.out.print("Jogador " + jogador + ", escolhe a coluna (1-3): ");
            int coluna = lerNumero();

            if (linha < 1 || linha > 3 || coluna < 1 || coluna > 3) {
                System.out.println("Linha/coluna tem de ser entre 1 e 3. Tenta outra vez.");
                continue;
            }
            boolean jogadaFeita = tabuleiro.jogar(linha - 1, coluna - 1, jogador);
            if (!jogadaFeita) {
                System.out.println("Essa casa ja esta ocupada. Escolhe outra.");
                continue;
            }
            return;
        }
    }

    private int lerNumero() {
        while (true) {
            String linha = scanner.nextLine().trim();
            try {
                return Integer.parseInt(linha);
            } catch (NumberFormatException e) {
                System.out.print("Introduz um numero valido: ");
            }
        }
    }

    private boolean perguntarSeQuerJogarOutraVez() {
        System.out.print("\nQueres jogar outra vez? (s/n): ");
        String resposta = scanner.nextLine().trim().toLowerCase();
        return resposta.equals("s") || resposta.equals("sim");
    }
}
