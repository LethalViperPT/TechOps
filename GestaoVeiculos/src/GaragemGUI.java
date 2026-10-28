import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.util.List;

/**
 * Gestão de Veículos - versão com interface gráfica (Swing).
 * Reutiliza as mesmas classes Veiculo/Carro/Mota/Autocarro e GestorVeiculos
 * da versão de consola.
 */
public class GaragemGUI extends JFrame {

    private static final Color COR_PRIMARIA = new Color(0x1E, 0x40, 0xAF);
    private static final Color FUNDO = new Color(0xF4, 0xF6, 0xFB);

    private static final String FICHEIRO_DADOS = "veiculos.txt";
    private final PersistenciaVeiculos persistencia = new PersistenciaVeiculos(FICHEIRO_DADOS);
    private final GestorVeiculos gestor = new GestorVeiculos();

    private final JComboBox<String> comboTipo = new JComboBox<>(new String[]{"Carro", "Mota", "Autocarro"});
    private final JTextField campoMatricula = new JTextField(10);
    private final JTextField campoMarca = new JTextField(10);
    private final JTextField campoModelo = new JTextField(10);
    private final JTextField campoAno = new JTextField(6);
    private final JTextField campoCor = new JTextField(10);
    private final JTextField campoPreco = new JTextField(8);

    // campos específicos de cada tipo, trocados consoante o combo de tipo
    private final JPanel painelCamposEspecificos = new JPanel(new CardLayout());
    private final JTextField campoPortas = new JTextField(4);
    private final JTextField campoCombustivel = new JTextField(10);
    private final JTextField campoCilindrada = new JTextField(6);
    private final JTextField campoCapacidade = new JTextField(6);

    private final JComboBox<String> filtroTipo = new JComboBox<>(new String[]{"Todos", "Carro", "Mota", "Autocarro"});
    private final JTextField campoPesquisa = new JTextField(16);
    private final VeiculoTableModel modeloTabela = new VeiculoTableModel();
    private final JTable tabela = new JTable(modeloTabela);
    private final JLabel labelEstado = new JLabel(" ");

    public GaragemGUI() {
        super("Gestão de Veículos");
        configurarLargurasDosCampos();
        gestor.addAll(persistencia.carregar());

        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                persistencia.guardar(gestor);
                dispose();
                System.exit(0);
            }
        });
        setLayout(new BorderLayout(8, 8));
        getContentPane().setBackground(FUNDO);

        add(construirFormulario(), BorderLayout.NORTH);
        add(construirPainelTabela(), BorderLayout.CENTER);
        add(construirRodape(), BorderLayout.SOUTH);

        comboTipo.addActionListener(e -> trocarCamposEspecificos());
        trocarCamposEspecificos();
        atualizarTabela();

        setSize(1180, 580);
        setMinimumSize(new Dimension(1040, 500));
        setLocationRelativeTo(null);
    }

    /**
     * Dá a cada campo de texto um tamanho fixo em pixels. Sem isto, alguns
     * campos ficavam praticamente invisíveis (largura quase zero) — o
     * cálculo automático do GridBagLayout, combinado com vários campos a
     * partilhar a mesma coluna, estava a encolhê-los. Definir o tamanho
     * explicitamente resolve isso de forma garantida.
     */
    private void configurarLargurasDosCampos() {
        Dimension larga = new Dimension(150, 26);
        Dimension media = new Dimension(110, 26);
        Dimension estreita = new Dimension(70, 26);

        campoMatricula.setPreferredSize(media);
        campoMarca.setPreferredSize(media);
        campoModelo.setPreferredSize(larga);
        campoAno.setPreferredSize(estreita);
        campoCor.setPreferredSize(media);
        campoPreco.setPreferredSize(estreita);
        campoPortas.setPreferredSize(estreita);
        campoCombustivel.setPreferredSize(media);
        campoCilindrada.setPreferredSize(estreita);
        campoCapacidade.setPreferredSize(estreita);
    }

    private static void aplicarTemaNimbus() {
        // Em Linux, o tema do sistema (GTK) tem um bug conhecido em cliques
        // dentro de JComboBox. O Nimbus não tem esse problema.
        //
        // Importante: isto tem de ser chamado ANTES de qualquer componente
        // Swing ser criado (por isso é feito no main(), antes do "new
        // GaragemGUI()", e não dentro do construtor) — os campos de texto
        // desta classe são inicializados como campos de instância, o que em
        // Java acontece antes do corpo do construtor correr. Se o tema só
        // for aplicado depois de os campos já existirem, alguns ficam com
        // um tamanho errado (praticamente invisíveis).
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ignorada) {
            // mantém o tema por omissão se o Nimbus não estiver disponível
        }
    }

    // ---------- Formulário ----------

    private JPanel construirFormulario() {
        JPanel painel = new JPanel(new GridBagLayout());
        painel.setBackground(FUNDO);
        painel.setBorder(BorderFactory.createEmptyBorder(12, 14, 8, 14));

        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(4, 6, 4, 6);
        c.fill = GridBagConstraints.HORIZONTAL;

        JLabel titulo = new JLabel("Gestão de Veículos");
        titulo.setFont(titulo.getFont().deriveFont(Font.BOLD, 20f));
        titulo.setForeground(COR_PRIMARIA);
        c.gridx = 0; c.gridy = 0; c.gridwidth = 8; c.insets = new Insets(0, 6, 10, 6);
        painel.add(titulo, c);

        c.gridwidth = 1; c.insets = new Insets(4, 6, 4, 6);

        c.gridx = 0; c.gridy = 1;
        painel.add(new JLabel("Tipo:"), c);
        c.gridx = 1;
        painel.add(comboTipo, c);

        c.gridx = 2;
        painel.add(new JLabel("Matricula:"), c);
        c.gridx = 3; c.weightx = 0.22;
        painel.add(campoMatricula, c);
        c.weightx = 0;

        c.gridx = 4;
        painel.add(new JLabel("Marca:"), c);
        c.gridx = 5; c.weightx = 0.22;
        painel.add(campoMarca, c);
        c.weightx = 0;

        c.gridx = 6;
        painel.add(new JLabel("Modelo:"), c);
        c.gridx = 7; c.weightx = 0.2;
        painel.add(campoModelo, c);
        c.weightx = 0;

        c.gridx = 0; c.gridy = 2;
        painel.add(new JLabel("Ano:"), c);
        c.gridx = 1; c.weightx = 0.14;
        painel.add(campoAno, c);
        c.weightx = 0;

        c.gridx = 2;
        painel.add(new JLabel("Cor:"), c);
        c.gridx = 3; c.weightx = 0.22;
        painel.add(campoCor, c);
        c.weightx = 0;

        c.gridx = 4;
        painel.add(new JLabel("Preco (EUR):"), c);
        c.gridx = 5; c.weightx = 0.22;
        painel.add(campoPreco, c);
        c.weightx = 0;

        c.gridx = 6;
        painel.add(new JLabel("Dados do tipo:"), c);
        c.gridx = 7; c.weightx = 0.2;
        painel.add(painelCamposEspecificos, c);
        c.weightx = 0;

        // painéis com os campos próprios de cada tipo (trocados com CardLayout)
        JPanel painelCarro = new JPanel(new GridLayout(1, 4, 4, 0));
        painelCarro.setOpaque(false);
        painelCarro.add(new JLabel("Portas:"));
        painelCarro.add(campoPortas);
        painelCarro.add(new JLabel("Combustivel:"));
        painelCarro.add(campoCombustivel);

        JPanel painelMota = new JPanel(new GridLayout(1, 2, 4, 0));
        painelMota.setOpaque(false);
        painelMota.add(new JLabel("Cilindrada (cc):"));
        painelMota.add(campoCilindrada);

        JPanel painelAutocarro = new JPanel(new GridLayout(1, 2, 4, 0));
        painelAutocarro.setOpaque(false);
        painelAutocarro.add(new JLabel("Capacidade (passageiros):"));
        painelAutocarro.add(campoCapacidade);

        painelCamposEspecificos.setOpaque(false);
        painelCamposEspecificos.add(painelCarro, "Carro");
        painelCamposEspecificos.add(painelMota, "Mota");
        painelCamposEspecificos.add(painelAutocarro, "Autocarro");

        c.gridx = 0; c.gridy = 3; c.gridwidth = 8; c.insets = new Insets(10, 6, 4, 6);
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        painelBotoes.setOpaque(false);

        JButton botaoAdicionar = new JButton("Adicionar Veiculo");
        botaoAdicionar.setBackground(COR_PRIMARIA);
        botaoAdicionar.setForeground(Color.WHITE);
        botaoAdicionar.setOpaque(true);
        botaoAdicionar.setFocusPainted(false);
        botaoAdicionar.addActionListener(e -> adicionarVeiculo());

        JButton botaoLimpar = new JButton("Limpar Campos");
        botaoLimpar.addActionListener(e -> limparFormulario());

        painelBotoes.add(botaoAdicionar);
        painelBotoes.add(botaoLimpar);
        painel.add(painelBotoes, c);

        return painel;
    }

    private void trocarCamposEspecificos() {
        CardLayout cl = (CardLayout) painelCamposEspecificos.getLayout();
        cl.show(painelCamposEspecificos, (String) comboTipo.getSelectedItem());
    }

    // ---------- Tabela ----------

    private JPanel construirPainelTabela() {
        tabela.setRowHeight(24);
        tabela.getTableHeader().setBackground(COR_PRIMARIA);
        tabela.getTableHeader().setForeground(Color.WHITE);
        tabela.getTableHeader().setFont(tabela.getTableHeader().getFont().deriveFont(Font.BOLD));

        JScrollPane scroll = new JScrollPane(tabela);
        scroll.setBorder(BorderFactory.createEmptyBorder(4, 14, 4, 14));

        JPanel painel = new JPanel(new BorderLayout());
        painel.setBackground(FUNDO);
        painel.add(scroll, BorderLayout.CENTER);
        return painel;
    }

    private JPanel construirRodape() {
        JPanel painel = new JPanel(new BorderLayout());
        painel.setBackground(FUNDO);
        painel.setBorder(BorderFactory.createEmptyBorder(4, 14, 12, 14));

        JPanel linha = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 4));
        linha.setOpaque(false);
        linha.add(new JLabel("Filtrar por tipo:"));
        linha.add(filtroTipo);
        filtroTipo.addActionListener(e -> atualizarTabela());

        JButton botaoRemover = new JButton("Remover Selecionado");
        botaoRemover.addActionListener(e -> removerSelecionado());
        linha.add(botaoRemover);

        linha.add(new JLabel("   Pesquisar:"));
        campoPesquisa.setPreferredSize(new Dimension(180, 26));
        linha.add(campoPesquisa);

        JButton botaoPesquisar = new JButton("Pesquisar");
        botaoPesquisar.addActionListener(e -> pesquisar());
        linha.add(botaoPesquisar);

        JButton botaoLimparPesquisa = new JButton("Limpar Pesquisa");
        botaoLimparPesquisa.addActionListener(e -> {
            campoPesquisa.setText("");
            atualizarTabela();
        });
        linha.add(botaoLimparPesquisa);

        // Enter no campo de pesquisa também pesquisa, sem ser preciso clicar no botão
        campoPesquisa.addActionListener(e -> pesquisar());

        painel.add(linha, BorderLayout.NORTH);
        painel.add(labelEstado, BorderLayout.SOUTH);
        return painel;
    }

    private void pesquisar() {
        String termo = campoPesquisa.getText().trim();
        if (termo.isBlank()) {
            atualizarTabela();
            return;
        }
        List<Veiculo> resultado = gestor.pesquisar(termo);
        modeloTabela.atualizar(resultado);
        labelEstado.setText("Pesquisa \"" + termo + "\": " + resultado.size() + " veiculo(s) encontrado(s) (de " + gestor.size() + " no total).");
    }

    // ---------- Ações ----------

    private void adicionarVeiculo() {
        String matricula = campoMatricula.getText().trim();
        String marca = campoMarca.getText().trim();
        String modelo = campoModelo.getText().trim();
        String cor = campoCor.getText().trim();

        if (matricula.isBlank() || marca.isBlank() || modelo.isBlank() || cor.isBlank()) {
            mostrarAviso("Preenche todos os campos (matricula, marca, modelo e cor).");
            return;
        }
        if (gestor.procurarPorMatricula(matricula) != null) {
            mostrarAviso("Ja existe um veiculo com essa matricula.");
            return;
        }

        int ano;
        double preco;
        try {
            ano = Integer.parseInt(campoAno.getText().trim());
            preco = Double.parseDouble(campoPreco.getText().trim().replace(",", "."));
        } catch (NumberFormatException e) {
            mostrarAviso("Ano e preco tem de ser numeros validos.");
            return;
        }

        String tipo = (String) comboTipo.getSelectedItem();
        Veiculo veiculo;
        try {
            switch (tipo) {
                case "Carro" -> {
                    int portas = Integer.parseInt(campoPortas.getText().trim());
                    String combustivel = campoCombustivel.getText().trim();
                    if (combustivel.isBlank()) {
                        mostrarAviso("Indica o tipo de combustivel.");
                        return;
                    }
                    veiculo = new Carro(matricula, marca, modelo, ano, cor, preco, portas, combustivel);
                }
                case "Mota" -> {
                    int cilindrada = Integer.parseInt(campoCilindrada.getText().trim());
                    veiculo = new Mota(matricula, marca, modelo, ano, cor, preco, cilindrada);
                }
                default -> {
                    int capacidade = Integer.parseInt(campoCapacidade.getText().trim());
                    veiculo = new Autocarro(matricula, marca, modelo, ano, cor, preco, capacidade);
                }
            }
        } catch (NumberFormatException e) {
            mostrarAviso("Os dados especificos do tipo (portas / cilindrada / capacidade) tem de ser numeros.");
            return;
        }

        gestor.add(veiculo);
        persistencia.guardar(gestor);
        mostrarEstado("Veiculo " + matricula + " adicionado com sucesso.");
        limparFormulario();
        atualizarTabela();
    }

    private void removerSelecionado() {
        int linha = tabela.getSelectedRow();
        if (linha < 0) {
            mostrarAviso("Seleciona primeiro um veiculo na tabela.");
            return;
        }
        Veiculo veiculo = modeloTabela.getVeiculoNaLinha(linha);
        int confirmacao = JOptionPane.showConfirmDialog(this,
                "Remover o veiculo " + veiculo.getMatricula() + " (" + veiculo.getMarca() + " " + veiculo.getModelo() + ")?",
                "Confirmar remoção", JOptionPane.YES_NO_OPTION);
        if (confirmacao != JOptionPane.YES_OPTION) return;

        gestor.removerPorMatricula(veiculo.getMatricula());
        persistencia.guardar(gestor);
        mostrarEstado("Veiculo " + veiculo.getMatricula() + " removido.");
        atualizarTabela();
    }

    private void limparFormulario() {
        campoMatricula.setText("");
        campoMarca.setText("");
        campoModelo.setText("");
        campoAno.setText("");
        campoCor.setText("");
        campoPreco.setText("");
        campoPortas.setText("");
        campoCombustivel.setText("");
        campoCilindrada.setText("");
        campoCapacidade.setText("");
    }

    private void atualizarTabela() {
        String tipo = (String) filtroTipo.getSelectedItem();
        List<Veiculo> lista = "Todos".equals(tipo) ? gestor.listarTodos() : gestor.listarPorTipo(tipo);
        modeloTabela.atualizar(lista);
        labelEstado.setText("A mostrar " + lista.size() + " de " + gestor.size() + " veiculo(s) no total.");
    }

    private void mostrarAviso(String mensagem) {
        JOptionPane.showMessageDialog(this, mensagem, "Aviso", JOptionPane.WARNING_MESSAGE);
    }

    private void mostrarEstado(String mensagem) {
        labelEstado.setText(mensagem);
    }

    // ---------- Modelo da tabela ----------

    /** Adapta a lista de veículos para ser apresentada na JTable. */
    private static class VeiculoTableModel extends AbstractTableModel {
        private static final String[] COLUNAS = {"Tipo", "Matricula", "Marca", "Modelo", "Ano", "Cor", "Preco (EUR)", "Info especifica"};
        private List<Veiculo> veiculos = new java.util.ArrayList<>();

        void atualizar(List<Veiculo> novaLista) {
            this.veiculos = novaLista;
            fireTableDataChanged();
        }

        Veiculo getVeiculoNaLinha(int linha) {
            return veiculos.get(linha);
        }

        @Override
        public int getRowCount() {
            return veiculos.size();
        }

        @Override
        public int getColumnCount() {
            return COLUNAS.length;
        }

        @Override
        public String getColumnName(int coluna) {
            return COLUNAS[coluna];
        }

        @Override
        public Object getValueAt(int linha, int coluna) {
            Veiculo v = veiculos.get(linha);
            return switch (coluna) {
                case 0 -> v.getTipo();
                case 1 -> v.getMatricula();
                case 2 -> v.getMarca();
                case 3 -> v.getModelo();
                case 4 -> v.getAno();
                case 5 -> v.getCor();
                case 6 -> String.format("%.2f", v.getPreco());
                case 7 -> v.getInfoEspecifica();
                default -> "";
            };
        }

        @Override
        public boolean isCellEditable(int linha, int coluna) {
            return false;
        }
    }

    public static void main(String[] args) {
        aplicarTemaNimbus();
        SwingUtilities.invokeLater(() -> new GaragemGUI().setVisible(true));
    }
}
