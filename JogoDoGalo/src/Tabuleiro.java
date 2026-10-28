/**
 * Representa o tabuleiro do jogo do galo (3x3) e as regras básicas:
 * jogar numa casa, verificar se há vencedor e verificar empate.
 */
public class Tabuleiro {

    public static final char VAZIO = '-';

    private final char[][] casas;

    public Tabuleiro() {
        casas = new char[3][3];
        limpar();
    }

    public void limpar() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                casas[i][j] = VAZIO;
            }
        }
    }

    /**
     * Tenta jogar na casa (linha, coluna). Devolve true se a jogada foi
     * feita, ou false se a posição for inválida ou já estiver ocupada.
     */
    public boolean jogar(int linha, int coluna, char simbolo) {
        if (linha < 0 || linha > 2 || coluna < 0 || coluna > 2) {
            return false;
        }
        if (casas[linha][coluna] != VAZIO) {
            return false;
        }
        casas[linha][coluna] = simbolo;
        return true;
    }

    public char getCasa(int linha, int coluna) {
        return casas[linha][coluna];
    }

    /** Repõe uma casa como vazia. Usado pela IA para simular jogadas sem as confirmar. */
    public void limparCasa(int linha, int coluna) {
        casas[linha][coluna] = VAZIO;
    }

    public boolean casaVazia(int linha, int coluna) {
        return casas[linha][coluna] == VAZIO;
    }

    public boolean tabuleiroCheio() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (casas[i][j] == VAZIO) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Verifica se há um vencedor. Devolve 'X' ou 'O' se alguém já ganhou,
     * ou VAZIO se ainda não houver vencedor.
     */
    public char verificarVencedor() {
        // linhas e colunas
        for (int i = 0; i < 3; i++) {
            if (casas[i][0] != VAZIO && casas[i][0] == casas[i][1] && casas[i][1] == casas[i][2]) {
                return casas[i][0];
            }
            if (casas[0][i] != VAZIO && casas[0][i] == casas[1][i] && casas[1][i] == casas[2][i]) {
                return casas[0][i];
            }
        }
        // diagonais
        if (casas[0][0] != VAZIO && casas[0][0] == casas[1][1] && casas[1][1] == casas[2][2]) {
            return casas[0][0];
        }
        if (casas[0][2] != VAZIO && casas[0][2] == casas[1][1] && casas[1][1] == casas[2][0]) {
            return casas[0][2];
        }
        return VAZIO;
    }

    /** Devolve as 3 posições [linha,coluna] da linha vencedora, ou null se não houver. */
    public int[][] getLinhaVencedora() {
        for (int i = 0; i < 3; i++) {
            if (casas[i][0] != VAZIO && casas[i][0] == casas[i][1] && casas[i][1] == casas[i][2]) {
                return new int[][]{{i, 0}, {i, 1}, {i, 2}};
            }
            if (casas[0][i] != VAZIO && casas[0][i] == casas[1][i] && casas[1][i] == casas[2][i]) {
                return new int[][]{{0, i}, {1, i}, {2, i}};
            }
        }
        if (casas[0][0] != VAZIO && casas[0][0] == casas[1][1] && casas[1][1] == casas[2][2]) {
            return new int[][]{{0, 0}, {1, 1}, {2, 2}};
        }
        if (casas[0][2] != VAZIO && casas[0][2] == casas[1][1] && casas[1][1] == casas[2][0]) {
            return new int[][]{{0, 2}, {1, 1}, {2, 0}};
        }
        return null;
    }

    /** Representação do tabuleiro em texto, para a versão de consola. */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 3; i++) {
            sb.append(" ").append(casas[i][0]).append(" | ").append(casas[i][1]).append(" | ").append(casas[i][2]).append(" \n");
            if (i < 2) {
                sb.append("---+---+---\n");
            }
        }
        return sb.toString();
    }
}
