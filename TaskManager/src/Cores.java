/**
 * Códigos ANSI para dar cor ao texto apresentado na consola.
 * Suportado nativamente pela consola do IntelliJ IDEA e por terminais Linux/macOS.
 */
public final class Cores {

    public static final String RESET = "\u001B[0m";
    public static final String BOLD = "\u001B[1m";

    public static final String VERMELHO = "\u001B[31m";
    public static final String VERDE = "\u001B[32m";
    public static final String AMARELO = "\u001B[33m";
    public static final String AZUL = "\u001B[34m";
    public static final String CIANO = "\u001B[36m";
    public static final String CINZENTO = "\u001B[90m";

    private Cores() {
        // classe utilitária, sem instâncias
    }

    /** Envolve o texto com uma cor e repõe a cor por omissão no final. */
    public static String colorir(String texto, String cor) {
        return cor + texto + RESET;
    }

    /** Devolve a cor associada a cada nível de prioridade. */
    public static String corDaPrioridade(Prioridade prioridade) {
        switch (prioridade) {
            case ALTA:
                return VERMELHO;
            case MEDIA:
                return AMARELO;
            case BAIXA:
            default:
                return VERDE;
        }
    }
}
