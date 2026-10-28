import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * Jogo do Galo - versão com interface gráfica (Swing).
 * Reutiliza as mesmas classes Tabuleiro e ComputadorIA da versão de consola.
 */
public class JogoGUI extends JFrame {

    private static final Color COR_X = new Color(0x1E, 0x40, 0xAF);
    private static final Color COR_O = new Color(0xB9, 0x1C, 0x1C);
    private static final Color COR_VENCEDORA = new Color(0xD9, 0xF2, 0xD9);
    private static final Color COR_FUNDO_CASA = Color.WHITE;

    private final Tabuleiro tabuleiro = new Tabuleiro();
    private final ComputadorIA ia = new ComputadorIA();
    private final JButton[][] botoes = new JButton[3][3];
    private final JLabel labelEstado = new JLabel("Vez do jogador X", SwingConstants.CENTER);
    private final JComboBox<String> comboModo = new JComboBox<>(new String[]{"Dois jogadores", "Contra o computador"});

    private char jogadorAtual = 'X';
    private boolean jogoTerminado = false;

    public JogoGUI() {
        super("Jogo do Galo");
        aplicarTemaNimbus();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        add(construirPainelTopo(), BorderLayout.NORTH);
        add(construirTabuleiroVisual(), BorderLayout.CENTER);
        add(labelEstado, BorderLayout.SOUTH);

        labelEstado.setFont(labelEstado.getFont().deriveFont(Font.BOLD, 16f));
        labelEstado.setBorder(BorderFactory.createEmptyBorder(8, 8, 12, 8));

        setSize(420, 480);
        setMinimumSize(new Dimension(360, 420));
        setLocationRelativeTo(null);
    }

    private void aplicarTemaNimbus() {
        // Em Linux, o tema do sistema (GTK) tem um bug conhecido em que o
        // clique do rato em JComboBox nem sempre regista. O Nimbus evita isso.
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ignorada) {
            // mantém o tema por omissão do Swing se o Nimbus não estiver disponível
        }
    }

    private JPanel construirPainelTopo() {
        JPanel painel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        painel.add(new JLabel("Modo:"));
        painel.add(comboModo);

        JButton botaoNovoJogo = new JButton("Novo Jogo");
        botaoNovoJogo.addActionListener(this::novoJogo);
        painel.add(botaoNovoJogo);

        return painel;
    }

    /**
     * Botão de casa do tabuleiro. O Nimbus (tema usado nesta aplicação) ignora
     * setBackground() nos botões por definição — por isso pintamos o fundo
     * manualmente, para o destaque da linha vencedora funcionar sempre,
     * independentemente do tema ativo.
     */
    private static class CasaButton extends JButton {
        CasaButton() {
            setContentAreaFilled(false);
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            g.setColor(getBackground());
            g.fillRect(0, 0, getWidth(), getHeight());
            super.paintComponent(g);
        }
    }

    private JPanel construirTabuleiroVisual() {
        JPanel grelha = new JPanel(new GridLayout(3, 3, 6, 6));
        grelha.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                JButton botao = new CasaButton();
                botao.setFont(botao.getFont().deriveFont(Font.BOLD, 48f));
                botao.setFocusPainted(false);
                botao.setBackground(COR_FUNDO_CASA);
                botao.setOpaque(true);

                int linha = i;
                int coluna = j;
                botao.addActionListener(e -> jogarNaCasa(linha, coluna));

                botoes[i][j] = botao;
                grelha.add(botao);
            }
        }
        return grelha;
    }

    private boolean modoContraComputador() {
        return comboModo.getSelectedIndex() == 1;
    }

    private void jogarNaCasa(int linha, int coluna) {
        if (jogoTerminado) {
            return;
        }
        if (modoContraComputador() && jogadorAtual == 'O') {
            return; // é a vez do computador, o utilizador não joga por O
        }

        boolean jogadaFeita = tabuleiro.jogar(linha, coluna, jogadorAtual);
        if (!jogadaFeita) {
            return; // casa já ocupada
        }
        atualizarBotao(linha, coluna);

        if (verificarFimDeJogo()) {
            return;
        }

        trocarJogador();

        if (modoContraComputador() && jogadorAtual == 'O') {
            labelEstado.setText("O computador esta a pensar...");
            // pequena pausa para parecer mais natural, em vez de jogar instantaneamente
            Timer temporizador = new Timer(400, this::jogadaDoComputador);
            temporizador.setRepeats(false);
            temporizador.start();
        }
    }

    private void jogadaDoComputador(ActionEvent evento) {
        int[] jogada = ia.escolherJogada(tabuleiro, 'O', 'X');
        if (jogada == null) {
            return; // não deveria acontecer se o jogo ainda não tiver terminado
        }
        tabuleiro.jogar(jogada[0], jogada[1], 'O');
        atualizarBotao(jogada[0], jogada[1]);

        if (!verificarFimDeJogo()) {
            trocarJogador();
        }
    }

    private void trocarJogador() {
        jogadorAtual = (jogadorAtual == 'X') ? 'O' : 'X';
        labelEstado.setText("Vez do jogador " + jogadorAtual);
    }

    private void atualizarBotao(int linha, int coluna) {
        JButton botao = botoes[linha][coluna];
        char simbolo = tabuleiro.getCasa(linha, coluna);
        botao.setText(String.valueOf(simbolo));
        botao.setForeground(simbolo == 'X' ? COR_X : COR_O);
    }

    /** Verifica se o jogo terminou (vitória ou empate) e atualiza a interface. Devolve true se terminou. */
    private boolean verificarFimDeJogo() {
        char vencedor = tabuleiro.verificarVencedor();
        if (vencedor != Tabuleiro.VAZIO) {
            jogoTerminado = true;
            labelEstado.setText("O jogador " + vencedor + " ganhou!");
            destacarLinhaVencedora();
            return true;
        }
        if (tabuleiro.tabuleiroCheio()) {
            jogoTerminado = true;
            labelEstado.setText("Empate!");
            return true;
        }
        return false;
    }

    private void destacarLinhaVencedora() {
        int[][] posicoes = tabuleiro.getLinhaVencedora();
        if (posicoes == null) {
            return;
        }
        for (int[] posicao : posicoes) {
            botoes[posicao[0]][posicao[1]].setBackground(COR_VENCEDORA);
        }
    }

    private void novoJogo(ActionEvent evento) {
        tabuleiro.limpar();
        jogadorAtual = 'X';
        jogoTerminado = false;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                botoes[i][j].setText("");
                botoes[i][j].setBackground(COR_FUNDO_CASA);
            }
        }
        labelEstado.setText("Vez do jogador X");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new JogoGUI().setVisible(true));
    }
}
