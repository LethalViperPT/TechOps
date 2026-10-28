import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * IA simples para o modo "contra o computador". Não usa nenhum algoritmo
 * complicado — segue só um conjunto de prioridades parecido com o que uma
 * pessoa faria a jogar:
 *   1. Se puder ganhar já, ganha.
 *   2. Se o adversário puder ganhar na próxima jogada, bloqueia.
 *   3. Se o centro estiver livre, joga no centro.
 *   4. Se houver um canto livre, joga num canto.
 *   5. Caso contrário, joga numa casa livre qualquer.
 */
public class ComputadorIA {

    private final Random random = new Random();

    public int[] escolherJogada(Tabuleiro tabuleiro, char simboloComputador, char simboloAdversario) {

        int[] jogadaVencedora = procurarJogadaQueGanha(tabuleiro, simboloComputador);
        if (jogadaVencedora != null) {
            return jogadaVencedora;
        }

        int[] jogadaDeBloqueio = procurarJogadaQueGanha(tabuleiro, simboloAdversario);
        if (jogadaDeBloqueio != null) {
            return jogadaDeBloqueio;
        }

        if (tabuleiro.casaVazia(1, 1)) {
            return new int[]{1, 1};
        }

        int[][] cantos = {{0, 0}, {0, 2}, {2, 0}, {2, 2}};
        List<int[]> cantosLivres = new ArrayList<>();
        for (int[] canto : cantos) {
            if (tabuleiro.casaVazia(canto[0], canto[1])) {
                cantosLivres.add(canto);
            }
        }
        if (!cantosLivres.isEmpty()) {
            return cantosLivres.get(random.nextInt(cantosLivres.size()));
        }

        List<int[]> livres = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (tabuleiro.casaVazia(i, j)) {
                    livres.add(new int[]{i, j});
                }
            }
        }
        if (livres.isEmpty()) {
            return null; // tabuleiro cheio, não há jogada possível
        }
        return livres.get(random.nextInt(livres.size()));
    }

    /**
     * Testa cada casa livre: se colocar "simbolo" nessa casa resultasse
     * numa vitória, devolve essa posição. Usado tanto para ver se o
     * computador pode ganhar, como para ver se tem de bloquear o adversário.
     */
    private int[] procurarJogadaQueGanha(Tabuleiro tabuleiro, char simbolo) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (!tabuleiro.casaVazia(i, j)) {
                    continue;
                }
                tabuleiro.jogar(i, j, simbolo);
                boolean ganha = tabuleiro.verificarVencedor() == simbolo;
                tabuleiro.limparCasa(i, j);
                if (ganha) {
                    return new int[]{i, j};
                }
            }
        }
        return null;
    }
}
