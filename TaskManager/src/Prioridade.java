/**
 * Representa o nível de prioridade de uma tarefa.
 */
public enum Prioridade {
    BAIXA,
    MEDIA,
    ALTA;

    /**
     * Converte um texto introduzido pelo utilizador (ex: "media", "MÉDIA", "2")
     * na respetiva constante do enum. Lança IllegalArgumentException se não
     * reconhecer o valor.
     */
    public static Prioridade fromTexto(String texto) {
        String t = texto.trim().toUpperCase()
                .replace("É", "E").replace("Á", "A");
        switch (t) {
            case "1":
            case "BAIXA":
                return BAIXA;
            case "2":
            case "MEDIA":
                return MEDIA;
            case "3":
            case "ALTA":
                return ALTA;
            default:
                throw new IllegalArgumentException("Prioridade inválida: " + texto);
        }
    }
}
